# ============================================================
# Fix SQL Syntax Errors - PowerShell
# Usage: .\deploy\fix-sql.ps1
# ============================================================

Write-Host "============================================" -ForegroundColor Cyan
Write-Host " Fixing SQL Syntax Errors" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

$sqlFiles = @("sql\whis_1.sql", "sql\whis_2.sql")

foreach ($file in $sqlFiles) {
    if (Test-Path $file) {
        Write-Host "`nProcessing: $file" -ForegroundColor Yellow
        
        $content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
        $originalSize = $content.Length
        
        # 1. Fix CRLF → LF
        $content = $content -replace "`r`n", "`n"
        
        # 2. Fix newlines INSIDE string literals (MySQL doesn't allow this)
        # Pattern: 'text\r\nmore text' → 'text more text'
        $content = $content -replace "([^']*)\\r\\n([^']*)", "`$1 `$2"
        
        # 3. Fix escape quotes trong description
        # Tìm pattern: 'text \'escaped\' more text'' và fix thành 'text \'escaped\' more text\'
        $content = $content -replace "(\\'[^']*?)''(?=,\s*\d)", "`$1\'"
        
        # 3. Fix unescaped single quotes trong text Việt
        # Pattern: ', 'Text với dấu ' lỗi',
        # Không tự động fix vì có thể sai - cần manual check
        
        # 4. Đảm bảo UTF-8 without BOM
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        $outputPath = $file -replace '\.sql$', '_fixed.sql'
        [System.IO.File]::WriteAllText($outputPath, $content, $utf8NoBom)
        
        $newSize = (Get-Item $outputPath).Length
        Write-Host "  ✓ Original: $originalSize bytes" -ForegroundColor Green
        Write-Host "  ✓ Fixed: $newSize bytes" -ForegroundColor Green
        Write-Host "  ✓ Saved to: $outputPath" -ForegroundColor Green
        
        # Backup original
        Copy-Item $file "$file.bak" -Force
        # Replace original
        Move-Item $outputPath $file -Force
        
        Write-Host "  ✓ Replaced original (backup: $file.bak)" -ForegroundColor Green
    }
}

Write-Host "`n============================================" -ForegroundColor Cyan
Write-Host " Done! SQL files fixed." -ForegroundColor Green
Write-Host " Commit and push to GitHub." -ForegroundColor White
Write-Host "============================================" -ForegroundColor Cyan
