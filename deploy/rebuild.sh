#!/bin/bash
# ============================================================
# server-indie - Rebuild & Restart
# Flow: Pull code mới → Copy sang gameserver → Build → Restart service
#
# CHẠY TẠI USER ROOT (nơi có git repo):
#   cd /root/server-indie
#   bash deploy/rebuild.sh
#
# Script sẽ tự động:
#   1. Git pull code mới
#   2. Rsync code sang /home/gameserver/server-indie
#   3. Build JAR tại /home/gameserver
#   4. Restart dragonserver service
# ============================================================

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CODE_SOURCE="$(cd "$SCRIPT_DIR/.." && pwd)"

# Load .env nếu có
ENV_FILE="$CODE_SOURCE/.env"
if [ -f "$ENV_FILE" ]; then
    source "$ENV_FILE"
fi

# Deploy dir (mặc định) - có thể override trong .env
DEPLOY_DIR="${DEPLOY_DIR:-/home/gameserver/server-indie}"
GAME_USER="${GAME_USER:-gameserver}"

echo "============================================"
echo " server-indie - Rebuild & Restart"
echo " Code source : $CODE_SOURCE (pull tại đây)"
echo " Deploy dir  : $DEPLOY_DIR (build tại đây)"
echo " Game user   : $GAME_USER"
echo " Time        : $(date '+%Y-%m-%d %H:%M:%S')"
echo "============================================"

# ===================== STEP 1: Dừng server =====================
echo ""
echo "[1/5] Dừng server..."
if systemctl is-active --quiet dragonserver 2>/dev/null; then
    sudo systemctl stop dragonserver
    echo "  → Server đã dừng"
else
    echo "  → Server không chạy, bỏ qua"
fi

# ===================== STEP 2: Pull code mới =====================
echo ""
echo "[2/5] Pull code mới tại $CODE_SOURCE..."
cd "$CODE_SOURCE"
if [ -d ".git" ]; then
    git pull --ff-only 2>&1 && echo "  → Git pull thành công" || {
        echo "[WARN] Git pull thất bại, tiếp tục với code hiện tại"
    }
else
    echo "[WARN] Không phải git repo, bỏ qua pull"
fi

# ===================== STEP 3: Rsync code sang deploy dir =====================
echo ""
echo "[3/5] Rsync code: $CODE_SOURCE → $DEPLOY_DIR..."
sudo mkdir -p "$DEPLOY_DIR"
sudo rsync -av --delete \
    --exclude='.git/' \
    --exclude='build/' \
    --exclude='bin/' \
    --exclude='.gradle/' \
    --exclude='.env' \
    --exclude='config/server.properties' \
    --exclude='logs/' \
    --exclude='src_backup/' \
    --exclude='migrate.py' \
    "$CODE_SOURCE/" "$DEPLOY_DIR/"
sudo chown -R "$GAME_USER:$GAME_USER" "$DEPLOY_DIR"
echo "  → Rsync hoàn tất"

# ===================== STEP 4: Build tại deploy dir =====================
echo ""
echo "[4/5] Building tại $DEPLOY_DIR..."
cd "$DEPLOY_DIR"
sudo chmod +x gradlew
sudo -u "$GAME_USER" bash -c "cd '$DEPLOY_DIR' && ./gradlew clean jar --no-daemon"
if [ $? -eq 0 ]; then
    echo "  → Build thành công"
else
    echo "[ERROR] Build thất bại!"
    exit 1
fi

# ===================== STEP 5: Khởi động service =====================
echo ""
echo "[5/5] Khởi động server..."
if systemctl is-enabled --quiet dragonserver 2>/dev/null; then
    sudo systemctl start dragonserver
    sleep 3
    echo ""
    echo "--- Server Status ---"
    sudo systemctl status dragonserver --no-pager -l 2>&1 | head -20
    echo ""
    echo "--- Recent Logs ---"
    sudo journalctl -u dragonserver --no-pager -n 20
else
    echo "[WARN] Service chưa cài đặt"
    echo "  Chạy thủ công: cd $DEPLOY_DIR && bash deploy/start.sh"
fi

echo ""
echo "============================================"
echo " Rebuild hoàn tất!"
echo " JAR: $DEPLOY_DIR/build/libs/server-indie-1.0.0.jar"
echo "============================================"
