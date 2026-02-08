#!/bin/bash
# ============================================================
# Fix UTF-8 Encoding Issues
# Usage: bash deploy/fix-encoding.sh
# ============================================================

cd "$(dirname "$0")/.."

echo "============================================"
echo " Fixing UTF-8 Encoding"
echo "============================================"

# 1. Set locale UTF-8
echo ""
echo "[1/4] Setting locale..."
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8
echo "  → Locale set to UTF-8"

# 2. Git config
echo ""
echo "[2/4] Configuring Git..."
git config core.quotepath false
git config i18n.commitencoding utf-8
git config i18n.logoutputencoding utf-8
echo "  → Git encoding configured"

# 3. Fix file encoding (nếu có công cụ)
echo ""
echo "[3/4] Checking files..."
for file in README.md .env.example deploy/*.sh; do
    if [ -f "$file" ]; then
        # Đảm bảo newline Unix (LF)
        if command -v dos2unix &> /dev/null; then
            dos2unix "$file" 2>/dev/null
            echo "  ✓ Fixed: $file"
        else
            # Manual LF conversion
            sed -i 's/\r$//' "$file" 2>/dev/null && echo "  ✓ Fixed: $file"
        fi
    fi
done

# 4. Set executable permissions
echo ""
echo "[4/4] Setting permissions..."
chmod +x deploy/*.sh
echo "  → All scripts are executable"

echo ""
echo "============================================"
echo " Done! Commit and push changes:"
echo "   git add ."
echo "   git commit -m 'Fix UTF-8 encoding'"
echo "   git push"
echo "============================================"
