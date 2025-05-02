import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class HuffmanTree {
  private HuffmanNode root;

  private static class HuffmanNode implements Comparable<HuffmanNode> {
    final public String symbols;
    final public Double frequency;
    final public HuffmanNode left, right;

    //public HuffmanNode(String symbol, double frequency) { }

    //public HuffmanNode(HuffmanNode left, HuffmanNode right) { }

    //public int compareTo(HuffmanNode hn) { }

    public String toString() {
      return "<" + symbols + ", " + frequency + ">";
    }
  }

  public static class HuffmanHeapBuilder {
    public void insertSymbol(String symbol, int frequency) {
      // implement this
      // Build a HuffmanNode and keep track of it in an array
    }

    public BinaryHeap<HuffmanNode> heapify() {
      // implement this
      /** 
       * Use the BinaryHear constructor to perform 
       *  the=O(n) buildHeap operation to get a 
       *  min-heap of HuffmanNode objects
       */ 
      return null;
    }
  }

  public HuffmanTree(HuffmanNode root) {
    this.root = root;
  }

  public void printLegend() {
    // Implement this by printing the legend of the Huffman Code
  }

  public void printTreeSpec() {
    // Implement this by printing the tree specification of the Huffman Code in one line
  }

  /**
   * Converts special characters to their string escape sequence representation.
   * If the input symbol is a tab, pipe, or backslash, it returns the corresponding escape sequence.
   * Otherwise, it returns the symbol as is.
   * 
   * @param symbol A string containing a single character to be converted.
   * @return The string representation of the character, with special characters converted to escape sequences.
   */
  public static String convertSymbolToChar(String symbol) {
    if (symbol.equals("\t")) return "\\t";
    if (symbol.equals("|")) return "\\|";
    if (symbol.equals("\\")) return "\\\\";
    return symbol;
  }

  public static HuffmanTree createFromHeap(BinaryHeap<HuffmanNode> b) {
    // Implement this
    // Input: A Binary Heap of HuffmanNode object (leaves)
    // Output: A single HuffmanTree object

    return null;
  }

  /**
   * Usage from the command line:
   * 
   * MacOs / Unix
   * cat sample_freq.txt | java HuffmanTree 
   * cat sample_freq.txt | java HuffmanTree legend
   * 
   * Windows
   * type sample_freq.txt | java HuffmanTree 
   * type sample_freq.txt | java HuffmanTree legend
   */
  public static void main(String [] args) throws IOException {
    String mode = (args.length == 0)? "spec": args[0];

    HuffmanHeapBuilder heapBuilder = new HuffmanHeapBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String line;

    while ((line = reader.readLine()) != null) {
      String[] splitLine = line.split("\t");
      String symbol = splitLine[0];
      int frequency = Integer.parseInt(splitLine[1]);
      heapBuilder.insertSymbol(symbol, frequency);
    }

    reader.close();

    BinaryHeap<HuffmanNode> heap = heapBuilder.heapify();

    HuffmanTree tree = createFromHeap(heap);

    if (mode.toLowerCase().equals("legend")) {
      tree.printLegend();
    } else {
      tree.printTreeSpec();
    }
  }
}