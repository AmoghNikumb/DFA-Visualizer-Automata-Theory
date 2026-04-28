package generator;

import model.DFA;
import model.State;
import model.Transition;

/**
 * Builds a DFA that accepts strings starting with a given pattern.
 *
 * Algorithm:
 *   States: q0 (start), q1 ... q(n) (final trap — once matched, stay accepting).
 *   A dead state "qDead" handles any early mismatch.
 *
 * Example — starts with "ab", alphabet {a,b}:
 *   q0    --a--> q1,      q0    --b--> qDead
 *   q1    --b--> q2*,     q1    --a--> qDead
 *   q2*   --a--> q2*,     q2*   --b--> q2*    (stay in final)
 *   qDead --a--> qDead,   qDead --b--> qDead  (stay dead)
 */
public class StartsWithGenerator {

    public static DFA build(String pattern, String alphabet) {
        int n = pattern.length();
        DFA dfa = new DFA();

        for (char c : alphabet.toCharArray()) dfa.addSymbol(c);

        // Normal states q0 … qn
        State[] states = new State[n + 1];
        for (int i = 0; i <= n; i++) {
            boolean isStart = (i == 0);
            boolean isFinal = (i == n);
            states[i] = new State("q" + i, isStart, isFinal);
            dfa.addState(states[i]);
        }

        // Dead state — reached on any mismatch before pattern is complete
        State dead = new State("qDead", false, false);
        dfa.addState(dead);

        // Transitions for q0 … q(n-1): match pattern char or go dead
        for (int i = 0; i < n; i++) {
            for (char c : alphabet.toCharArray()) {
                if (c == pattern.charAt(i)) {
                    dfa.addTransition(new Transition(states[i], c, states[i + 1]));
                } else {
                    dfa.addTransition(new Transition(states[i], c, dead));
                }
            }
        }

        // Final state qn: loop on all symbols (already accepted)
        for (char c : alphabet.toCharArray()) {
            dfa.addTransition(new Transition(states[n], c, states[n]));
        }

        // Dead state: loop on all symbols (stay dead)
        for (char c : alphabet.toCharArray()) {
            dfa.addTransition(new Transition(dead, c, dead));
        }

        return dfa;
    }
}
