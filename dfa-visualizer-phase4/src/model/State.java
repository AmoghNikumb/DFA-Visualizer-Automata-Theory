package model;

/**
 * Represents a single state in a DFA.
 * Each state has a name, and flags for start/final status.
 */
public class State {

    private final String name;
    private final boolean isStartState;
    private final boolean isFinalState;

    public State(String name, boolean isStartState, boolean isFinalState) {
        this.name = name;
        this.isStartState = isStartState;
        this.isFinalState = isFinalState;
    }

    public String getName() {
        return name;
    }

    public boolean isStartState() {
        return isStartState;
    }

    public boolean isFinalState() {
        return isFinalState;
    }

    @Override
    public String toString() {
        String label = name;
        if (isStartState) label = "->" + label;
        if (isFinalState) label = "(" + label + ")";
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof State)) return false;
        State other = (State) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
