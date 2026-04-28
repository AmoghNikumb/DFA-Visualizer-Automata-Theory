package generator;

import model.DFA;

/**
 * DFAGenerator is the entry point for condition-based DFA construction.
 *
 * Supported conditions:
 *   - "ends with XYZ"
 *   - "starts with XYZ"
 *   - "contains XYZ"
 *   - "even number of a" / "even number of b"
 *   - "odd number of a"  / "odd number of b"
 *
 * Usage:
 *   DFA dfa = DFAGenerator.generate("ends with abb", "ab");
 */
public class DFAGenerator {

    /**
     * Parses the condition string and delegates to the correct builder.
     *
     * @param condition  Natural language condition e.g. "ends with abb"
     * @param alphabet   Comma-separated or plain alphabet string e.g. "ab" or "a,b"
     * @return           A fully constructed DFA
     */
    public static DFA generate(String condition, String alphabet) {
        // Normalise
        String cond = condition.trim().toLowerCase();
        String alpha = alphabet.replace(",", "").replace(" ", "").trim();

        if (cond.startsWith("ends with")) {
            String pattern = extractPattern(cond, "ends with");
            return EndsWithGenerator.build(pattern, alpha);
        } else if (cond.startsWith("starts with")) {
            String pattern = extractPattern(cond, "starts with");
            return StartsWithGenerator.build(pattern, alpha);
        } else if (cond.startsWith("contains")) {
            String pattern = extractPattern(cond, "contains");
            return ContainsGenerator.build(pattern, alpha);
        } else if (cond.contains("even number of")) {
            char symbol = extractSymbol(cond);
            return EvenOddGenerator.buildEven(symbol, alpha);
        } else if (cond.contains("odd number of")) {
            char symbol = extractSymbol(cond);
            return EvenOddGenerator.buildOdd(symbol, alpha);
        } else {
            throw new IllegalArgumentException(
                "Unsupported condition: \"" + condition + "\"\n" +
                "Supported: ends with, starts with, contains, even/odd number of"
            );
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private static String extractPattern(String condition, String prefix) {
        return condition.substring(prefix.length()).trim();
    }

    private static char extractSymbol(String condition) {
        // e.g. "even number of a" → 'a'
        String[] parts = condition.split(" ");
        return parts[parts.length - 1].charAt(0);
    }
}
