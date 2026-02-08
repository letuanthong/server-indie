#!/bin/bash
# ============================================================
# Import SQL safely with proper encoding
# Usage: bash deploy/import-sql.sh
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

echo "============================================"
echo " Import SQL với UTF-8 encoding"
echo "============================================"

cd "$SERVER_DIR"

# Fix line endings trước
echo ""
echo "[1/3] Fixing line endings..."
if [ -f "sql/whis_1.sql" ]; then
    # Remove carriage return
    sed -i 's/\r$//' sql/whis_1.sql 2>/dev/null || \
        tr -d '\r' < sql/whis_1.sql > sql/whis_1.sql.tmp && mv sql/whis_1.sql.tmp sql/whis_1.sql
    echo "  ✓ Fixed whis_1.sql"
fi

if [ -f "sql/whis_2.sql" ]; then
    sed -i 's/\r$//' sql/whis_2.sql 2>/dev/null || \
        tr -d '\r' < sql/whis_2.sql > sql/whis_2.sql.tmp && mv sql/whis_2.sql.tmp sql/whis_2.sql
    echo "  ✓ Fixed whis_2.sql"
fi

# Import whis_1
echo ""
echo "[2/3] Importing whis_1..."
if [ -f "sql/whis_1.sql" ]; then
    mysql -u "$DB_USER" -p"$DB_PASS" \
        --default-character-set=utf8mb4 \
        --comments \
        --force \
        "$DB_NAME" < sql/whis_1.sql
    
    if [ $? -eq 0 ]; then
        echo "  ✓ whis_1 imported successfully"
    else
        echo "  ✗ whis_1 import failed"
        exit 1
    fi
else
    echo "  ✗ File sql/whis_1.sql not found"
    exit 1
fi

# Import whis_2
echo ""
echo "[3/3] Importing whis_2..."
if [ -f "sql/whis_2.sql" ]; then
    mysql -u "$DB_USER" -p"$DB_PASS" \
        --default-character-set=utf8mb4 \
        --comments \
        --force \
        "$DB_NAME_DATA" < sql/whis_2.sql
    
    if [ $? -eq 0 ]; then
        echo "  ✓ whis_2 imported successfully"
    else
        echo "  ✗ whis_2 import failed"
        exit 1
    fi
else
    echo "  ✗ File sql/whis_2.sql not found"
    exit 1
fi

echo ""
echo "============================================"
echo " Import hoàn tất!"
echo "============================================"

# Verify
echo ""
echo "Kiểm tra tables..."
mysql -u "$DB_USER" -p"$DB_PASS" -e "USE $DB_NAME; SHOW TABLES;" | head -10
echo "..."
mysql -u "$DB_USER" -p"$DB_PASS" -e "USE $DB_NAME_DATA; SHOW TABLES;" | head -10
