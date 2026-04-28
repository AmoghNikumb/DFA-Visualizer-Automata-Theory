#!/bin/bash

# ──────────────────────────────────────────────────────────────────────
#  DFA Visualizer Phase 4 — Build & Run Script (Linux/macOS)
# ──────────────────────────────────────────────────────────────────────

set -e

echo ""
echo "╔══════════════════════════════════════════════════════════════╗"
echo "║          DFA Visualizer Phase 4 - Build & Run               ║"
echo "╚══════════════════════════════════════════════════════════════╝"
echo ""

# Check if Gradle wrapper exists
if [ ! -f "gradlew" ]; then
    echo "[INFO] Gradle wrapper not found. Installing..."
    gradle wrapper --gradle-version 8.7
fi

# Parse command-line argument
TARGET=${1:-ui}

echo "[INFO] Target: $TARGET"
echo "[INFO] Running: ./gradlew run$TARGET"
echo ""

./gradlew "run$TARGET" -q

if [ $? -eq 0 ]; then
    echo ""
    echo "[SUCCESS] Execution completed successfully."
    exit 0
else
    echo ""
    echo "[ERROR] Build or execution failed. See output above."
    exit 1
fi
