#!/bin/bash
# ============================================================
# server-indie - Setup Script cho Debian 12
# Clone repo về → chạy script này → xong
# Usage: sudo bash deploy/setup.sh
# ============================================================

set -e

# ===================== MÀU SẮC =====================
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

log_info()  { echo -e "${CYAN}[INFO]${NC} $1"; }
log_ok()    { echo -e "${GREEN}[OK]${NC} $1"; }
log_warn()  { echo -e "${YELLOW}[WARN]${NC} $1"; }
log_error() { echo -e "${RED}[ERROR]${NC} $1"; }

# ===================== KIỂM TRA =====================
if [ "$EUID" -ne 0 ]; then
    log_error "Vui lòng chạy với sudo: sudo bash deploy/setup.sh"
    exit 1
fi

if [ ! -f /etc/debian_version ]; then
    log_error "Script này chỉ hỗ trợ Debian/Ubuntu"
    exit 1
fi

# ===================== CẤU HÌNH =====================
ENV_FILE="$(dirname "$(readlink -f "$0")")/../.env"
if [ -f "$ENV_FILE" ]; then
    log_info "Đọc cấu hình từ .env"
    source "$ENV_FILE"
fi

# Giá trị mặc định (ghi đè bởi .env nếu có)
DB_NAME="${DB_NAME:-whis_1}"
DB_NAME_DATA="${DB_NAME_DATA:-whis_2}"
DB_USER="${DB_USER:-dragonserver}"
DB_PASS="${DB_PASS:-DragonServer@2025}"
GAME_USER="${GAME_USER:-gameserver}"
SERVER_PORT="${SERVER_PORT:-14445}"
JAVA_XMS="${JAVA_XMS:-1g}"
JAVA_XMX="${JAVA_XMX:-2g}"

# Tìm đường dẫn project (thư mục cha của deploy/)
PROJECT_DIR="$(cd "$(dirname "$(readlink -f "$0")")/.." && pwd)"
INSTALL_DIR="/home/${GAME_USER}/server-indie"

echo ""
echo "============================================================"
echo "         server-indie - Debian 12 Auto Setup"
echo "============================================================"
echo ""
echo "  Project source : $PROJECT_DIR"
echo "  Install dir    : $INSTALL_DIR"
echo "  DB User        : $DB_USER"
echo "  DB Names       : $DB_NAME, $DB_NAME_DATA"
echo "  Game User      : $GAME_USER"
echo "  Server Port    : $SERVER_PORT"
echo "  JVM Memory     : ${JAVA_XMS} - ${JAVA_XMX}"
echo ""
echo "============================================================"
echo ""

read -p "Tiếp tục cài đặt? (y/N): " CONFIRM
if [[ ! "$CONFIRM" =~ ^[Yy]$ ]]; then
    echo "Đã hủy."
    exit 0
fi

# ===================== STEP 1: System packages =====================
log_info "=== Bước 1/8: Cập nhật hệ thống ==="
apt update && apt upgrade -y
apt install -y curl wget unzip git nano htop screen ufw lsb-release \
    apt-transport-https ca-certificates gnupg
log_ok "Hệ thống đã cập nhật"

# ===================== STEP 2: Tạo user =====================
log_info "=== Bước 2/8: Tạo user $GAME_USER ==="
if id "$GAME_USER" &>/dev/null; then
    log_warn "User $GAME_USER đã tồn tại, bỏ qua"
else
    useradd -m -s /bin/bash "$GAME_USER"
    log_ok "Đã tạo user $GAME_USER"
fi

# ===================== STEP 3: Java 21 =====================
log_info "=== Bước 3/8: Cài đặt Java 21 (Eclipse Temurin) ==="
if java -version 2>&1 | grep -q "21\."; then
    log_warn "Java 21 đã được cài đặt, bỏ qua"
