# DFA Visualizer – Phase 4

A modern **Deterministic Finite Automaton (DFA) Visualizer** built with JavaFX. Visualize and simulate DFAs with interactive state diagrams, pattern matching, and real-time state transitions.

## 🎯 Features

### Phase 4 Enhancements
- ✅ **Smart Layout Algorithms** – Automatic state positioning (row, two-row, circular based on state count)
- ✅ **Advanced Graphics** – Radial gradients, glow effects, drop shadows on highlighted states
- ✅ **Curved Transitions** – Bidirectional curved arrows and self-loop support with CubicCurve
- ✅ **Multi-Symbol Labels** – Merged symbol labels on transitions (e.g., "a,b")
- ✅ **Visual Start Arrow** – Dashed arrow indicator on start state
- ✅ **Responsive Design** – Auto-redraws on window resize

### Supported DFA Generators
- **Even/Odd Numbers** – Accept strings with even/odd symbol counts
- **Contains Pattern** – Detect specific substrings
- **Starts With** – Match prefix patterns
- **Ends With** – Match suffix patterns
- **Custom Generators** – Extensible architecture for user-defined DFAs

### Core Capabilities
- 🔍 Real-time DFA simulation
- 📊 Interactive transition table
- 🎨 Styled UI with custom CSS
- ✔️ Comprehensive test suite (Phase 1, 2, 3 tests included)

---

## 📋 Prerequisites

### System Requirements
- **Java 17+** (LTS recommended)
- **JavaFX SDK 17.0.14** (⚠️ JavaFX 26 is incompatible with JDK 17)
- **IntelliJ IDEA** (optional; any IDE with JavaFX support works)

### Download JavaFX
```bash
# Download from: https://openjfx.io
# Or use: https://openjfx.io/openjfx-docs/
```

---

## 🚀 Quick Start

### Windows (Recommended)
```powershell
cd dfa-visualizer-phase4
.\build.bat ui
```

### All Platforms (PowerShell)
```powershell
powershell -ExecutionPolicy Bypass -File build.ps1 ui
```

### Linux/macOS (Bash)
```bash
chmod +x build.sh
./build.sh ui
```

### Manual Build & Run
```powershell
$JAVAFX_PATH = "C:\path\to\javafx-sdk-17.0.14\lib"
javac -encoding UTF-8 --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -d out-cli src/**/*.java
java --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -cp out-cli ui.MainApp
```

---

## 🧪 Running Tests

```powershell
# Phase 1 tests
.\build.bat phase1

# Phase 2 tests
.\build.bat phase2

# Even/Odd tests
.\build.bat test
```

---

## 📁 Project Structure

```
dfa-visualizer-phase4/
├── src/
│   ├── model/              # Core DFA data structures
│   │   ├── DFA.java
│   │   ├── State.java
│   │   └── Transition.java
│   ├── simulator/          # DFA simulation engine
│   │   └── DFASimulator.java
│   ├── generator/          # DFA generators
│   │   ├── DFAGenerator.java
│   │   ├── EvenOddGenerator.java
│   │   ├── ContainsGenerator.java
│   │   ├── StartsWithGenerator.java
│   │   └── EndsWithGenerator.java
│   ├── ui/                 # JavaFX UI components
│   │   ├── MainApp.java
│   │   ├── GraphPane.java
│   │   ├── InputPanel.java
│   │   ├── ControlPanel.java
│   │   └── TransitionTable.java
│   ├── utils/              # Graph layout utilities
│   │   └── GraphLayout.java
│   ├── resources/
│   │   └── styles.css
│   ├── Phase1Test.java
│   └── Phase2Test.java
├── build.bat               # Windows build script
├── build.ps1               # PowerShell build script
├── build.sh                # Bash build script
├── BUILD.md                # Detailed build documentation
├── pom.xml                 # Maven config (optional)
└── build.gradle            # Gradle config (optional)
```

---

## 💻 Usage

### Launch the Application
1. Run using one of the methods above
2. Select a DFA generator from the dropdown
3. Enter an input string
4. Click **Simulate** to visualize state transitions
5. Watch the graph highlight accepting states in real-time

### UI Components
- **GraphPane** – Animated DFA visualization with interactive graph
- **InputPanel** – String input and DFA generator selection
- **ControlPanel** – Simulation controls and step-through debugging
- **TransitionTable** – Detailed transition rules and state information

---

## ⚙️ Build System

### Available Build Scripts
| Script | Platforms | Usage |
|--------|-----------|-------|
| `build.bat` | Windows | `.\build.bat ui` |
| `build.ps1` | All | `powershell -File build.ps1 ui` |
| `build.sh` | Linux/macOS | `./build.sh ui` |

