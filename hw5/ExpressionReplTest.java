public class ExpressionReplTest {
    private static int totalTests = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {
        testInputList(
            "exponent",
            "1.0",
            "1^0"
        );

        testInputList(
            "evaluate simple expression",
            "8.0",
            "3 + 5"
        );

        testInputList(
            "store and retrieve variable",
            "14.0",
            "x = 10.0", "(x + x) / 4 * 3 - 1"
        );
        
        testInputList(
            "update variable",
            "4.0",
            "a = 0", "a = a + 1", "a = a + 1", "a = a + 1", "a = a + 1", "a"
        );

        testInputList(
            "sample test from instructions",
            "243.0",
            "x = (35 - 8) ^ 2", "y = x / 3", "y"
        );

        testInputList(
            "incrementing z",
            "15.0",
            "z = 10", "z = z + 1", "z = z + 1", "z = z + 1", "z = z + 1", "z = z + 1", "z"
        );

        System.out.println("Tests passed: " + testsPassed + "/" + totalTests);
    }

    public static void testInputList(String name, String expectedOutput, String... inputs) {
        totalTests += 1;

        ExpressionRepl repl = new ExpressionRepl();
        String output = null;
        
        // Place all the commands in the repl
        for (String input : inputs) output = repl.evaluateInput(input);

        if (expectedOutput.equals(output)) {
            System.out.println(name + " passed.");
            testsPassed++;
        } else {
            System.out.println(name + " failed.");
            System.out.println(name + " Failed - Expected: " + expectedOutput + ", got: " + output);
        }
    }
}
