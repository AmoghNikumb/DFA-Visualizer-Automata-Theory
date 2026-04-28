@echo off
REM ──────────────────────────────────────────────────────────────────────
REM  DFA Visualizer Phase 4 — Build & Run Script (Batch)
REM ──────────────────────────────────────────────────────────────────────

setlocal enabledelayedexpansion

REM Configuration
set "JAVAFX_PATH=C:\Users\ADMIN\Downloads\openjfx-17.0.14_windows-x64_bin-sdk\javafx-sdk-17.0.14\lib"
set "OUTPUT_DIR=out-cli"
set "ENCODING=UTF-8"

echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║          DFA Visualizer Phase 4 - Build & Run               ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.

REM Parse command-line argument
set "TARGET=%1"
if "!TARGET!"=="" (
    set "TARGET=ui"
)

REM Verify Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo [x] Java not found. Please install Java 17+.
    exit /b 1
)
echo [OK] Java found.

REM Verify JavaFX SDK path exists
if not exist "!JAVAFX_PATH!" (
    echo [x] JavaFX SDK not found at: !JAVAFX_PATH!
    exit /b 1
)
echo [OK] JavaFX SDK found.
echo.

REM Clean and create output directory
if exist "!OUTPUT_DIR!" (
    echo [INFO] Cleaning output directory...
    rmdir /s /q "!OUTPUT_DIR!"
)
mkdir "!OUTPUT_DIR!"

REM Compile all source files
echo [INFO] Compiling source files...
for /r "src" %%f in (*.java) do (
    set "SRC_FILES=!SRC_FILES! %%f"
)

javac -encoding !ENCODING! ^
    --module-path "!JAVAFX_PATH!" ^
    --add-modules javafx.controls,javafx.fxml ^
    -d "!OUTPUT_DIR!" ^
    !SRC_FILES!

if errorlevel 1 (
    echo [x] Compilation failed!
    exit /b 1
)

echo [OK] Compilation successful.

REM Compile standalone test file if it exists
if exist "TestEvenOdd.java" (
    echo [INFO] Compiling TestEvenOdd...
    javac -encoding !ENCODING! -cp "!OUTPUT_DIR!" -d "!OUTPUT_DIR!" TestEvenOdd.java
    if errorlevel 1 (
        echo [x] TestEvenOdd compilation failed!
        exit /b 1
    )
)

echo [OK] All compilations successful.
echo.

REM Execute target
echo [INFO] Running target: !TARGET!
echo.

if /i "!TARGET!"=="ui" (
    java --module-path "!JAVAFX_PATH!" ^
        --add-modules javafx.controls,javafx.fxml ^
        -cp "!OUTPUT_DIR!" ^
        ui.MainApp
) else if /i "!TARGET!"=="phase1" (
    java -cp "!OUTPUT_DIR!" Phase1Test
) else if /i "!TARGET!"=="phase2" (
    java -cp "!OUTPUT_DIR!" Phase2Test
) else if /i "!TARGET!"=="test" (
    java -cp "!OUTPUT_DIR!" TestEvenOdd
) else (
    echo [x] Unknown target: !TARGET!
    echo    Valid targets: ui, phase1, phase2, test
    exit /b 1
)

if errorlevel 1 (
    echo.
    echo [x] Execution failed!
    exit /b 1
) else (
    echo.
    echo [OK] Execution completed successfully.
    exit /b 0
)
