import java.io.*;

public class ExpressionRepl {
    public static void main(String[] args) throws IOException {
        ExpressionRepl repl = new ExpressionRepl();

        while (true) {
            // Read
            System.out.print("> ");
            String input = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
            
            // Eval
            String response = repl.evaluateInput(input);

            // Print
            System.out.println(response);
        }
    }

    private MapInterface<String, Double> variableBindings = new MapImplemented<String, Double>();

    public String evaluateInput(String input) {
        Tokenizer.TokenList tokenList = Tokenizer.tokenize(input);
        ExpressionInterpreter interpreter = new ExpressionInterpreter();
        Node root = interpreter.buildExpressionTree(tokenList.list);
        String variableName = tokenList.variableName;

        Node solvedNode = solveIfPossible(root);

        // Handle null result
        if (solvedNode == null || solvedNode.element == null) {
            return "Not solved.";
        }

        Double solved = Double.parseDouble(solvedNode.element);

        if (variableName == null) {
            return String.valueOf(solved);
        }

        variableBindings.put(variableName, solved);
        return variableName + " = " + solved;
    }


    private Node solveIfPossible(Node root) {
        if (root == null) {
            return null;  // Return null immediately if the root is null
        }

        // Leaf node: either a number or a variable
        if (root.leftChild == null && root.rightChild == null) {
            if (isNumeric(root.element)) {
                return root;
            } else {
                Double value = variableBindings.get(root.element);
                if (value != null) {
                    return new Node(Double.toString(value));
                } else {
                    System.out.println("Unbound Variable: " + root.element);
                    return null;  // Return null for unbound variables
                }
            }
        }

        // Recursively solve children
        Node left = solveIfPossible(root.leftChild);
        Node right = solveIfPossible(root.rightChild);

        // Null checks to avoid NullPointerException
        if (left == null || right == null || left.element == null || right.element == null) {
            return null;  // If either side is null, return null
        }

        // If both sides are numeric, evaluate the expression
        if (isNumeric(left.element) && isNumeric(right.element)) {
            double leftVal = Double.parseDouble(left.element);
            double rightVal = Double.parseDouble(right.element);
            double result = evaluate(root.element, leftVal, rightVal);
            return new Node(Double.toString(result));
        }

        return root;  // Return the original root if it cannot be solved
    }

    public static boolean isNumeric(String str) {
        try {
            double v = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }

    private static double evaluate(String operator, double left, double right) {
        switch (operator) {
            case "+": return left + right;
            case "-": return left - right;
            case "*": return left * right;
            case "/": return left / right;
            case "^": return Math.pow(left, right);
            default: throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public ExpressionRepl() { }
}