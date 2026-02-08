#!/bin/bash
# ============================================================
# server-indie - Start Script
# Usage: bash deploy/start.sh
# ============================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVER_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
JAR_FILE="$SERVER_DIR/build/libs/server-indie-1.0.0.jar"
LOG_DIR="$SERVER_DIR/logs"

# Đọc .env nếu có
if [ -f "$SERVER_DIR/.env" ]; then
    source "$SERVER_DIR/.env"
fi

JAVA_XMS="${JAVA_XMS:-1g}"
JAVA_XMX="${JAVA_XMX:-2g}"

# Kiểm tra JAR
if [ ! -f "$JAR_FILE" ]; then
    echo "[ERROR] Không tìm thấy $JAR_FILE"
    echo "        Chạy: ./gradlew clean jar"
    exit 1
fi

# Tạo thư mục log
mkdir -p "$LOG_DIR"

# JVM Options
JVM_OPTS=(
    -server
    -Xms${JAVA_XMS}
    -Xmx${JAVA_XMX}
    # G1GC - tối ưu cho game server, ít pause
    -XX:+UseG1GC
    -XX:MaxGCPauseMillis=50
    -XX:+ParallelRefProcEnabled
    -XX:G1HeapRegionSize=4m
    -XX:+UseStringDeduplication
    # OOM handling
    -XX:+HeapDumpOnOutOfMemoryError
    -XX:HeapDumpPath="$LOG_DIR/heapdump.hprof"
    # Encoding UTF-8 (quan trọng cho tiếng Việt)
    -Dfile.encoding=UTF-8
    -Dconsole.encoding=UTF-8
    -Dsun.jnu.encoding=UTF-8
)

cd "$SERVER_DIR"

echo "============================================"
echo " server-indie Starting..."
echo " JAR : $JAR_FILE"
echo " RAM : ${JAVA_XMS} - ${JAVA_XMX}"
echo " PID : $$"
echo " Time: $(date '+%Y-%m-%d %H:%M:%S')"
echo "============================================"

# Chạy server — exec thay thế process hiện tại (tốt cho systemd)
exec java "${JVM_OPTS[@]}" -jar "$JAR_FILE"