else
    wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | \
        gpg --dearmor -o /usr/share/keyrings/adoptium.gpg 2>/dev/null || true

    echo "deb [signed-by=/usr/share/keyrings/adoptium.gpg] https://packages.adoptium.net/artifactory/deb $(lsb_release -cs) main" | \
        tee /etc/apt/sources.list.d/adoptium.list > /dev/null

    apt update
    apt install -y temurin-21-jdk
    log_ok "Java 21 đã cài đặt"
fi
java -version

# ===================== STEP 4: MariaDB =====================
log_info "=== Bước 4/8: Cài đặt MariaDB ==="
if systemctl is-active --quiet mariadb 2>/dev/null; then
    log_warn "MariaDB đã chạy, bỏ qua cài đặt"
else
    apt install -y mariadb-server mariadb-client
    systemctl enable mariadb
    systemctl start mariadb
    log_ok "MariaDB đã cài đặt và khởi động"
fi

# Cấu hình MySQL tối ưu cho game server
log_info "Áp dụng cấu hình MariaDB tối ưu..."
cat > /etc/mysql/mariadb.conf.d/99-server-indie.cnf << 'MYSQLCONF'
[mysqld]
# === Connection ===
max_connections = 100
wait_timeout = 600
interactive_timeout = 600

# === InnoDB Performance ===
innodb_buffer_pool_size = 512M
innodb_log_file_size = 128M
innodb_flush_log_at_trx_commit = 2
innodb_flush_method = O_DIRECT

# === Character Set ===
character-set-server = utf8mb4
collation-server = utf8mb4_general_ci

# === Slow Query Log ===
slow_query_log = 1
slow_query_log_file = /var/log/mysql/slow.log
long_query_time = 2
MYSQLCONF

systemctl restart mariadb
log_ok "MariaDB đã cấu hình tối ưu"

# ===================== STEP 5: Database =====================
log_info "=== Bước 5/8: Tạo database và import SQL ==="

