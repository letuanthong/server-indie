#!/bin/bash
# ============================================================
# server-indie - Database Backup Script
# Usage: bash deploy/backup.sh
# Cron:  0 4 * * * /home/gameserver/server-indie/deploy/backup.sh
# ============================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SERVER_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

# Đọc .env
if [ -f "$SERVER_DIR/.env" ]; then
    source "$SERVER_DIR/.env"
fi

DB_NAME="${DB_NAME:-whis_1}"
DB_NAME_DATA="${DB_NAME_DATA:-whis_2}"
DB_USER="${DB_USER:-dragonserver}"
DB_PASS="${DB_PASS:-DragonServer@2025}"
BACKUP_DIR="${BACKUP_DIR:-/home/gameserver/backups}"
KEEP_DAYS="${KEEP_DAYS:-7}"

DATE=$(date +%Y%m%d_%H%M%S)

mkdir -p "$BACKUP_DIR"

echo "[$(date '+%Y-%m-%d %H:%M:%S')] === Bắt đầu backup ==="

# Backup whis_1 (player data — quan trọng)
echo "  Backup ${DB_NAME}..."
if mysqldump -u "$DB_USER" -p"$DB_PASS" \
    --single-transaction --routines --triggers --quick \
    "$DB_NAME" 2>/dev/null | gzip > "$BACKUP_DIR/${DB_NAME}_${DATE}.sql.gz"; then
    SIZE=$(du -sh "$BACKUP_DIR/${DB_NAME}_${DATE}.sql.gz" | cut -f1)
    echo "  [OK] ${DB_NAME} → ${SIZE}"
else
    echo "  [ERROR] Backup ${DB_NAME} thất bại!"
fi

# Backup whis_2 (game data)
echo "  Backup ${DB_NAME_DATA}..."
if mysqldump -u "$DB_USER" -p"$DB_PASS" \
    --single-transaction --routines --triggers --quick \
    "$DB_NAME_DATA" 2>/dev/null | gzip > "$BACKUP_DIR/${DB_NAME_DATA}_${DATE}.sql.gz"; then
    SIZE=$(du -sh "$BACKUP_DIR/${DB_NAME_DATA}_${DATE}.sql.gz" | cut -f1)
    echo "  [OK] ${DB_NAME_DATA} → ${SIZE}"
else
    echo "  [ERROR] Backup ${DB_NAME_DATA} thất bại!"
fi

# Xóa backup cũ
DELETED=$(find "$BACKUP_DIR" -name "*.sql.gz" -mtime +"$KEEP_DAYS" -delete -print | wc -l)
if [ "$DELETED" -gt 0 ]; then
    echo "  Đã xóa $DELETED file backup cũ hơn ${KEEP_DAYS} ngày"
fi

echo "[$(date '+%Y-%m-%d %H:%M:%S')] === Backup hoàn tất ==="
echo "  Thư mục: $BACKUP_DIR"
ls -lh "$BACKUP_DIR"/*.sql.gz 2>/dev/null | tail -6
