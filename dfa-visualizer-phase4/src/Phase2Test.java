import generator.DFAGenerator;
import model.DFA;
import simulator.DFASimulator;

/**
 * Phase 2 Test — DFA Generator
 *
 * Tests all supported conditions:
 *   1. ends with
 *   2. starts with
 *   3. contains
 *   4. even number of
 *   5. odd number of
 */
public class Phase2Test {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     DFA VISUALIZER — Phase 2 Tests       ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        // ── Test 1: Ends With "abb" ──────────────────────────────────────────
        runSection("1. ENDS WITH 'abb'  |  Alphabet: {a,b}",
            DFAGenerator.generate("ends with abb", "ab"),
            new String[]{"abb", "aabbabb", "babb", "ab", "bbb", "abba"}
        );

        // ── Test 2: Starts With "ab" ─────────────────────────────────────────
        runSection("2. STARTS WITH 'ab'  |  Alphabet: {a,b}",
            DFAGenerator.generate("starts with ab", "ab"),
            new String[]{"ab", "abba", "ababab", "ba", "aab", "b"}
        );

        // ── Test 3: Contains "101" ───────────────────────────────────────────
        runSection("3. CONTAINS '101'  |  Alphabet: {0,1}",
            DFAGenerator.generate("contains 101", "01"),
            new String[]{"101", "0101", "11010", "100", "010", "111"}
        );

        // ── Test 4: Even Number of 'a' ───────────────────────────────────────
        runSection("4. EVEN NUMBER OF 'a'  |  Alphabet: {a,b}",
            DFAGenerator.generate("even number of a", "ab"),
            new String[]{"", "aa", "bab", "aab", "abba", "a", "bba"}
        );

        // ── Test 5: Odd Number of 'b' ────────────────────────────────────────
        runSection("5. ODD NUMBER OF 'b'  |  Alphabet: {a,b}",
            DFAGenerator.generate("odd number of b", "ab"),
            new String[]{"b", "abb", "bab", "bb", "aab", "abba"}
        );
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    static void runSection(String title, DFA dfa, String[] inputs) {
        System.out.println("┌─────────────────────────────────────────────");
        System.out.println("│ " + title);
        System.out.println("└─────────────────────────────────────────────");

        dfa.printTransitionTable();

        for (String input : inputs) {
            DFASimulator sim = new DFASimulator(dfa, input);
            sim.simulate();
            printCompact(input, sim);
        }
        System.out.println();
    }

    static void printCompact(String input, DFASimulator sim) {
        StringBuilder trace = new StringBuilder();
        for (DFASimulator.SimulationStep step : sim.getSteps()) {
            if (step.getType().equals("MOVE")) {
                trace.append(step.getFromState())
                     .append(" --").append(step.getSymbol()).append("--> ");
            }
        }
        // append final state
        if (!sim.getSteps().isEmpty()) {
            DFASimulator.SimulationStep last = sim.getSteps().get(sim.getSteps().size() - 1);
            trace.append(last.getFromState());
        }

        String result = sim.isAccepted() ? "✅ ACCEPTED" : "❌ REJECTED";
        String displayInput = input.isEmpty() ? "(empty)" : "\"" + input + "\"";
        System.out.printf("  %-12s  %s  |  %s%n", displayInput, result, trace);
    }
}
