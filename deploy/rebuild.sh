#!/bin/bash
# ============================================================
# server-indie - Rebuild & Restart
# Hỗ trợ 3 cách cập nhật code:
#   1. Có .git      → tự động git pull
#   2. Có SOURCE_DIR → rsync từ thư mục source (user khác)
#   3. Truyền arg    → bash deploy/rebuild.sh /path/to/source
#
# Usage:
#   bash deploy/rebuild.sh                    # tự detect
#   bash deploy/rebuild.sh /root/server-indie # chỉ định source
# ============================================================

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVER_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

# Load .env nếu có
ENV_FILE="$SERVER_DIR/.env"
if [ -f "$ENV_FILE" ]; then
    source "$ENV_FILE"
fi

# Source dir có thể truyền qua argument hoặc .env
SOURCE_DIR="${1:-${SOURCE_DIR:-}}"

cd "$SERVER_DIR"

echo "============================================"
echo " server-indie - Rebuild & Restart"
echo " Server dir : $SERVER_DIR"
[ -n "$SOURCE_DIR" ] && echo " Source dir : $SOURCE_DIR"
echo " Time       : $(date '+%Y-%m-%d %H:%M:%S')"
echo "============================================"

# ===================== STEP 1: Dừng server =====================
echo ""
echo "[1/4] Dừng server..."
if systemctl is-active --quiet dragonserver 2>/dev/null; then
    sudo systemctl stop dragonserver
    echo "  → Server đã dừng"
else
    echo "  → Server không chạy, bỏ qua"
fi

# ===================== STEP 2: Cập nhật code =====================
echo ""
echo "[2/4] Cập nhật code..."

if [ -d "$SERVER_DIR/.git" ]; then
    # Cách 1: Thư mục hiện tại có git → pull trực tiếp
    echo "  → Phát hiện git repo, đang pull..."
    git pull --ff-only 2>&1 && echo "  → Git pull thành công" || echo "  → Git pull thất bại, tiếp tục build với code hiện tại"

elif [ -n "$SOURCE_DIR" ]; then
    # Cách 2: Sync từ thư mục source (user khác)
    if [ ! -d "$SOURCE_DIR" ]; then
        echo "[ERROR] Thư mục source không tồn tại: $SOURCE_DIR"
        exit 1
    fi

    # Nếu source có git, pull trước
    if [ -d "$SOURCE_DIR/.git" ]; then
        echo "  → Pull code mới tại $SOURCE_DIR..."
        (cd "$SOURCE_DIR" && git pull --ff-only 2>&1) && echo "  → Git pull thành công" || echo "  → Git pull thất bại, dùng code hiện có"
    fi

    # Rsync source → server dir (giữ nguyên config, build, .env)
    echo "  → Sync code từ $SOURCE_DIR → $SERVER_DIR..."
    rsync -a --delete \
        --exclude='.git' \
        --exclude='build' \
        --exclude='.gradle' \
        --exclude='.env' \
        --exclude='config/server.properties' \
        --exclude='logs' \
        "$SOURCE_DIR/" "$SERVER_DIR/"
    echo "  → Sync hoàn tất"
else
    echo "  → Không có git và không có SOURCE_DIR"
    echo "  → Build với code hiện tại"
    echo ""
    echo "  TIP: Để sync code từ user khác, dùng 1 trong 2 cách:"
    echo "    bash deploy/rebuild.sh /root/server-indie"
    echo "    hoặc thêm SOURCE_DIR=/root/server-indie vào .env"
fi

# ===================== STEP 3: Build =====================
echo ""
echo "[3/4] Building..."
chmod +x gradlew
if ./gradlew clean jar --no-daemon; then
    echo "  → Build thành công"
else
    echo "[ERROR] Build thất bại! Server không được restart."
    exit 1
fi

# ===================== STEP 4: Khởi động lại =====================
echo ""
echo "[4/4] Khởi động server..."
if systemctl is-enabled --quiet dragonserver 2>/dev/null; then
    sudo systemctl start dragonserver
    sleep 3
    echo ""
    echo "--- Server Status ---"
    sudo systemctl status dragonserver --no-pager -l 2>&1 | head -20
    echo ""
    echo "--- Recent Logs ---"
    sudo journalctl -u dragonserver --no-pager -n 15
else
    echo "  Service chưa cài đặt, chạy thủ công:"
    echo "  bash deploy/start.sh"
fi

echo ""
echo "============================================"
echo " Rebuild hoàn tất!"
echo "============================================"
