package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.DFA;
import model.State;

import java.util.*;

/**
 * TransitionTable — RIGHT section of the UI.
 *
 * Displays the DFA transition table as a styled grid.
 * During simulation, highlights the active row (current state)
 * and the active column (current symbol).
 *
 * Layout:
 *   ┌────────────────────────────┐
 *   │  Transition Table          │
 *   ├──────────┬────────┬────────┤
 *   │  State   │   a    │   b    │
 *   ├──────────┼────────┼────────┤
 *   │ ->q0     │  q1    │  q0    │
 *   │  q1      │  q1    │  q2    │
 *   │  q2      │  q1    │ (q3)   │
 *   └──────────┴────────┴────────┘
 */
public class TransitionTable extends VBox {

    private GridPane grid;
    private DFA      currentDFA;

    // Tracks label nodes for highlight access: [row][col]
    private final List<List<Label>> cells = new ArrayList<>();

    public TransitionTable() {
        setStyle("-fx-background-color: linear-gradient(to right, #1a1a2e 0%, #1e1e3f 100%); " +
                 "-fx-border-color: #3a3a5a; -fx-border-width: 0 0 0 2; " +
                 "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 8, 0, -2, 0);");
        setPrefWidth(240);
        setPadding(new Insets(16, 14, 16, 14));
        setSpacing(10);

        Label title = new Label("Transition Table");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        title.setStyle("-fx-text-fill: #6c63ff; -fx-effect: dropshadow(gaussian, rgba(108, 99, 255, 0.5), 8, 0, 0, 0);");

        Label placeholder = new Label("Generate a DFA\nto see the table.");
        placeholder.setStyle("-fx-text-fill: #7070a0; -fx-font-size: 12px;");

        getChildren().addAll(title, placeholder);
    }

    // ── Public API ───────────────────────────────────────────────────────────

    /** Rebuilds the table for the given DFA. */
    public void populate(DFA dfa) {
        this.currentDFA = dfa;
        cells.clear();
        getChildren().clear();

        Label title = new Label("Transition Table");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        title.setStyle("-fx-text-fill: #6c63ff; -fx-effect: dropshadow(gaussian, rgba(108, 99, 255, 0.5), 8, 0, 0, 0);");
        getChildren().add(title);

        grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);

        List<Character> alphabet = dfa.getAlphabet();
        List<State>     states   = dfa.getStates();

        // ── Header row ───────────────────────────────────────────────────────
        List<Label> headerRow = new ArrayList<>();
        headerRow.add(headerCell("State"));
        grid.add(headerRow.get(0), 0, 0);

        for (int col = 0; col < alphabet.size(); col++) {
            Label hdr = headerCell(String.valueOf(alphabet.get(col)));
            grid.add(hdr, col + 1, 0);
            headerRow.add(hdr);
        }
        cells.add(headerRow);

        // ── Data rows ────────────────────────────────────────────────────────
        for (int row = 0; row < states.size(); row++) {
            State state = states.get(row);
            List<Label> dataRow = new ArrayList<>();

            // State name cell
            String stateName = state.getName();
            String display   = stateName;
            if (state.isStartState()) display = "→" + display;
            if (state.isFinalState()) display = "*" + display;

            Label stateCell = dataCell(display);
            if (state.isFinalState()) {
                stateCell.setStyle(stateCell.getStyle() + "-fx-text-fill: #4ade80; -fx-font-weight: bold;");
            }
            grid.add(stateCell, 0, row + 1);
            dataRow.add(stateCell);

            // Transition cells
            for (int col = 0; col < alphabet.size(); col++) {
                String next = dfa.getNextStateName(stateName, alphabet.get(col));
                Label cell  = dataCell(next != null ? next : "—");
                grid.add(cell, col + 1, row + 1);
                dataRow.add(cell);
            }

            cells.add(dataRow);
        }

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        getChildren().add(scroll);
    }

    /**
     * Highlights the row for currentStateName and column for symbol.
     * Resets all other cells to default first.
     */
    public void highlight(String currentStateName, char symbol) {
        if (currentDFA == null) return;
        resetHighlight();

        List<State>     states   = currentDFA.getStates();
        List<Character> alphabet = currentDFA.getAlphabet();

        // Find row index (offset +1 for header)
        int rowIdx = -1;
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).getName().equals(currentStateName)) {
                rowIdx = i + 1;
                break;
            }
        }

        // Find col index (offset +1 for state name column)
        int colIdx = -1;
        for (int i = 0; i < alphabet.size(); i++) {
            if (alphabet.get(i) == symbol) {
                colIdx = i + 1;
                break;
            }
        }

        if (rowIdx < 0 || rowIdx >= cells.size()) return;

        // Highlight full row
        List<Label> row = cells.get(rowIdx);
        for (Label cell : row) {
            cell.setStyle(cell.getStyle().replace("#1e1e3f", "#3a3a5a")
                + "-fx-background-color: #3a3a5a;");
        }

        // Highlight specific cell
        if (colIdx >= 0 && colIdx < row.size()) {
            Label active = row.get(colIdx);
            active.setStyle("-fx-background-color: linear-gradient(to bottom, #6c63ff 0%, #5a52d5 100%);" +
                            "-fx-text-fill: #ffffff;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 6 10 6 10;" +
                            "-fx-alignment: center;" +
                            "-fx-border-radius: 4;" +
                            "-fx-background-radius: 4;" +
                            "-fx-effect: dropshadow(gaussian, rgba(108, 99, 255, 0.6), 8, 0, 0, 0);");
        }
    }

    /** Resets all cells to default styling. */
    public void resetHighlight() {
        // Skip header row (index 0)
        for (int r = 1; r < cells.size(); r++) {
            for (int c = 0; c < cells.get(r).size(); c++) {
                Label cell = cells.get(r).get(c);
                if (c == 0) {
                    // State name column
                    State s = currentDFA.getStates().get(r - 1);
                    String style = dataCellStyle();
                    if (s.isFinalState()) style += "-fx-text-fill: #4ade80;";
                    cell.setStyle(style);
                } else {
                    cell.setStyle(dataCellStyle());
                }
            }
        }
    }

    // ── Cell Factories ───────────────────────────────────────────────────────

    private Label headerCell(String text) {
        Label lbl = new Label(text);
        lbl.setMinWidth(60);
        lbl.setAlignment(Pos.CENTER);
        lbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        lbl.setStyle(
            "-fx-text-fill: #6c63ff;" +
            "-fx-background-color: linear-gradient(to bottom, #2a2a4a 0%, #1e1e3f 100%);" +
            "-fx-padding: 6 10 6 10;" +
            "-fx-alignment: center;" +
            "-fx-border-radius: 4;" +
            "-fx-background-radius: 4;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 4, 0, 0, 2);"
        );
        return lbl;
    }

    private Label dataCell(String text) {
        Label lbl = new Label(text);
        lbl.setMinWidth(60);
        lbl.setAlignment(Pos.CENTER);
        lbl.setFont(Font.font("Segoe UI", 12));
        lbl.setStyle(dataCellStyle());
        return lbl;
    }

    private String dataCellStyle() {
        return  "-fx-text-fill: #e0e0ff;" +
                "-fx-background-color: #1e1e3f;" +
                "-fx-padding: 6 10 6 10;" +
                "-fx-alignment: center;" +
                "-fx-border-radius: 4;" +
                "-fx-background-radius: 4;";
    }
}
