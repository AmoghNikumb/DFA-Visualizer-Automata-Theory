package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * MainApp — JavaFX entry point.
 *
 * Root layout (BorderPane):
 *   TOP    → InputPanel   (condition, string, alphabet, buttons)
 *   CENTER → GraphPane    (DFA graph drawing area)
 *   RIGHT  → TransitionTable (state/symbol grid)
 *   BOTTOM → ControlPanel (play, pause, next, reset, speed, log)
 */
public class MainApp extends Application {

    public static final int WIDTH  = 1100;
    public static final int HEIGHT = 700;

    @Override
    public void start(Stage primaryStage) {

        // ── Create all panels ───────────────────────────────────────────────
        GraphPane       graphPane       = new GraphPane();
        TransitionTable transitionTable = new TransitionTable();
        ControlPanel    controlPanel    = new ControlPanel();
        InputPanel      inputPanel      = new InputPanel(graphPane, transitionTable, controlPanel);

        // ── Root layout ─────────────────────────────────────────────────────
        BorderPane root = new BorderPane();
        root.setTop(inputPanel);
        root.setCenter(graphPane);
        root.setRight(transitionTable);
        root.setBottom(controlPanel);

        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e 0%, #16213e 100%);");
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("DFA Visualizer — Automata Theory Tool");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
