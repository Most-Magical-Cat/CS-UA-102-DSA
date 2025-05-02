import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;


public class HuffmanTree {
  private HuffmanNode root;
  private HuffmanNode current;

  private static class HuffmanNode implements Comparable<HuffmanNode> {
    final public String symbols;
    final public Double frequency;
    final public HuffmanNode left, right;

    public HuffmanNode(String symbol, double frequency) {
      this.symbols = symbol;
      this.frequency = frequency;
      this.left = this.right = null;
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
      this.left = left;
      this.right = right;
      this.frequency = left.frequency + right.frequency;
      this.symbols = left.symbols + right.symbols;
    }

    public int compareTo(HuffmanNode hn) {
      int compare = this.frequency.compareTo(hn.frequency);
      if (compare == 0) {
        compare = this.symbols.compareTo(hn.symbols);
      }
      return compare;
    }

    public String toString() {
      return "<" + symbols + ", " + frequency + ">";
    }
  }

  public static class HuffmanHeapBuilder {

    private HuffmanNode[] nodeArray;
    private BinaryHeap<HuffmanNode> heap;
    private static final int DEFAULT_CAPACITY = 10;
    private int size;

    public HuffmanHeapBuilder() {
      nodeArray = new HuffmanNode[DEFAULT_CAPACITY];
      size = 0;
    }

    public void insertSymbol(String symbol, int frequency) {
      if (size == nodeArray.length - 1) {
        resizeArray();
      }
      nodeArray[size] = new HuffmanNode(symbol, frequency);
      size = size + 1;

    }

    private void resizeArray() {
      HuffmanNode[] newArray = new HuffmanNode[nodeArray.length * 2];
      System.arraycopy(nodeArray, 0, newArray, 0, nodeArray.length);
      nodeArray = newArray;
    }

    public BinaryHeap<HuffmanNode> heapify() {
      HuffmanNode[] trimmed = new HuffmanNode[size];
      System.arraycopy(nodeArray, 0, trimmed, 0, size);
//      for (int i = 0; i < size; i++) {
//        System.out.println(trimmed[i]);
//      }
      heap = new BinaryHeap<>(trimmed);
      return heap;
    }
  }

  public HuffmanTree(HuffmanNode root) {
    this.root = root;
  }

  public void printLegend() {
    printLegendHelper(root, "");
  }

  private void printLegendHelper(HuffmanNode node, String path) {
    if (node == null) return;

    printLegendHelper(node.left, path + "0");
    printLegendHelper(node.right, path + "1");

    if (node.left == null && node.right == null) {
      System.out.println(node.symbols + "\t" + path);
    }
  }

  public void printTreeSpec() {
    printTreeSpecHelper(root, true);
  }

  public void printTreeSpecHelper(HuffmanNode node, boolean isRight) {
    if (node == null) return;

    printTreeSpecHelper(node.left, false);
    printTreeSpecHelper(node.right, isRight);


    if (node.left == null && node.right == null) {
      System.out.print(convertSymbolToChar(node.symbols));
    } else if (!isRight) {
      System.out.print("|");
    }
  }


  public String advanceCurrent(char bit) {

    if (bit == '0') {
        current = current.left;
    } else if (bit == '1') {
        current = current.right;
    }

    if (current.left == null && current.right == null) { // It's a leaf
      String symbol = current.symbols;
      current = root; // Reset for the next symbol
      return symbol;
    }

    return null;
  }


  public void decode(String bitString) {
    current = root; // Start at the root of the tree
    for (int i = 0; i < bitString.length(); i++) {
        char bit = bitString.charAt(i);
        String symbol = advanceCurrent(bit);
        if (symbol != null) {
            System.out.print(symbol);
        }
    }
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
    while (b.getSize() > 1) {
      HuffmanNode node1 = b.extractMin();
      HuffmanNode node2 = b.extractMin();
      HuffmanNode newnode = new HuffmanNode(node2, node1);
      b.insert(newnode);
    }
      return new HuffmanTree(b.extractMin());
  }

  public static HuffmanTree loadTree(String storedTree) {
    Stack<HuffmanNode> stack = new Stack<>();

    for (int i = 0; i < storedTree.length(); i++) {
      char c = storedTree.charAt(i);

      if (c == '\\') {
        char next = storedTree.charAt(++i);
        switch (next) {
          case '\\': stack.push(new HuffmanNode(String.valueOf('\\'), 0)); break;
          case '|': stack.push(new HuffmanNode(String.valueOf('|'), 0)); break;
          case 'e': stack.push(new HuffmanNode(String.valueOf((char)128), 0)); break;
          case 'n': stack.push(new HuffmanNode(String.valueOf('\n'), 0)); break;
        }
      } else if (c == '|') {
        HuffmanNode right = stack.pop();
        HuffmanNode left = stack.pop();
        HuffmanNode parent = new HuffmanNode(left, right);
        stack.push(parent);
      } else {
        stack.push(new HuffmanNode(String.valueOf(c), 0));
      }
    }

    while (stack.size() > 1) {
      HuffmanNode right = stack.pop();
      HuffmanNode left = stack.pop();
      HuffmanNode parent = new HuffmanNode(left, right);
      stack.push(parent);
    }

    return new HuffmanTree(stack.pop());
    
  }

  public String[] convertLegend() {
      String[] ASCIIarray = new String[129];
      convertLegendHelper(root, "", ASCIIarray);
      return ASCIIarray;
    }

  public void convertLegendHelper(HuffmanNode node, String path, String[] ASCIIarray) {
      if (node == null) return;

      if (node.left == null && node.right == null) {
        int ascii = (int) (node.symbols.charAt(0));
        ASCIIarray[ascii] = path;
      } else {
        convertLegendHelper(node.left, path + "0", ASCIIarray);
        convertLegendHelper(node.right, path + "1", ASCIIarray);
      }
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