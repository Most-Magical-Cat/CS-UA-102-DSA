/* Homework assignment 1: Fibonacci Race
 */


public class FibonacciRace {

  public static final double GoldenRatio = (1 + Math.sqrt(5)) / 2;

  static long fibre(int n) {
    if (n>1) { return fibre(n-1)+fibre(n-2);}
    else if (n == 1) {return 1;}
    else {return 0;}
  }

  static long fibdy(int n) {
    if (n == 0 ) return 0;
    if (n == 1) return 1;

    long[] fibarray = new long[n+1];
    fibarray[1]=1;
    fibarray[0]=0;
    for (int j = 2; j < n+1; j++) {
      fibarray[j] = fibarray[j-1] + fibarray[j-2];
    }
    return fibarray[n];
  }
  public static void main(String[] args) {
    // Your code goes here 
    if(args.length < 2) {System.out.println("Enter Something!");
    return;
    }
    int maxInput = Integer.parseInt(args[0]);
    int maxTime = Integer.parseInt(args[1]);

    if (maxInput < 0 || maxTime < 0) {
      System.out.println("Invalid number(s)");
      return;
    }

    boolean isRecursion = true;
    boolean isDynamic = true;
    boolean isFormula = true;

    for (int i = 0; i<=maxInput; i++) {
      //RECURSION
      if (isRecursion) {
        long startTime1 = System.currentTimeMillis();
        long fr = fibre(i);
        long endTime1 = System.currentTimeMillis();
        long timeToRun1 = endTime1 - startTime1;
        System.out.println("RECURSE\t"+i+"\t"+fr+"\t"+timeToRun1);
        if (timeToRun1 > maxTime) {isRecursion = false;}
      }
      
      //DYNAMIC
      if (isDynamic) {
      long startTime2 = System.currentTimeMillis();
      long fd = fibdy(i);
      long endTime2 = System.currentTimeMillis();
      long timeToRun2 = endTime2 - startTime2;
      System.out.println("DYNAMIC\t"+i+"\t"+fd+"\t"+timeToRun2);
      if (timeToRun2 > maxTime) {isDynamic = false;}
      }
      
      if (isFormula) {
      long startTime3 = System.currentTimeMillis();
      double f = (Math.pow(GoldenRatio, i) - Math.pow(-GoldenRatio, -i)) / Math.sqrt(5);
      long ff = Double.valueOf(f).longValue();
      long endTime3 = System.currentTimeMillis();
      long timeToRun3 = endTime3 - startTime3;
      System.out.println("FORMULA\t" + i + "\t" + ff + "\t" +timeToRun3); 
      if (timeToRun3 > maxTime) {isFormula = false;}
      }
      
    }
  }
}