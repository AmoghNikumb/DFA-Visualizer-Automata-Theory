package utils;

import model.State;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GraphLayout — computes (x, y) coordinates for DFA states.
 *
 * Strategies:
 *   1–2 states   → horizontal centre line
 *   3–6 states   → horizontal row with even spacing
 *   7–8 states   → two rows
 *   9+ states    → circular layout
 *
 * Always leaves padding so no state is clipped at the canvas edge.
 */
public class GraphLayout {

    private static final double PADDING = 80;

    /**
     * Returns a map of stateName → [x, y] for all states.
     *
     * @param states      ordered list of DFA states
     * @param canvasW     available canvas width
     * @param canvasH     available canvas height
     */
    public static Map<String, double[]> compute(List<State> states,
                                                 double canvasW,
                                                 double canvasH) {
        Map<String, double[]> positions = new LinkedHashMap<>();
        int n = states.size();

        double cx = canvasW / 2;
        double cy = canvasH / 2;

        if (n <= 6) {
            // ── Horizontal row ────────────────────────────────────────────
            double usable  = canvasW - PADDING * 2;
            double spacing = n > 1 ? usable / (n - 1) : 0;
            double startX  = n > 1 ? PADDING : cx;

            for (int i = 0; i < n; i++) {
                double x = startX + i * spacing;
                double y = cy;
                positions.put(states.get(i).getName(), new double[]{x, y});
            }

        } else if (n <= 9) {
            // ── Two rows ──────────────────────────────────────────────────
            int topCount = (int) Math.ceil(n / 2.0);
            int botCount = n - topCount;

            double rowSpacingX = (canvasW - PADDING * 2) / Math.max(topCount - 1, 1);
            double y1 = cy - 80;
            double y2 = cy + 80;

            for (int i = 0; i < topCount; i++) {
                double x = topCount > 1 ? PADDING + i * rowSpacingX : cx;
                positions.put(states.get(i).getName(), new double[]{x, y1});
            }
            double botSpacingX = botCount > 1
                ? (canvasW - PADDING * 2) / (botCount - 1) : 0;
            for (int i = 0; i < botCount; i++) {
                double x = botCount > 1 ? PADDING + i * botSpacingX : cx;
                positions.put(states.get(topCount + i).getName(), new double[]{x, y2});
            }

        } else {
            // ── Circular layout ───────────────────────────────────────────
            double r = Math.min(cx, cy) - PADDING;
            for (int i = 0; i < n; i++) {
                double angle = 2 * Math.PI * i / n - Math.PI / 2;
                double x = cx + r * Math.cos(angle);
                double y = cy + r * Math.sin(angle);
                positions.put(states.get(i).getName(), new double[]{x, y});
            }
        }

        return positions;
    }
}
