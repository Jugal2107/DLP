import java.io.*;
import java.util.*;

public class Prac3{
    private static final Set<String> keywords = new HashSet<>(Arrays.asList(
        "int", "float", "char", "if", "else", "while", "return", "for", "do", "void", "switch", "case"
    ));

    private static final Set<String> operators = new HashSet<>(Arrays.asList(
        "+", "-", "*", "/", "=", "==", "!=", "<", ">", "<=", ">=", "&&", "||", "!"
    ));

    private static final Set<Character> punctuation = new HashSet<>(Arrays.asList(
        ';', ',', '{', '}', '(', ')'
    ));

    private static final List<String> symbolTable = new ArrayList<>();

    private static void analyzeSourceCode(String sourceCode) {
        String newCode = removeComments(sourceCode);
        StringTokenizer tokenizer = new StringTokenizer(newCode, " \t\n\r;,+-*/=<>!&|(){}\"'", true);
        System.out.println("TOKENS:");
        System.out.println("-------");

        boolean inMultiLineComment = false;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (inMultiLineComment) {
                if (token.equals("*/")) {
                    inMultiLineComment = false;
                }
                continue; // Skip tokens inside multi-line comment
            }

            // Start of a multi-line comment
            if (token.equals("/*")) {
                inMultiLineComment = true;
                continue;
            }

            if (token.equals("//")) {
                while (tokenizer.hasMoreTokens() && !token.contains("\n")) {
                    token = tokenizer.nextToken(); // Ignore until the end of the line
                }
                continue;
            }
            
            token = token.trim();

            if (token.isEmpty()) continue;

            if (keywords.contains(token)) {
                System.out.println("Keyword: " + token);
            } else if (isIdentifier(token)) {
                System.out.println("Identifier: " + token);
                if (!symbolTable.contains(token)) {
                    symbolTable.add(token);
                }
            } else if (isConstant(token)) {
                System.out.println("Constant: " + token);
            } else if (operators.contains(token)) {
                System.out.println("Operator: " + token);
            } else if (token.length() == 1 && punctuation.contains(token.charAt(0))) {
                System.out.println("Punctuation: " + token);
            } else {
                System.out.println("LEXICAL ERROR: Invalid token -> " + token);
            }
        }

        printSymbolTable();
    }

    private static String removeComments(String sourceCode) {
        return sourceCode.replaceAll("//.*", "").replaceAll("/\\*.*?\\*/", "").trim();
    }

    private static boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    private static boolean isConstant(String token) {
        return token.matches("\\d+");
    }

    private static void printSymbolTable() {
        System.out.println("\nSYMBOL TABLE ENTRIES:");
        System.out.println("----------------------");
        for (int i = 0; i < symbolTable.size(); i++) {
            System.out.println((i + 1) + ") " + symbolTable.get(i));
        }
    }
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("code.c"));
            StringBuilder sourceCode = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sourceCode.append(line).append("\n");
            }
            reader.close();

            analyzeSourceCode(sourceCode.toString());
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