# Tạo user và database
mysql -u root << EOSQL
CREATE DATABASE IF NOT EXISTS \`${DB_NAME}\` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS \`${DB_NAME_DATA}\` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASS}';
GRANT ALL PRIVILEGES ON \`${DB_NAME}\`.* TO '${DB_USER}'@'localhost';
GRANT ALL PRIVILEGES ON \`${DB_NAME_DATA}\`.* TO '${DB_USER}'@'localhost';
FLUSH PRIVILEGES;
EOSQL
log_ok "Đã tạo database ${DB_NAME}, ${DB_NAME_DATA} và user ${DB_USER}"

# Import SQL
if [ -f "$PROJECT_DIR/sql/whis_1.sql" ]; then
    log_info "Import whis_1.sql..."
    # Fix line endings với tr (tốt hơn sed)
    tr -d '\r' < "$PROJECT_DIR/sql/whis_1.sql" > "$PROJECT_DIR/sql/whis_1.sql.tmp" 2>/dev/null || true
    if [ -f "$PROJECT_DIR/sql/whis_1.sql.tmp" ]; then
        mv "$PROJECT_DIR/sql/whis_1.sql.tmp" "$PROJECT_DIR/sql/whis_1.sql"
    fi
    
    mysql -u "$DB_USER" -p"$DB_PASS" \
        --default-character-set=utf8mb4 \
        --comments --force \
        "$DB_NAME" < "$PROJECT_DIR/sql/whis_1.sql" 2>&1 | grep -v "^Warning:" || true
    
    log_ok "Đã import whis_1.sql"
else
    log_warn "Không tìm thấy sql/whis_1.sql"
fi

if [ -f "$PROJECT_DIR/sql/whis_2.sql" ]; then
    log_info "Import whis_2.sql..."
    # Fix line endings với tr (tốt hơn sed)
    tr -d '\r' < "$PROJECT_DIR/sql/whis_2.sql" > "$PROJECT_DIR/sql/whis_2.sql.tmp" 2>/dev/null || true
    if [ -f "$PROJECT_DIR/sql/whis_2.sql.tmp" ]; then
        mv "$PROJECT_DIR/sql/whis_2.sql.tmp" "$PROJECT_DIR/sql/whis_2.sql"
    fi
    
    mysql -u "$DB_USER" -p"$DB_PASS" \
        --default-character-set=utf8mb4 \
        --comments --force \
        "$DB_NAME_DATA" < "$PROJECT_DIR/sql/whis_2.sql" 2>&1 | grep -v "^Warning:" || true
    
    log_ok "Đã import whis_2.sql"
else
    log_warn "Không tìm thấy sql/whis_2.sql"
fi

# ===================== STEP 6: Build =====================
log_info "=== Bước 6/8: Copy project và build ==="

# Copy project sang thư mục game user (nếu chưa ở đúng chỗ)
if [ "$PROJECT_DIR" != "$INSTALL_DIR" ]; then
    mkdir -p "$INSTALL_DIR"
    rsync -a --exclude='.git' --exclude='build' --exclude='.gradle' \
        "$PROJECT_DIR/" "$INSTALL_DIR/"
    log_ok "Đã copy project sang $INSTALL_DIR"
else
    log_info "Project đã ở đúng vị trí $INSTALL_DIR"
fi

# Cập nhật server.properties với thông tin DB
log_info "Cập nhật cấu hình database trong server.properties..."
cd "$INSTALL_DIR"

sed -i "s|^database.user=.*|database.user=${DB_USER}|" config/server.properties
sed -i "s|^database.pass=.*|database.pass=${DB_PASS}|" config/server.properties
sed -i "s|^database.min=.*|database.min=5|" config/server.properties
sed -i "s|^database.max=.*|database.max=20|" config/server.properties
sed -i "s|^database.lifetime=.*|database.lifetime=300000|" config/server.properties

log_ok "Đã cập nhật config/server.properties"

# Build
log_info "Building JAR..."
chmod +x gradlew
./gradlew clean jar --no-daemon
log_ok "Build thành công: build/libs/server-indie-1.0.0.jar"

# Tạo thư mục logs
mkdir -p "$INSTALL_DIR/logs"

# Cấp quyền cho scripts
chmod +x deploy/*.sh

# Set ownership
chown -R "$GAME_USER:$GAME_USER" "$INSTALL_DIR"

# ===================== STEP 7: Systemd =====================
log_info "=== Bước 7/8: Cài đặt Systemd service ==="
bash "$INSTALL_DIR/deploy/install-service.sh"
log_ok "Systemd service đã cài đặt"

# ===================== STEP 8: Firewall =====================
log_info "=== Bước 8/8: Cấu hình Firewall ==="
ufw allow 22/tcp    >/dev/null 2>&1
ufw allow "${SERVER_PORT}/tcp" >/dev/null 2>&1

if ! ufw status | grep -q "Status: active"; then
    echo "y" | ufw enable >/dev/null 2>&1
fi

log_ok "Firewall đã mở port 22 (SSH) và ${SERVER_PORT} (Game)"

# ===================== XONG =====================
echo ""
echo "============================================================"
echo -e "${GREEN}         SETUP HOÀN TẤT!${NC}"
echo "============================================================"
echo ""
echo "  Quản lý server:"
echo "    sudo systemctl start dragonserver     # Khởi động"
echo "    sudo systemctl stop dragonserver      # Dừng"
echo "    sudo systemctl restart dragonserver   # Restart"
echo "    sudo systemctl status dragonserver    # Trạng thái"
echo ""
echo "  Xem log:"
echo "    sudo journalctl -u dragonserver -f"
echo ""
echo "  Chạy thủ công (test):"
echo "    su - $GAME_USER"
echo "    cd $INSTALL_DIR"
echo "    bash deploy/start.sh"
echo ""
echo "  Backup database:"
echo "    bash $INSTALL_DIR/deploy/backup.sh"
echo ""
echo "  QUAN TRỌNG:"
echo "    - Sửa config/server.properties → server.ip = IP public"
echo "    - Sửa .env nếu cần thay đổi cấu hình"
echo "    - Kiểm tra: ss -tlnp | grep ${SERVER_PORT}"
echo ""
echo "============================================================"
