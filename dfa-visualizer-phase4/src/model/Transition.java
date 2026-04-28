package model;

/**
 * Represents a transition in a DFA.
 * A transition goes from one state to another on a given input symbol.
 */
public class Transition {

    private final State fromState;
    private final char symbol;
    private final State toState;

    public Transition(State fromState, char symbol, State toState) {
        this.fromState = fromState;
        this.symbol = symbol;
        this.toState = toState;
    }

    public State getFromState() {
        return fromState;
    }

    public char getSymbol() {
        return symbol;
    }

    public State getToState() {
        return toState;
    }

    @Override
    public String toString() {
        return fromState.getName() + " --" + symbol + "--> " + toState.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Transition)) return false;
        Transition other = (Transition) obj;
        return this.fromState.equals(other.fromState)
                && this.symbol == other.symbol
                && this.toState.equals(other.toState);
    }

    @Override
    public int hashCode() {
        return fromState.hashCode() * 31 + Character.hashCode(symbol);
    }
}
