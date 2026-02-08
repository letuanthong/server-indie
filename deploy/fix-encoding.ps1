# ============================================================
# Fix UTF-8 Encoding Issues - Windows PowerShell Version
# Usage: .\deploy\fix-encoding.ps1
# ============================================================

Write-Host "============================================" -ForegroundColor Cyan
Write-Host " Fixing UTF-8 Encoding (Windows)" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

# 1. Git config
Write-Host ""
Write-Host "[1/3] Configuring Git..." -ForegroundColor Yellow
git config core.quotepath false
git config i18n.commitencoding utf-8
git config i18n.logoutputencoding utf-8
git config core.autocrlf false
Write-Host "  → Git encoding configured" -ForegroundColor Green

# 2. Fix line endings for shell scripts and text files
Write-Host ""
Write-Host "[2/3] Fixing line endings (CRLF → LF)..." -ForegroundColor Yellow

$files = @(
    "README.md",
    ".env.example",
    "deploy\setup.sh",
    "deploy\start.sh",
    "deploy\stop.sh",
    "deploy\backup.sh",
    "deploy\rebuild.sh",
    "deploy\install-service.sh",
    "deploy\fix-encoding.sh"
)

foreach ($file in $files) {
    if (Test-Path $file) {
        $content = Get-Content $file -Raw
        # Replace CRLF with LF
        $content = $content -replace "`r`n", "`n"
        # Ensure UTF-8 without BOM
        $utf8NoBom = New-Object System.Text.UTF8Encoding $false
        [System.IO.File]::WriteAllText((Resolve-Path $file), $content, $utf8NoBom)
        Write-Host "  ✓ Fixed: $file" -ForegroundColor Green
    }
}

# 3. Set executable permissions (Git metadata)
Write-Host ""
Write-Host "[3/3] Setting Git executable mode..." -ForegroundColor Yellow
$shellScripts = Get-ChildItem -Path "deploy" -Filter "*.sh"
foreach ($script in $shellScripts) {
    git update-index --chmod=+x "deploy/$($script.Name)"
}
Write-Host "  → All scripts marked as executable" -ForegroundColor Green

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host " Done! Now commit and push:" -ForegroundColor Green
Write-Host "   git add ." -ForegroundColor White
Write-Host "   git commit -m 'Fix UTF-8 encoding'" -ForegroundColor White
Write-Host "   git push" -ForegroundColor White
Write-Host "============================================" -ForegroundColor Cyan
