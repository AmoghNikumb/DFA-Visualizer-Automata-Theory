package ui;

import generator.DFAGenerator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.DFA;

/**
 * InputPanel — TOP section of the UI.
 *
 * Contains:
 *   - Condition field  (e.g. "ends with abb")
 *   - Input string     (e.g. "aabbabb")
 *   - Alphabet field   (e.g. "ab")
 *   - [Generate DFA]   button
 *   - [Start Simulation] button
 *   - Status label
 */
public class InputPanel extends VBox {

    // ── Fields ───────────────────────────────────────────────────────────────
    private final TextField conditionField;
    private final TextField inputStringField;
    private final TextField alphabetField;
    private final Label     statusLabel;

    // ── References to other panels ───────────────────────────────────────────
    private final GraphPane       graphPane;
    private final TransitionTable transitionTable;
    private final ControlPanel    controlPanel;

    // ── Current DFA ──────────────────────────────────────────────────────────
    private DFA currentDFA;

    public InputPanel(GraphPane graphPane,
                      TransitionTable transitionTable,
                      ControlPanel controlPanel) {

        this.graphPane       = graphPane;
        this.transitionTable = transitionTable;
        this.controlPanel    = controlPanel;
        controlPanel.setVisualRefs(graphPane, transitionTable);

        // ── Title ────────────────────────────────────────────────────────────
        Label title = new Label("🔷 DFA Visualizer");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: #6c63ff; -fx-effect: dropshadow(gaussian, rgba(108, 99, 255, 0.6), 10, 0, 0, 0);");

        // ── Input fields ─────────────────────────────────────────────────────
        conditionField    = styledField("ends with abb", 280);
        inputStringField  = styledField("aabbabb",       180);
        alphabetField     = styledField("ab",             80);

        // ── Labels ───────────────────────────────────────────────────────────
        HBox row = new HBox(12,
            labeledField("Condition",    conditionField),
            labeledField("Input String", inputStringField),
            labeledField("Alphabet",     alphabetField)
        );
        row.setAlignment(Pos.CENTER_LEFT);

        // ── Buttons ──────────────────────────────────────────────────────────
        Button generateBtn  = styledButton("⚙  Generate DFA",    "#89b4fa");
        Button simulateBtn  = styledButton("▶  Start Simulation", "#a6e3a1");

        generateBtn.setOnAction(e -> onGenerate());
        simulateBtn.setOnAction(e -> onSimulate());

        HBox buttons = new HBox(10, generateBtn, simulateBtn);
        buttons.setAlignment(Pos.CENTER_LEFT);

        // ── Status label ─────────────────────────────────────────────────────
        statusLabel = new Label("Enter a condition and click Generate DFA.");
        statusLabel.setStyle("-fx-text-fill: #b0b0d0; -fx-font-size: 13px;");

        // ── Hint label ───────────────────────────────────────────────────────
        Label hint = new Label(
            "💡 Conditions: ends with · starts with · contains · even/odd number of"
        );
        hint.setStyle("-fx-text-fill: #7070a0; -fx-font-size: 11px;");

        // ── Layout ───────────────────────────────────────────────────────────
        HBox topRow = new HBox(20, title, row, buttons);
        topRow.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(topRow, statusLabel, hint);
        setSpacing(8);
        setPadding(new Insets(16, 20, 14, 20));
        setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e 0%, #1e1e3f 100%); " +
                 "-fx-border-color: #3a3a5a; -fx-border-width: 0 0 2 0; " +
                 "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 8, 0, 0, 2);");
    }

    // ── Actions ──────────────────────────────────────────────────────────────

    private void onGenerate() {
        String condition = conditionField.getText().trim();
        String alphabet  = alphabetField.getText().trim();

        if (condition.isEmpty() || alphabet.isEmpty()) {
            setStatus("⚠  Please fill in Condition and Alphabet.", "#fab387");
            return;
        }

        try {
            currentDFA = DFAGenerator.generate(condition, alphabet);
            graphPane.drawDFA(currentDFA);
            transitionTable.populate(currentDFA);
            controlPanel.setDFA(currentDFA, inputStringField.getText().trim());
            setStatus("✅  DFA generated for: \"" + condition + "\"", "#a6e3a1");
        } catch (IllegalArgumentException ex) {
            setStatus("❌  " + ex.getMessage(), "#f38ba8");
        }
    }

    private void onSimulate() {
        if (currentDFA == null) {
            setStatus("⚠  Generate a DFA first.", "#fab387");
            return;
        }
        String input = inputStringField.getText().trim();
        controlPanel.startSimulation(currentDFA, input);
        setStatus("▶  Simulating: \"" + input + "\"", "#89dceb");
    }

    private void setStatus(String msg, String color) {
        statusLabel.setText(msg);
        statusLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 12px;");
    }

    // ── UI Helpers ───────────────────────────────────────────────────────────

    private TextField styledField(String prompt, double width) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setText(prompt);
        tf.setPrefWidth(width);
        tf.setStyle(
            "-fx-background-color: #1e1e3f;" +
            "-fx-text-fill: #e0e0ff;" +
            "-fx-prompt-text-fill: #6c6c8e;" +
            "-fx-border-color: #3a3a5a;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 12 8 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 4, 0, 0, 2);"
        );
        return tf;
    }

    private VBox labeledField(String labelText, TextField field) {
        Label lbl = new Label(labelText);
        lbl.setStyle("-fx-text-fill: #b0b0d0; -fx-font-size: 11px; -fx-font-weight: bold;");
        VBox box = new VBox(4, lbl, field);
        return box;
    }

    private Button styledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #2a2a4a 0%, #1e1e3f 100%);" +
            "-fx-text-fill: " + color + ";" +
            "-fx-border-color: " + color + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 9 18 9 18;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 4, 0, 0, 2);"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: linear-gradient(to bottom, " + color + " 0%, " + adjustBrightness(color, -20) + " 100%);" +
            "-fx-text-fill: #ffffff;" +
            "-fx-border-color: " + color + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 9 18 9 18;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, " + toRgba(color, 0.6) + ", 10, 0, 0, 0);"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #2a2a4a 0%, #1e1e3f 100%);" +
            "-fx-text-fill: " + color + ";" +
            "-fx-border-color: " + color + ";" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 9 18 9 18;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 4, 0, 0, 2);"
        ));
        return btn;
    }

    private String adjustBrightness(String hex, int amount) {
        // Simple brightness adjustment for hex colors
        return hex; // Simplified for now
    }

    private String toRgba(String hex, double alpha) {
        // Convert hex to rgba with alpha
        if (hex.startsWith("#") && hex.length() == 7) {
            int r = Integer.parseInt(hex.substring(1, 3), 16);
            int g = Integer.parseInt(hex.substring(3, 5), 16);
            int b = Integer.parseInt(hex.substring(5, 7), 16);
            return String.format("rgba(%d, %d, %d, %.1f)", r, g, b, alpha);
        }
        return "rgba(108, 99, 255, " + alpha + ")";
    }

    public DFA getCurrentDFA() { return currentDFA; }
}
