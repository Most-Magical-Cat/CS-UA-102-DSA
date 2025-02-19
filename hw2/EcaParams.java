public class EcaParams {
    public final RuleSet rule = new RuleSet();
    public AnsiColor offColor = new AnsiColor("Black");
    public AnsiColor onColor = new AnsiColor("White");;
    public long randomSeed = 0;
    public int width = 100;
    public double init = 0.1;
    public long iterations = 1000;

    public class RuleSet{

        public void setRule (int n, double d) {
                rules[n] = d;
            }

        public void setWolframRule (int n) {
            int s = Integer.parseInt(Integer.toBinaryString(n));
            for (int i = 0; i < 8; i++) {
                rules[i] = (n >> i) & 1;;
            }
        }
    }
    public final double[] rules = new double[8];


    public EcaParams(String[] args) {
        int index = 0;

        while(index < args.length) {
            String currentItem = args[index];

            if (!isFlag(currentItem)) {
                throw new IllegalArgumentException("Expected a flag");
            }

            int flagInputs = getNumberOfFlagInputs(args, index);
            index += 1;

            if (currentItem.equals("-rules")) {
                if (flagInputs == 8) {
                    for(int i = 0; i < 8; i++) {
                        double d = Double.parseDouble(args[index + i]);
                        rule.setRule(i, d);
                    }
                } else if (flagInputs == 1) {
                    int n = Integer.parseInt(args[index]);
                    rule.setWolframRule(n);
                } else {
                    throw new IllegalArgumentException("-rules flag should have either a wolfram number or 8 doubles");
                }
            } else if (currentItem.equals("-off-color")) {
                if (flagInputs == 1) {
                    offColor = new AnsiColor(args[index]);
                } else {
                    throw new IllegalArgumentException("-off-color flag should have one color input");
                }
            } else if (currentItem.equals("-on-color")) {
                if (flagInputs == 1) {
                    onColor = new AnsiColor(args[index]);
                } else {
                    throw new IllegalArgumentException("-on-color flag should have one color input");
                }
            } else if (currentItem.equals("-random-seed")) {
                if (flagInputs == 1) {
                    randomSeed = Integer.parseInt(args[index]);
                } else {
                    throw new IllegalArgumentException("-random-seed flag should have one int input");
                }
            } else if (currentItem.equals("-size")) {
                if (flagInputs == 1) {
                    width = Integer.parseInt(args[index]);
                } else {
                    throw new IllegalArgumentException("-size flag should have one int input");
                }
            } else if (currentItem.equals("-init")) {
                if (flagInputs == 1) {
                    init = Double.parseDouble(args[index]);
                } else {
                    throw new IllegalArgumentException("-init flag should have one double input");
                }
            } else if (currentItem.equals("-iter")) {
                if (flagInputs == 1) {
                    iterations = Integer.parseInt(args[index]);
                } else {
                    throw new IllegalArgumentException("-iter flag should have one long input");
                }
            } else {
                throw new IllegalArgumentException("unknown flag: " + currentItem);
            }

            index = index + flagInputs;
        }
    }

    private int getNumberOfFlagInputs(String[] args, int flagLocation) {
        int i = 1;

        while(flagLocation + i < args.length) {
            if (isFlag(args[flagLocation + i])) break;
            i++;
        }

        return i - 1;
    }

    private boolean isFlag(String arg) {
        return arg.toCharArray()[0] == '-';
    }
}