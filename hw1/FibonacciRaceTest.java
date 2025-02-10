import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FibonacciRaceTest {
  public static void main(String[] args) {
    boolean errorsFound = false;
    int lineCount = 0;

    try {
      // Run FibonacciRace with arguments 10, 10
      Process process = new ProcessBuilder("java", "FibonacciRace", "10", "10").start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String[] expectedOrder = {"RECURSE", "DYNAMIC", "FORMULA"};
      
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
        String[] parts = line.split("\t");
        
        // Test 1: Ensure each line has exactly 4 items
        if (parts.length != 4) {
          System.out.println("Error: line " + lineCount + ": " + line);
          System.out.println("There must be 4 pieces of data on each line separated by tabs, this has " + parts.length);
          errorsFound = true;
          break;
        }

        // Test 2: Ensure the first item is one of the expected types
        if (!testValidLabel(parts[0])) {
          System.out.println("Error: line " + lineCount + ": " + line);
          System.out.println("First item is not RECURSE, DYNAMIC, or FORMULA. It was: " + parts[0]);
          errorsFound = true;
          break;
        }

        String labelCheck = testLineNumberForLabel(lineCount);
        if (labelCheck != null && !parts[0].equals(labelCheck)) {
          System.out.println("Error: line " + lineCount + ": " + line);
          System.out.println("Line 0 should run " + labelCheck);
          errorsFound = true;
          break;
        }

        // Test 4: Ensure the other three parts are integers
        boolean validNumbers = true;
        for (int i = 1; i < 4; i++) {
          try {
            Long.parseLong(parts[i]);
          } catch (NumberFormatException e) {
            validNumbers = false;
            break;
          }
        }

        if (!validNumbers) {
          System.out.println("Error: line " + lineCount + ": " + line);
          System.out.println("Invalid number found");
          errorsFound = true;
          break;
        }

        lineCount++;
      }

      // Wait for process to finish
      process.waitFor();
    } catch(Exception e) {
      System.out.println("Test Failed: Exception occurred - " + e.getMessage());
      errorsFound = true;
    }

    if (lineCount < 3) {
      System.out.println("Test Failed: every run should produce at least 3 lines");
      errorsFound = true;
    }

    if (!errorsFound) {
      System.out.println("No errors found");
    }
  }

  public static boolean testValidLabel(String s) {
    return (s != null && (
      s.equals("RECURSE") || s.equals("DYNAMIC") || s.equals("FORMULA"))
    );
  }

  public static String testLineNumberForLabel(int lineCount) {
    if (lineCount == 0) return "RECURSE";
    if (lineCount == 1) return "DYNAMIC";
    if (lineCount == 2) return "FORMULA";
    return null;
  }
}