### Build Targets
- `ui` – Compile and run the JavaFX application
- `phase1` – Run Phase 1 tests
- `phase2` – Run Phase 2 tests
- `test` – Run TestEvenOdd tests
- `clean` – Remove compiled files

### Configuration
- **UTF-8 Encoding** – All build scripts use UTF-8 for proper Unicode handling
- **Module Path** – Configured for JavaFX modular system
- **Output Directory** – Compiled classes go to `out-cli/`

---

## 🛠️ Development Setup (IntelliJ IDEA)

### Step 1: Create Project
1. File → New Project → JavaFX Application
2. Name: `dfa-visualizer-phase4`
3. Choose Java 17+ as SDK

### Step 2: Add Source Files
1. Copy `src/` contents to your project's `src/`

### Step 3: Configure JavaFX
1. File → Project Structure → Libraries
2. Click `+` → Java
3. Navigate to `javafx-sdk-17.0.14/lib` → Select all `.jar` files
4. Click OK

### Step 4: Configure Run Configuration
1. Run → Edit Configurations
2. Main class: `ui.MainApp`
3. VM Options:
   ```
   --module-path /path/to/javafx-sdk-17.0.14/lib --add-modules javafx.controls,javafx.fxml
   ```
4. Click OK and Run

---

## 📚 Technical Details

### DFA Model
- **State** – Represents a DFA state with accept/reject flag
- **Transition** – Defines state transitions on input symbols
- **DFA** – Core automaton with start state and transition function

### Simulation Engine
- **DFASimulator** – Step-by-step execution with state tracking
- Supports dynamic symbol sets and state configuration

### Graph Layout Algorithms
- **Row Layout** – Linear arrangement for small DFAs (≤5 states)
- **Two-Row Layout** – Balanced distribution for medium DFAs (6-10 states)
- **Circular Layout** – Ring arrangement for large DFAs (>10 states)

### Visualization Effects
- Radial gradients on state circles
- Glow effect on active states
- Drop shadow for depth
- Curved arrows for bidirectional transitions
- CubicCurve self-loops

---

## 📝 Testing

The project includes comprehensive test suites:

### Phase 1 Tests
Tests for basic DFA model and simulator functionality

### Phase 2 Tests
Extended tests for generator implementations

### Phase 3 & 4 Tests
UI integration and visual rendering tests

Run all tests:
```powershell
.\build.bat phase1
.\build.bat phase2
.\build.bat test
```

---

## ⚠️ Important Notes

### JavaFX Compatibility
- ✅ JavaFX 17.0.14 works with JDK 17
- ❌ JavaFX 26 is NOT compatible with JDK 17
- Always use matching major versions (e.g., JDK 17 + JavaFX 17)

### Module Path
If you encounter module errors, verify:
```
--module-path /path/to/javafx-sdk-17.0.14/lib
--add-modules javafx.controls,javafx.fxml
```

### Encoding Issues
All scripts compile with UTF-8 encoding to support Unicode test strings.

---

## 🌐 Deployment Notes

**⚠️ Important:** This is a **desktop JavaFX application**, not a web application. It cannot be directly deployed to Vercel.

### Options for Online Deployment

1. **Self-Hosted Java Server** (Recommended for Desktop App)
   - Deploy to AWS EC2, DigitalOcean, or similar
   - Users run as a desktop application

2. **Convert to Web Application**
   - Rewrite UI with React/Vue/Angular
   - Use Node.js backend with REST API
   - Then deploy to Vercel (frontend) + serverless function (backend)

3. **Docker Container**
   - Containerize the JavaFX app
   - Deploy to Docker Hub or cloud container registry
   - Users run locally or via Docker

### GitHub Repository Setup
This README is optimized for GitHub:
```bash
git init
git add .
git commit -m "Initial DFA Visualizer Phase 4 commit"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/dfa-visualizer.git
git push -u origin main
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is provided as-is for educational purposes.

---

## 📧 Support

For issues, questions, or suggestions:
1. Check [BUILD.md](BUILD.md) for detailed build instructions
2. Review test files for usage examples
3. Create an issue on GitHub

---

## 🔄 Version History

### Phase 4
- Smart layout algorithms
- Advanced graphics effects
- Improved UI responsiveness

### Phase 3
- Extended generator support
- Comprehensive test suite

### Phase 1 & 2
- Core DFA model and simulator
- Basic UI implementation

---

## 🎓 Educational Value

This project demonstrates:
- **Formal Language Theory** – DFA implementation and simulation
- **JavaFX Graphics** – Advanced 2D rendering and animation
- **Software Architecture** – Modular design with clear separation of concerns
- **Test-Driven Development** – Comprehensive test coverage
- **Build Automation** – Multi-platform build scripts

---

**Happy automaton visualizing! 🤖**
