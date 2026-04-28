package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a complete Deterministic Finite Automaton (DFA).
 *
 * Stores:
 *   - All states
 *   - All transitions
 *   - Start state
 *   - Final states
 *   - Transition table (HashMap for fast lookup)
 */
public class DFA {

    private final List<State> states;
    private final List<Transition> transitions;
    private final List<Character> alphabet;
    private State startState;
    private final List<State> finalStates;

    // Transition table: Map<StateName, Map<Symbol, TargetStateName>>
    private final Map<String, Map<Character, String>> transitionTable;

    public DFA() {
        this.states = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.transitionTable = new HashMap<>();
    }

    // ─── Builders ────────────────────────────────────────────────────────────

    public void addState(State state) {
        states.add(state);
        if (state.isStartState()) {
            startState = state;
        }
        if (state.isFinalState()) {
            finalStates.add(state);
        }
        // Initialise row in transition table
        transitionTable.put(state.getName(), new HashMap<>());
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
        // Register in transition table
        transitionTable
            .get(transition.getFromState().getName())
            .put(transition.getSymbol(), transition.getToState().getName());
    }

    public void addSymbol(char symbol) {
        if (!alphabet.contains(symbol)) {
            alphabet.add(symbol);
        }
    }

    // ─── Lookup ──────────────────────────────────────────────────────────────

    /**
     * Returns the next state name given a current state name and input symbol.
     * Returns null if no transition exists (dead / trap state).
     */
    public String getNextStateName(String currentStateName, char symbol) {
        Map<Character, String> row = transitionTable.get(currentStateName);
        if (row == null) return null;
        return row.get(symbol);
    }

    public State getStateByName(String name) {
        for (State s : states) {
            if (s.getName().equals(name)) return s;
        }
        return null;
    }

    // ─── Getters ─────────────────────────────────────────────────────────────

    public List<State> getStates() { return states; }

    public List<Transition> getTransitions() { return transitions; }

    public List<Character> getAlphabet() { return alphabet; }

    public State getStartState() { return startState; }

    public List<State> getFinalStates() { return finalStates; }

    public Map<String, Map<Character, String>> getTransitionTable() { return transitionTable; }

    // ─── Display ─────────────────────────────────────────────────────────────

    /**
     * Prints the full transition table to the console.
     * Useful for debugging before the JavaFX UI is ready.
     */
    public void printTransitionTable() {
        System.out.println("\n=== Transition Table ===");

        // Header row
        System.out.printf("%-10s", "State");
        for (char symbol : alphabet) {
            System.out.printf("%-10s", symbol);
        }
        System.out.println();

        // Divider
        System.out.println("-".repeat(10 + alphabet.size() * 10));

        // Data rows
        for (State state : states) {
            String label = state.getName();
            if (state.isStartState()) label = "->" + label;
            if (state.isFinalState()) label = "*" + label;
            System.out.printf("%-10s", label);

            for (char symbol : alphabet) {
                String next = getNextStateName(state.getName(), symbol);
                System.out.printf("%-10s", next != null ? next : "-");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DFA {\n");
        sb.append("  Start : ").append(startState != null ? startState.getName() : "none").append("\n");
        sb.append("  Final : ").append(finalStates).append("\n");
        sb.append("  Transitions:\n");
        for (Transition t : transitions) {
            sb.append("    ").append(t).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
