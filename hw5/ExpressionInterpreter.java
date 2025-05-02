public class ExpressionInterpreter {



    public static void main(String[] args) {
        SinglyLinkedList<String> tokens = Tokenizer.tokenize(args[0]).list;

        ExpressionInterpreter interpreter = new ExpressionInterpreter();

        // 1) Build the expression Tree From the Tokens (requires a Node class)
        Node root = interpreter.buildExpressionTree(tokens);



        System.out.print("Postfix: ");
        // 2) Print the post order travesal (postfix notation)
        printPostOrder(root);
        System.out.print("\n");

        System.out.print("Infix: ");
        // 3) Print the inorder travesal (infix notation) with additional parentheses
        printInOrder(root);
        System.out.print("\n");

        // 4) Solve the expression as much as possible, printing out the unbound variables.
        root = solveAsMuchAsPossible(root);
        
        System.out.print("Solved: ");
        // 5) Print the solved expression.
        printInOrder(root);
        System.out.print("\n");
    }

    public Node buildExpressionTree(SinglyLinkedList<String> tokens) {
        // TODO - fill this in
        ArrayStack<Node> expressionStack = new ArrayStack<Node>();
        ArrayStack<String> operatorStack = new ArrayStack<String>();

        operatorStack.push("(");

        tokens.addLast(")");

        while (!tokens.isEmpty()) {
            String token = tokens.removeFirst();

            if (token.equals("(")) {operatorStack.push(token);}
            else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^")) {
                while (!operatorStack.top().equals("(") && getPrecedence(operatorStack.top()) >= getPrecedence(token)){
                    popOperatorStack(expressionStack, operatorStack);
                }
                operatorStack.push(token);
        }
            else if (token.equals(")")) {
                while (!operatorStack.top().equals("(")) {
                    popOperatorStack(expressionStack, operatorStack);
                }
                operatorStack.pop();
        }
            else {
                Node n = new Node(token);
                expressionStack.push(n);
            }
        }
            return expressionStack.pop();
    }

    public static void printPostOrder(Node root) {
        // TODO - fill this in
        if (root == null) {return;}

        printPostOrder(root.leftChild);
        printPostOrder(root.rightChild);

        System.out.print(root.element+ " ");
    }
    
    public static void printInOrder(Node root) {
        // TODO - fill this in
        if (root == null) {return;}
        if (root.leftChild != null) {System.out.print("(");}
        printInOrder(root.leftChild);
        if (isOperator(root.element)) {System.out.print(" " + root.element + " ");}
        else {System.out.print(root.element);}
        printInOrder(root.rightChild);
        if (root.rightChild != null) {System.out.print(")");}
    }

    public static Node solveAsMuchAsPossible(Node root) {
        // TODO - fill this in
        if (root == null) {return null;}

        if (root.leftChild == null && root.rightChild == null) {
            if (isNumeric(root.element)) {return root;}
            else {
                System.out.println("Unbound Variable: " + root.element);
                return root; // Return variable nodes unchanged
            }
        }
        Node left = solveAsMuchAsPossible(root.leftChild);
        Node right = solveAsMuchAsPossible(root.rightChild);

        if (isNumeric(left.element) && isNumeric(right.element)) {
            double leftVal = Double.parseDouble(left.element);
            double rightVal = Double.parseDouble(right.element);
            double result = evaluate(root.element, leftVal, rightVal);
            return new Node (Double.toString(result)); // Replace subtree with computed value
        }

        // If either child is a variable, keep the expression tree as-is
        root.leftChild = left;
        root.rightChild = right;
        return root;
    }

    public static void popOperatorStack(ArrayStack<Node> expressionStack, ArrayStack<String> operatorStack){
        Node right = expressionStack.pop();
        Node left = expressionStack.pop();

        Node node = new Node(operatorStack.pop());
        node.rightChild = right;
        node.leftChild = left;
        expressionStack.push(node);
    }

    public static int getPrecedence(String op) {
            if (op.equals("^")) return 3;
            if (op.equals("*") || op.equals("/")) return 2;
            if (op.equals("+") || op.equals("-")) return 1;
            return 0;
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
//    private static boolean isNumeric(String str){
//        return str.matches("\\d.*\\d*\\sTO\\s*\\d.*");
//    }
    public static boolean isNumeric(String str) {
        try {
            double v = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }

    public static boolean isOperator(String op) {
        if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("^")) return true;
        else return false;
    }
}