#!/bin/bash
# ============================================================
# server-indie - Stop Script
# Usage: bash deploy/stop.sh
# ============================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVER_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
PID_FILE="$SERVER_DIR/server.pid"

echo "Đang dừng server-indie..."

# Cách 1: Dùng systemd (khuyến nghị)
if systemctl is-active --quiet dragonserver 2>/dev/null; then
    echo "Dừng qua systemd..."
    sudo systemctl stop dragonserver
    echo "[OK] server-indie đã dừng (systemd)"
    exit 0
fi

# Cách 2: Tìm process Java
PIDS=$(pgrep -f "server-indie-1.0.0.jar" 2>/dev/null)

if [ -z "$PIDS" ]; then
    echo "[WARN] Không tìm thấy process server-indie đang chạy"
    exit 0
fi

echo "Tìm thấy PID: $PIDS"
echo "Gửi SIGTERM (graceful shutdown)..."
kill $PIDS

# Chờ tối đa 30 giây
WAIT=0
while kill -0 $PIDS 2>/dev/null; do
    sleep 1
    WAIT=$((WAIT + 1))
    if [ $WAIT -ge 30 ]; then
        echo "[WARN] Timeout 30s — force kill..."
        kill -9 $PIDS 2>/dev/null
        break
    fi
    echo "  Đang chờ... ${WAIT}s"
done

echo "[OK] server-indie đã dừng"
