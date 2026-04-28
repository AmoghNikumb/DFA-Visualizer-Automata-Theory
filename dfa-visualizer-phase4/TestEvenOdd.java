import generator.EvenOddGenerator;
import model.DFA;
import model.State;

public class TestEvenOdd {
    public static void main(String[] args) {
        System.out.println("=== Testing Even Number of 'a' ===");
        DFA evenDFA = EvenOddGenerator.buildEven('a', "a");
        printDFA(evenDFA);
        
        System.out.println("\n=== Testing Odd Number of 'a' ===");
        DFA oddDFA = EvenOddGenerator.buildOdd('a', "a");
        printDFA(oddDFA);
    }
    
    private static void printDFA(DFA dfa) {
        System.out.println("States:");
        for (State s : dfa.getStates()) {
            String marker = "";
            if (s.isStartState()) marker += "START ";
            if (s.isFinalState()) marker += "FINAL";
            System.out.println("  " + s.getName() + " " + marker);
        }
        System.out.println("\nTransitions:");
        System.out.println(dfa.getTransitions());
    }
}
