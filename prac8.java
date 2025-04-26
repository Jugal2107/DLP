import java.util.*;

public class prac8 {
    static Map<String, List<String>> grammar = new HashMap<>();
    static Map<String, Set<String>> first = new HashMap<>();
    static Map<String, Set<String>> follow = new HashMap<>();
    static Map<String, Map<String, String>> parsingTable = new HashMap<>();
    static String startSymbol;

    public static void main(String[] args) {
        grammar.put("S", Arrays.asList("AB", "bC"));
        grammar.put("A", Arrays.asList("a", "e")); // using 'e' instead of epsilon
        grammar.put("B", Arrays.asList("b"));
        grammar.put("C", Arrays.asList("c"));

        startSymbol = "S";

        first.put("S", new HashSet<>(Arrays.asList("a", "b", "e")));
        first.put("A", new HashSet<>(Arrays.asList("a", "e")));
        first.put("B", new HashSet<>(Arrays.asList("b")));
        first.put("C", new HashSet<>(Arrays.asList("c")));

        follow.put("S", new HashSet<>(Arrays.asList("$")));
        follow.put("A", new HashSet<>(Arrays.asList("b")));
        follow.put("B", new HashSet<>(Arrays.asList("$")));
        follow.put("C", new HashSet<>(Arrays.asList("$")));

        constructParsingTable();

        System.out.println("\nPredictive Parsing Table:");
        for (String nonTerminal : parsingTable.keySet()) {
            for (String terminal : parsingTable.get(nonTerminal).keySet()) {
                System.out.println("M[" + nonTerminal + ", " + terminal + "] = " +
                        parsingTable.get(nonTerminal).get(terminal));
            }
        }

        System.out.println("\nIs LL(1) Grammar? " + (isLL1Grammar() ? "Yes" : "No"));

        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter input string to validate: ");
        String input = sc.nextLine();
        if (isLL1Grammar()) {
            System.out.println(parseInput(input) ? "Valid string" : "Invalid string");
        } else {
            System.out.println("Cannot validate string. Grammar is not LL(1).");
        }
    }

    static void constructParsingTable() {
        for (String nonTerminal : grammar.keySet()) {
            parsingTable.put(nonTerminal, new HashMap<>());
            for (String production : grammar.get(nonTerminal)) {
                Set<String> firstSet = getFirstOfString(production);
                for (String terminal : firstSet) {
                    if (!terminal.equals("e")) {
                        parsingTable.get(nonTerminal).put(terminal, production);
                    }
                }
                if (firstSet.contains("e")) {
                    for (String terminal : follow.get(nonTerminal)) {
                        parsingTable.get(nonTerminal).put(terminal, production);
                    }
                }
            }
        }
    }

    static Set<String> getFirstOfString(String str) {
        Set<String> result = new HashSet<>();
        if (str.length() == 0) return result;

        for (int i = 0; i < str.length(); i++) {
            String symbol = String.valueOf(str.charAt(i));
            if (!grammar.containsKey(symbol)) {
                result.add(symbol);
                break;
            } else {
                result.addAll(first.get(symbol));
                if (!first.get(symbol).contains("e"))
                    break;
                else if (i == str.length() - 1)
                    result.add("e");
            }
        }
        return result;
    }

    static boolean isLL1Grammar() {
        for (String nonTerminal : parsingTable.keySet()) {
            Set<String> seen = new HashSet<>();
            for (String terminal : parsingTable.get(nonTerminal).keySet()) {
                if (seen.contains(terminal))
                    return false;
                seen.add(terminal);
            }
        }
        return true;
    }

    static boolean parseInput(String input) {
        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push(startSymbol);

        input += "$";
        int i = 0;

        while (!stack.isEmpty()) {
            String top = stack.pop();
            String current = String.valueOf(input.charAt(i));

            if (top.equals(current)) {
                i++;
            } else if (!grammar.containsKey(top)) {
                return false;
            } else if (parsingTable.get(top).containsKey(current)) {
                String production = parsingTable.get(top).get(current);
                List<String> symbols = new ArrayList<>();
                for (int j = production.length() - 1; j >= 0; j--) {
                    if (production.charAt(j) != 'e') {
                        symbols.add(String.valueOf(production.charAt(j)));
                    }
                }
                for (String sym : symbols)
                    stack.push(sym);
            } else {
                return false;
            }
        }
        return i == input.length();
    }
}
