
public class Tokenizer {
    public static class TokenList {
        final public String variableName;
        final public SinglyLinkedList<String> list;

        public TokenList(String variableName, SinglyLinkedList<String> list) {
            this.variableName = variableName;
            this.list = list;
        }
    }

    public static TokenList tokenize(String expression) {
        Tokenizer tokenizer = new Tokenizer();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            tokenizer.acceptCharacter(currentChar);
        }

        return tokenizer.finalizeTokenList();
    }

    SinglyLinkedList<String> tokens = new SinglyLinkedList<>();
    StringBuilder numberBuffer = new StringBuilder();
    StringBuilder variableBuffer = new StringBuilder();
    String variableName = null;

    public Tokenizer() {}

    public void acceptCharacter(char currentChar) {
        // Ignore whitespaces
        if (Character.isWhitespace(currentChar)) {
            clearBuffersIfNonEmpty();
            return;
        }

        // If it's a digit, add to number buffer
        if (currentChar == '=') {
            // Reset tokens and set the variable name
            // Note that this doesn't work well for a malformed expression, only for <var> = <exp>
            clearBuffersIfNonEmpty();
            variableName = tokens.last();
            tokens = new SinglyLinkedList<>();
        } else if (Character.isDigit(currentChar) || currentChar == '.') {
            if (variableBuffer.length() > 0) tokenizeVariableBuffer();
            numberBuffer.append(currentChar);
        } else if (Character.isLetter(currentChar)) {
            // If it's a letter, treat it as part of a variable name
            if (numberBuffer.length() > 0) tokenizeNumberBuffer();
            variableBuffer.append(currentChar);
        } else {
            clearBuffersIfNonEmpty();

            // Add operators or parentheses directly to tokens
            if (isOperator(currentChar) || currentChar == '(' || currentChar == ')') {
                tokens.addLast(String.valueOf(currentChar));
            }
        }
    }

    public TokenList finalizeTokenList() {
        clearBuffersIfNonEmpty();
        return new TokenList(variableName, tokens);
    }

    private void clearBuffersIfNonEmpty() {
        // Add the last number/variable if still in the buffer
        if (numberBuffer.length() > 0) tokenizeNumberBuffer();
        if (variableBuffer.length() > 0) tokenizeVariableBuffer();
    }

    private void tokenizeNumberBuffer() {
        tokens.addLast(numberBuffer.toString());
        numberBuffer.setLength(0);  // Clear the buffer
    }

    private void tokenizeVariableBuffer() {
        tokens.addLast(variableBuffer.toString());
        variableBuffer.setLength(0);  // Clear the buffer
    }


    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
}