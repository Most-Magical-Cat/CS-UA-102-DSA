import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class FrequencyCounter {
  private int[] frequencies;
  
  public FrequencyCounter() {
    frequencies = new int[128];  // ASCII array like in FrequencyCounter
  }
  
  public void fromStdin() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while(reader.ready()) {
      int c = reader.read();
      frequencies[c] += 1;
    }
  }
  
  public void printFrequencies() {
    boolean isFirst = true;
    System.out.println("\\e\t1");

    for (int i = 0; i < frequencies.length; i++) {
      if (frequencies[i] == 0) continue;

      String symbol = asciiToSymbol(i);

      System.out.print(symbol);
      System.out.print("\t");
      System.out.println(frequencies[i]);
    }
  }

  public String asciiToSymbol(int i) {
    switch (i) {
      case 9: return "\\t";
      case 10: return "\\n";
      case 13: return "\\r";
      case 8: return "\\b";
      case 0: return "\\0";
      default:
        if (i < 32 || i > 126) {
          return String.format("\\x%02X", i); // Show non-printable as hex
        }
        return Character.toString((char) i);
    }
  }
  
  // Utility methods to access the data
  public int getCount(char c) {
    return frequencies[c];
  }
  
  public int getSpaceCount() {
    return frequencies[32];
  }
  
  public int[] getAllFrequencies() {
    return frequencies.clone();  // Return a copy to prevent modification
  }
  
  /**
   * Usage from the command line:
   * 
   * MacOs / Unix
   * cat just_to_say.txt | java FrequencyCounter > jts_freq.txt
   * 
   * Windows
   * type just_to_say.txt | java FrequencyCounter > jts_freq.txt
   */
  public static void main(String[] args) throws IOException {
    FrequencyCounter parser = new FrequencyCounter();
    parser.fromStdin();
    parser.printFrequencies();
  }
}