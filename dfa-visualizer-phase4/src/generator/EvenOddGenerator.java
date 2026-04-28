package generator;

import model.DFA;
import model.State;
import model.Transition;

/**
 * Builds a DFA that accepts strings with an even or odd number of a given symbol.
 *
 * Algorithm:
 *   2 states: q0 (even count seen so far), q1 (odd count seen so far).
 *
 *   For EVEN:
 *     q0 is the start AND final state (0 is even).
 *     Reading the target symbol toggles between q0 ↔ q1.
 *     Reading any other symbol stays in the same state.
 *
 *   For ODD:
 *     q0 is start, q1 is the final state.
 *     Same toggle logic.
 *
 * Example — even number of 'a', alphabet {a,b}:
 *   q0* --a--> q1,  q0* --b--> q0*
 *   q1  --a--> q0*, q1  --b--> q1
 */
public class EvenOddGenerator {

    public static DFA buildEven(char targetSymbol, String alphabet) {
        return build(targetSymbol, alphabet, true);
    }

    public static DFA buildOdd(char targetSymbol, String alphabet) {
        return build(targetSymbol, alphabet, false);
    }

    private static DFA build(char targetSymbol, String alphabet, boolean evenIsFinal) {
        DFA dfa = new DFA();

        for (char c : alphabet.toCharArray()) dfa.addSymbol(c);

        // q0 = even count, q1 = odd count
        boolean q0Final = evenIsFinal;   // even number of target → q0 is final
        boolean q1Final = !evenIsFinal;  // odd  number of target → q1 is final

        State q0 = new State("q0", true,  q0Final);
        State q1 = new State("q1", false, q1Final);

        dfa.addState(q0);
        dfa.addState(q1);

        for (char c : alphabet.toCharArray()) {
            if (c == targetSymbol) {
                // Toggle on target symbol
                dfa.addTransition(new Transition(q0, c, q1));
                dfa.addTransition(new Transition(q1, c, q0));
            } else {
                // Stay on other symbols
                dfa.addTransition(new Transition(q0, c, q0));
                dfa.addTransition(new Transition(q1, c, q1));
            }
        }

        return dfa;
    }
}
