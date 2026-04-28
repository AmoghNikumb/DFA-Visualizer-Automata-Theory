package generator;

import model.DFA;
import model.State;
import model.Transition;

/**
 * Builds a DFA that accepts strings containing a given pattern as a substring.
 *
 * Algorithm:
 *   Same KMP-style next-state function as EndsWithGenerator.
 *   Once the final state (full match) is reached, all transitions loop back
 *   to that state — because once the substring is found, the string is accepted
 *   regardless of remaining characters.
 *
 * Example — contains "ab", alphabet {a,b}:
 *   q0  --a--> q1,  q0  --b--> q0
 *   q1  --b--> q2*, q1  --a--> q1
 *   q2* --a--> q2*, q2* --b--> q2*   (absorbing final state)
 */
public class ContainsGenerator {

    public static DFA build(String pattern, String alphabet) {
        int n = pattern.length();
        DFA dfa = new DFA();

        for (char c : alphabet.toCharArray()) dfa.addSymbol(c);

        // States q0 … qn
        State[] states = new State[n + 1];
        for (int i = 0; i <= n; i++) {
            boolean isStart = (i == 0);
            boolean isFinal = (i == n);
            states[i] = new State("q" + i, isStart, isFinal);
            dfa.addState(states[i]);
        }

        // Transitions for q0 … q(n-1) using KMP next-state
        for (int state = 0; state < n; state++) {
            for (char c : alphabet.toCharArray()) {
                int next = getNextState(pattern, state, c);
                dfa.addTransition(new Transition(states[state], c, states[next]));
            }
        }

        // Final state qn: loop on all (absorbing — already found pattern)
        for (char c : alphabet.toCharArray()) {
            dfa.addTransition(new Transition(states[n], c, states[n]));
        }

        return dfa;
    }

    private static int getNextState(String pattern, int state, char c) {
        if (state < pattern.length() && pattern.charAt(state) == c) {
            return state + 1;
        }
        for (int ns = state - 1; ns >= 0; ns--) {
            if (pattern.charAt(ns) == c) {
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
