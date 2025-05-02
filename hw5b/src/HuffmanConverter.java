import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class HuffmanConverter {
  // Usage from the command line:
  // cat just_to_say.txt | java HuffmanConverter encode just_to_say_spec.txt
  public static void main(String [] args) throws IOException {
    String mode = args[0].toLowerCase();
    String treeFile = args[1];

    String treeStr = readfile(treeFile);

    HuffmanTree tree = HuffmanTree.loadTree(treeStr);

    String input = readStdin();

    if (mode.equals("decode")) {
      tree.decode(input);
    } else if (mode.equals("analyze")) {
      int bits = analyze(tree, input);
      System.out.println("Encoded Bits: " + bits);
      System.out.println("Original Characters: " + input.length());
      System.out.println("Compression Ratio: " + (double) bits / (input.length()));
    } else if (mode.equals("encode")) {
      encode(tree, input);
    } else {
      System.out.println("Unknown Mode: " + mode);
    }
  }

  public static void encode(HuffmanTree tree, String input) {
    String[] array = tree.convertLegend();
    while (input.length() > 0) {
      String symbol = input.substring(0, 1);
      input = input.substring(1);
      int ascii = (int) symbol.charAt(0);
      String code = array[ascii];
      System.out.print(code);
    }
    System.out.print(array[128]);
  }

  public static int analyze(HuffmanTree tree, String input) {
    String counter = "";
    String[] array = tree.convertLegend();
    while (input.length() > 0) {
      String symbol = input.substring(0, 1);
      input = input.substring(1);
      int ascii = (int) symbol.charAt(0);
      String code = array[ascii];
      counter += code;
    }
    counter += array[128];
    return counter.length();
  }




  public static String readfile(String fileName) throws IOException {
    StringBuilder result = new StringBuilder();
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    int c;
    while ((c = reader.read()) != -1) {
        result.append((char)c);
    }
    reader.close();
    return result.toString();
  }

  public static String readStdin() throws IOException {
    StringBuilder result = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int c;
    while ((c = reader.read()) != -1) {
        result.append((char)c);
    }
    reader.close();
    return result.toString();
  }
}
