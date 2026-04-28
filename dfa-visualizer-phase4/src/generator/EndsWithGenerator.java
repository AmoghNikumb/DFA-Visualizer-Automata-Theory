package generator;

import model.DFA;
import model.State;
import model.Transition;

/**
 * Builds a DFA that accepts strings ending with a given pattern.
 *
 * Algorithm:
 *   Uses a failure-function approach (similar to KMP).
 *   Each state represents how many characters of the pattern
 *   have been matched so far.
 *
 *   States: q0 (start), q1 ... q(n) where q(n) is the final state.
 *   n = pattern length
 *
 * Example — ends with "abb", alphabet {a,b}:
 *   q0 --a--> q1,  q0 --b--> q0
 *   q1 --a--> q1,  q1 --b--> q2
 *   q2 --a--> q1,  q2 --b--> q3*
 *   q3 --a--> q1,  q3 --b--> q0
 */
public class EndsWithGenerator {

    public static DFA build(String pattern, String alphabet) {
        int n = pattern.length();
        DFA dfa = new DFA();

        // Register alphabet symbols
        for (char c : alphabet.toCharArray()) dfa.addSymbol(c);

        // Create n+1 states
        State[] states = new State[n + 1];
        for (int i = 0; i <= n; i++) {
            boolean isStart = (i == 0);
            boolean isFinal = (i == n);
            states[i] = new State("q" + i, isStart, isFinal);
            dfa.addState(states[i]);
        }

        // Build transition function using failure function
        for (int state = 0; state <= n; state++) {
            for (char c : alphabet.toCharArray()) {
                int next = getNextState(pattern, state, c);
                dfa.addTransition(new Transition(states[state], c, states[next]));
            }
        }

        return dfa;
    }

    /**
     * Computes the next state given current matched length and input char.
     * If appending c to the matched prefix gives a prefix of pattern, go to that state.
     * Otherwise, fall back using the longest proper suffix that is also a prefix.
     */
    private static int getNextState(String pattern, int state, char c) {
        // If we can extend the match
        if (state < pattern.length() && pattern.charAt(state) == c) {
            return state + 1;
        }
        // Try shorter matches (failure function)
        for (int ns = state - 1; ns >= 0; ns--) {
            if (pattern.charAt(ns) == c) {
                // Check if pattern[0..ns-1] matches pattern[state-ns..state-1]
                boolean match = true;
                for (int k = 0; k < ns; k++) {
                    if (pattern.charAt(k) != pattern.charAt(state - ns + k)) {
                        match = false;
                        break;
                    }
                }
                if (match) return ns + 1;
            }
        }
        return 0;
    }
}
