import model.DFA;
import model.State;
import model.Transition;
import simulator.DFASimulator;

/**
 * Phase 1 Test — DFA Core Logic
 *
 * Manually builds the DFA for: language "ends with abb"
 *
 *   States   : q0, q1, q2, q3 (q3 is final)
 *   Alphabet : {a, b}
 *
 *   Transition Table:
 *   ─────────────────────
 *   State   a     b
 *   q0     q1    q0
 *   q1     q1    q2
 *   q2     q1    q3
 *   q3     q1    q0
 *   ─────────────────────
 *
 * Tests several strings and prints results.
 */
public class Phase1Test {

    public static void main(String[] args) {

        // ── Build DFA ───────────────────────────────────────────────────────
        DFA dfa = buildEndsWithAbbDFA();
        System.out.println(dfa);
        dfa.printTransitionTable();

        // ── Test Cases ──────────────────────────────────────────────────────
        String[] testStrings = {
            "abb",       // ✅ accept
            "aabbabb",   // ✅ accept
            "ab",        // ❌ reject
            "bbb",       // ❌ reject
            "abba",      // ❌ reject
            "babb",      // ✅ accept
            ""           // ❌ reject (empty string)
        };

        for (String input : testStrings) {
            DFASimulator simulator = new DFASimulator(dfa, input);
            simulator.simulate();
            simulator.printLog();
        }
    }

    // ── DFA Builder ─────────────────────────────────────────────────────────

    static DFA buildEndsWithAbbDFA() {
        DFA dfa = new DFA();

        // Define states
        State q0 = new State("q0", true,  false);  // start
        State q1 = new State("q1", false, false);
        State q2 = new State("q2", false, false);
        State q3 = new State("q3", false, true);   // final

        dfa.addState(q0);
        dfa.addState(q1);
        dfa.addState(q2);
        dfa.addState(q3);

        // Define alphabet
        dfa.addSymbol('a');
        dfa.addSymbol('b');

        // Define transitions
        dfa.addTransition(new Transition(q0, 'a', q1));
        dfa.addTransition(new Transition(q0, 'b', q0));

        dfa.addTransition(new Transition(q1, 'a', q1));
        dfa.addTransition(new Transition(q1, 'b', q2));

        dfa.addTransition(new Transition(q2, 'a', q1));
        dfa.addTransition(new Transition(q2, 'b', q3));

        dfa.addTransition(new Transition(q3, 'a', q1));
        dfa.addTransition(new Transition(q3, 'b', q0));

        return dfa;
    }
}
