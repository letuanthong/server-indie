# ============================================================
# Fix SQL for Linux MySQL/MariaDB - PowerShell
# Xử lý toàn bộ \r\n literal trong SQL strings
# Usage: .\deploy\fix-sql.ps1
# ============================================================

Write-Host "============================================" -ForegroundColor Cyan
Write-Host " Fixing SQL Files (comprehensive)" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

$sqlFiles = @("sql\whis_1.sql", "sql\whis_2.sql")

foreach ($file in $sqlFiles) {
    if (Test-Path $file) {
        Write-Host "`nProcessing: $file" -ForegroundColor Yellow
        
        $content = [System.IO.File]::ReadAllText((Resolve-Path $file), [System.Text.Encoding]::UTF8)
        $originalSize = $content.Length
        
        # 1. Fix actual CRLF line endings to LF
        $content = $content -replace "`r`n", "`n"
        
        # 2. Replace literal \r\n (4 chars) inside SQL strings with \n
        #    phpMyAdmin exports \r\n but MySQL on Linux chokes on \r
        $content = $content -replace '\\r\\n', '\n'
        
        # 3. Fix trailing single quote issues: 'text'' -> 'text\'
        $content = $content -replace "(\\'[^']*?)''(?=,\s*\d)", "`$1\'"
        
        # 4. Save as UTF-8 without BOM
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        
        # Backup original
        Copy-Item $file "$file.bak" -Force
        
        # Write fixed file
        [System.IO.File]::WriteAllText((Resolve-Path $file), $content, $utf8NoBom)
        
        $newSize = (Get-Item $file).Length
        $diff = $originalSize - $newSize
        Write-Host "  OK Size: $originalSize -> $newSize bytes (diff: $diff)" -ForegroundColor Green
        Write-Host "  OK Backup: $file.bak" -ForegroundColor Green
    } else {
        Write-Host "  SKIP Not found: $file" -ForegroundColor Red
    }
}

# Verify no more \r\n literals
Write-Host "`nVerifying..." -ForegroundColor Yellow
foreach ($file in $sqlFiles) {
    if (Test-Path $file) {
        $content = [System.IO.File]::ReadAllText((Resolve-Path $file), [System.Text.Encoding]::UTF8)
        $count = ([regex]::Matches($content, '\\r\\n')).Count
        if ($count -eq 0) {
            Write-Host "  OK $file - No \r\n found (clean!)" -ForegroundColor Green
        } else {
            Write-Host "  FAIL $file - Still has $count \r\n occurrences" -ForegroundColor Red
        }
    }
}

Write-Host "`n============================================" -ForegroundColor Cyan
Write-Host " Done! Commit and push:" -ForegroundColor Green
Write-Host "   git add sql/" -ForegroundColor White
Write-Host "   git commit -m 'Fix SQL encoding for Linux'" -ForegroundColor White
Write-Host "   git push" -ForegroundColor White
Write-Host "============================================" -ForegroundColor Cyan
