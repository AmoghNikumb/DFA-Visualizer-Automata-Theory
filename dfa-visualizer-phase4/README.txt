=== DFA Visualizer — Phase 1 + 2 + 3 + 4 ===

REQUIREMENTS:
─────────────
  - Java 17+
  - JavaFX SDK 17+  →  https://openjfx.io
  - IntelliJ IDEA

HOW TO RUN IN IntelliJ IDEA:
──────────────────────────────
1. File → New Project → JavaFX  (or plain Java)
2. Copy src/ contents into your project's src/
3. Add JavaFX library:
   File → Project Structure → Libraries → + → javafx-sdk/lib
4. Run → Edit Configurations → VM Options:
   --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
5. Main class: ui.MainApp
6. Run!

PROJECT STRUCTURE:
──────────────────
src/
├── module-info.java
├── Phase1Test.java
├── Phase2Test.java
├── model/          State · Transition · DFA
├── simulator/      DFASimulator
├── generator/      DFAGenerator · EndsWithGenerator · StartsWithGenerator
│                   ContainsGenerator · EvenOddGenerator
├── utils/
│   └── GraphLayout.java     ← NEW Phase 4: smart state positioning
├── ui/
│   ├── MainApp.java
│   ├── InputPanel.java
│   ├── GraphPane.java       ← UPGRADED Phase 4: polished graph rendering
│   ├── TransitionTable.java
│   └── ControlPanel.java
└── resources/
    └── styles.css

WHAT PHASE 4 ADDS:
──────────────────
  ✅ Smart layout: row / two-row / circular (based on state count)
  ✅ Radial gradient fill on every state circle
  ✅ Glow + DropShadow effect on highlighted states
  ✅ Bidirectional curved arrows (QuadCurve) for back-transitions
  ✅ CubicCurve self-loops above states
  ✅ Multi-symbol label merging (e.g. "a,b" on one arrow)
  ✅ Dashed start arrow on start state
  ✅ Canvas auto-redraws on window resize
