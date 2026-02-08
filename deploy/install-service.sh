#!/bin/bash
# ============================================================
# Cài đặt / cập nhật Systemd service cho server-indie
# Usage: sudo bash deploy/install-service.sh
# ============================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVER_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
SERVICE_SRC="$SCRIPT_DIR/dragonserver.service"
SERVICE_DST="/etc/systemd/system/dragonserver.service"

if [ "$EUID" -ne 0 ]; then
    echo "[ERROR] Cần chạy với sudo"
    exit 1
fi

if [ ! -f "$SERVICE_SRC" ]; then
    echo "[ERROR] Không tìm thấy $SERVICE_SRC"
    exit 1
fi

# Đọc .env nếu có để lấy GAME_USER
if [ -f "$SERVER_DIR/.env" ]; then
    source "$SERVER_DIR/.env"
fi
GAME_USER="${GAME_USER:-gameserver}"
INSTALL_DIR="/home/${GAME_USER}/server-indie"

# Tạo bản copy với đường dẫn đúng
echo "Cài đặt service file..."
sed \
    -e "s|User=gameserver|User=${GAME_USER}|" \
    -e "s|Group=gameserver|Group=${GAME_USER}|" \
    -e "s|/home/gameserver/server-indie|${INSTALL_DIR}|g" \
    "$SERVICE_SRC" > "$SERVICE_DST"

# Reload và enable
systemctl daemon-reload
systemctl enable dragonserver

echo "[OK] Service đã cài đặt: $SERVICE_DST"
echo ""
echo "Sử dụng:"
echo "  sudo systemctl start dragonserver"
echo "  sudo systemctl stop dragonserver"
echo "  sudo systemctl restart dragonserver"
echo "  sudo systemctl status dragonserver"
echo "  sudo journalctl -u dragonserver -f"
