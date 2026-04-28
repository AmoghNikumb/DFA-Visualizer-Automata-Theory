# DFA Visualizer Phase 4 — Build & Run Instructions

## ⚡ Quick Start

### Option 1: Batch Script (Windows) — **RECOMMENDED**
No additional tools required. Run from Command Prompt or PowerShell:

```powershell
# Navigate to project directory
cd dfa-visualizer-phase4

# Launch the JavaFX UI app
.\build.bat ui

# Run tests
.\build.bat phase1    # Phase 1 tests
.\build.bat phase2    # Phase 2 tests
.\build.bat test      # TestEvenOdd tests
```

### Option 2: PowerShell Script (Windows/Linux/macOS)

```powershell
cd dfa-visualizer-phase4
powershell -ExecutionPolicy Bypass -File build.ps1 ui      # Launch UI
powershell -ExecutionPolicy Bypass -File build.ps1 phase1  # Phase 1 tests
powershell -ExecutionPolicy Bypass -File build.ps1 phase2  # Phase 2 tests
powershell -ExecutionPolicy Bypass -File build.ps1 test    # EvenOdd tests
```

### Option 3: Shell Script (Linux/macOS)

```bash
cd dfa-visualizer-phase4
chmod +x build.sh
./build.sh ui      # Launch UI
./build.sh phase1  # Phase 1 tests
./build.sh phase2  # Phase 2 tests
./build.sh test    # EvenOdd tests
```

### Option 4: Manual Commands

If you prefer raw `javac`/`java` commands:

```powershell
$JAVAFX_PATH = "C:\Users\ADMIN\Downloads\openjfx-17.0.14_windows-x64_bin-sdk\javafx-sdk-17.0.14\lib"

# Compile
javac -encoding UTF-8 --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -d out-cli src/**/*.java
javac -encoding UTF-8 -cp out-cli -d out-cli TestEvenOdd.java

# Run
java --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -cp out-cli ui.MainApp
java -cp out-cli Phase1Test
java -cp out-cli TestEvenOdd
```

## 📦 Build Artifacts

After running any build script:
- **out-cli/** — Compiled `.class` files
- **All classes** compile successfully with no external build tool requirements

## 🏗️ Available Targets

| Target   | Command          | Description                |
|----------|------------------|----------------------------|
| `ui`     | `build.bat ui`   | Launch JavaFX visualizer   |
| `phase1` | `build.bat phase1` | Run Phase 1 simulation tests |
| `phase2` | `build.bat phase2` | Run Phase 2 generator tests |
| `test`   | `build.bat test` | Run EvenOdd standalone tests |

## 📋 System Requirements

- **Java 17+** (OpenJDK 17.0.18 verified working)
- **JavaFX 17.0.14** (auto-detected at compile/run time)
- **No additional tools needed** (Maven, Gradle, etc. are optional)

## 📂 Project Structure

```
src/
├── model/              DFA data structures
│   ├── DFA.java
│   ├── State.java
│   └── Transition.java
├── simulator/          DFA simulation engine
│   └── DFASimulator.java
├── generator/          Pattern-based DFA generators
│   ├── DFAGenerator.java
│   ├── EvenOddGenerator.java
│   ├── EndsWithGenerator.java
│   ├── StartsWithGenerator.java
│   └── ContainsGenerator.java
├── ui/                 JavaFX UI components
│   ├── MainApp.java       (entry point)
│   ├── GraphPane.java     (DFA visualization)
│   ├── InputPanel.java    (user input)
│   ├── ControlPanel.java  (simulation controls)
│   └── TransitionTable.java
├── utils/              Utilities
│   └── GraphLayout.java   (Phase 4: smart state positioning)
└── resources/
    └── styles.css

Test Files:
├── Phase1Test.java     (simulation tests)
├── Phase2Test.java     (generator tests)
└── TestEvenOdd.java    (standalone test)

Build Files:
├── pom.xml            (Maven config — optional)
├── build.gradle       (Gradle config — optional)
├── build.bat          (Batch script — Windows)
├── build.ps1          (PowerShell script — all platforms)
└── build.sh           (Shell script — Linux/macOS)
```

## ✨ Phase 4 Features

✅ Smart layout: row / two-row / circular (based on state count)  
✅ Radial gradient fill on every state circle  
✅ Glow + DropShadow effects on highlighted states  
✅ Bidirectional curved arrows (QuadCurve) for back-transitions  
✅ CubicCurve self-loops above states  
✅ Multi-symbol label merging (e.g., "a,b" on one arrow)  
✅ Dashed start arrow on start state  
✅ Canvas auto-redraws on window resize

## 🔧 Troubleshooting

### "Java not found"
- Install OpenJDK 17 or later
- Add Java `bin` directory to system PATH

### "JavaFX SDK not found"
- Default path: `C:\Users\ADMIN\Downloads\openjfx-17.0.14_windows-x64_bin-sdk\javafx-sdk-17.0.14\lib`
- Edit the script and update `JAVAFX_PATH` to your JavaFX installation location
- Or download: [openjfx.io](https://openjfx.io/)

### Compilation errors (Unicode characters)
- Build scripts automatically use UTF-8 encoding
- If using manual `javac`: add `-encoding UTF-8` flag

### Port already in use (UI won't start)
- Make sure no other instance is running
- Check for processes on the JavaFX port

