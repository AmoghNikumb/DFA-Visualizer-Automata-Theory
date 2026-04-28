#!/usr/bin/env pwsh

# ──────────────────────────────────────────────────────────────────────
#  DFA Visualizer Phase 4 — Build & Run Script (PowerShell)
# ──────────────────────────────────────────────────────────────────────

$ErrorActionPreference = "Stop"

# Configuration
$JAVAFX_PATH = "C:\Users\ADMIN\Downloads\openjfx-17.0.14_windows-x64_bin-sdk\javafx-sdk-17.0.14\lib"
$OUTPUT_DIR = "out-cli"
$ENCODING = "UTF-8"

# Parse arguments
$TARGET = if ($args.Count -gt 0) { $args[0] } else { "ui" }

Write-Host ""
Write-Host "╔══════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║          DFA Visualizer Phase 4 - Build & Run               ║" -ForegroundColor Cyan
Write-Host "╚══════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Verify Java is installed
$javaCheck = java -version 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "[✗] Java not found. Please install Java 17+." -ForegroundColor Red
    exit 1
}
Write-Host "[✓] Java found" -ForegroundColor Green

# Verify JavaFX SDK path exists
if (-not (Test-Path $JAVAFX_PATH)) {
    Write-Host "[✗] JavaFX SDK not found at: $JAVAFX_PATH" -ForegroundColor Red
    Write-Host "    Please install JavaFX 17 or update the path in this script." -ForegroundColor Yellow
    exit 1
}

Write-Host "[✓] JavaFX SDK found at: $JAVAFX_PATH" -ForegroundColor Green
Write-Host ""

# Create output directory
if (Test-Path $OUTPUT_DIR) {
    Write-Host "[INFO] Cleaning output directory..." -ForegroundColor Yellow
    Remove-Item -Recurse -Force $OUTPUT_DIR | Out-Null
}
New-Item -ItemType Directory -Path $OUTPUT_DIR | Out-Null

# Compile all source files
Write-Host "[INFO] Compiling source files..." -ForegroundColor Yellow
$srcFiles = Get-ChildItem -Path "src" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
javac -encoding $ENCODING `
    --module-path "$JAVAFX_PATH" `
    --add-modules javafx.controls,javafx.fxml `
    -d $OUTPUT_DIR `
    $srcFiles

if ($LASTEXITCODE -ne 0) {
    Write-Host "[✗] Compilation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "[✓] Compilation successful" -ForegroundColor Green

# Compile standalone test file if it exists
if (Test-Path "TestEvenOdd.java") {
    Write-Host "[INFO] Compiling TestEvenOdd..." -ForegroundColor Yellow
    javac -encoding $ENCODING -cp $OUTPUT_DIR -d $OUTPUT_DIR TestEvenOdd.java
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[✗] TestEvenOdd compilation failed!" -ForegroundColor Red
        exit 1
    }
}

Write-Host "[✓] All compilations successful" -ForegroundColor Green
Write-Host ""

# Execute target
Write-Host "[INFO] Running target: $TARGET" -ForegroundColor Yellow
Write-Host ""

switch ($TARGET.ToLower()) {
    "ui" {
        java --module-path "$JAVAFX_PATH" `
            --add-modules javafx.controls,javafx.fxml `
            -cp $OUTPUT_DIR `
            ui.MainApp
    }
    "phase1" { java -cp $OUTPUT_DIR Phase1Test }
    "phase2" { java -cp $OUTPUT_DIR Phase2Test }
    "test" { java -cp $OUTPUT_DIR TestEvenOdd }
    default {
        Write-Host "[✗] Unknown target: $TARGET" -ForegroundColor Red
        Write-Host "    Valid targets: ui, phase1, phase2, test" -ForegroundColor Yellow
        exit 1
    }
}

Write-Host ""
Write-Host "[✓] Execution completed successfully." -ForegroundColor Green
Write-Host ""
