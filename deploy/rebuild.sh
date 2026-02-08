#!/bin/bash
# ============================================================
# server-indie - Rebuild & Restart
# Dùng khi cập nhật code: pull code mới → chạy script này
# Usage: bash deploy/rebuild.sh
# ============================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVER_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$SERVER_DIR"

echo "============================================"
echo " server-indie - Rebuild & Restart"
echo " Time: $(date '+%Y-%m-%d %H:%M:%S')"
echo "============================================"

# 1. Dừng server
echo ""
echo "[1/4] Dừng server..."
if systemctl is-active --quiet dragonserver 2>/dev/null; then
    sudo systemctl stop dragonserver
    echo "  → Server đã dừng"
else
    echo "  → Server không chạy, bỏ qua"
fi

# 2. Pull code mới (nếu dùng git)
if [ -d ".git" ]; then
    echo ""
    echo "[2/4] Pull code mới..."
    git pull --ff-only 2>/dev/null && echo "  → Pull thành công" || echo "  → Không có git remote, bỏ qua"
else
    echo ""
    echo "[2/4] Không phải git repo, bỏ qua pull"
fi

# 3. Build
echo ""
echo "[3/4] Building..."
chmod +x gradlew
if ./gradlew clean jar --no-daemon; then
    echo "  → Build thành công"
else
    echo "[ERROR] Build thất bại! Server không được restart."
    exit 1
fi

# 4. Khởi động lại
echo ""
echo "[4/4] Khởi động server..."
if systemctl is-enabled --quiet dragonserver 2>/dev/null; then
    sudo systemctl start dragonserver
    sleep 3
    sudo systemctl status dragonserver --no-pager -l
else
    echo "  Service chưa cài đặt, chạy thủ công:"
    echo "  bash deploy/start.sh"
fi

echo ""
echo "============================================"
echo " Rebuild hoàn tất!"
echo "============================================"
