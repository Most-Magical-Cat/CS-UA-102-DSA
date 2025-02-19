import java.util.Arrays;
import java.util.Random;

public class ElementaryCellularAutomata {



    public static void main(String[] args) {
        EcaParams params = new EcaParams(args);



        // Start your code here
        boolean[] myarray = new boolean[params.width];
        boolean[] myarraynext = new boolean[params.width];

        Random random = new Random(params.randomSeed);


        for (int i = 0; i < params.width; i++) {
            myarray[i] = random.nextDouble() < params.init;
        }


        for (int i = 0; i < params.iterations; i++) {
            for (int j = 0; j < params.width; j++) {
                if (myarray[j]) {
                    params.onColor.printBlock();
                }
                else {
                    params.offColor.printBlock();
                }
            }
            for (int k = 0; k < params.width; k++) {

                int klast;
                int knext;
                double[] cases = params.rules;
                if (k == 0)  {klast = params.width - 1;}
                else {klast = k-1;}
                if (k == params.width - 1) {knext = 0;}
                else {knext = k+1;}
                if (!myarray[klast] && !myarray[k] && !myarray[knext]) {
                    if (cases[0] >= 1) {myarraynext[k] = true;}
                        else if (cases[0] <= 0) {myarraynext[k] = false;}
                        else {myarraynext[k] = random.nextDouble() < cases[0];}
                    }
                if (!myarray[klast] && !myarray[k] && myarray[knext]) {
                        if (cases[1] >= 1) {myarraynext[k] = true;}
                        else if (cases[1] <= 0) {myarraynext[k] = false;}
                        else {myarraynext[k] = random.nextDouble() < cases[1];}
                    }
                if (!myarray[klast] && myarray[k] && !myarray[knext]) {
                        if (cases[2] >= 1) {myarraynext[k] = true;}
                        else if (cases[2] <= 0) {myarraynext[k] = false;}
                        else {myarraynext[k] = random.nextDouble() < cases[2];}
                    }
                if (!myarray[klast] && myarray[k] && myarray[knext]) {
                        if (cases[3] >= 1) {myarraynext[k] = true;}
                        else if (cases[3] <= 0) {myarraynext[k] = false;}
                        else {myarraynext[k] = random.nextDouble() < cases[3];}
                    }
                if (myarray[klast] && !myarray[k] && !myarray[knext]) {
                        if (cases[4] >= 1) {myarraynext[k] = true;}
                        else if (cases[4] <= 0) {myarraynext[k] = false;}
                        else {myarraynext[k] = random.nextDouble() < cases[4];}
                    }
                if (myarray[klast] && !myarray[k] && myarray[knext]) {
                        if (cases[5] >= 1) {myarraynext[k] = true;}
                        else if (cases[5] <= 0) {myarraynext[k] = false;}
                        else {myarraynext[k] = random.nextDouble() < cases[5];}
                    }
                if (myarray[klast] && myarray[k] && !myarray[knext]) {
                        if (cases[6] >= 1) {myarraynext[k] = true;}
                        else if (cases[6] <= 0) {myarraynext[k] = false;}
                        else {myarraynext[k] = random.nextDouble() < cases[6];}
                    }
                if (myarray[klast] && myarray[k] && myarray[knext]) {
                        if (cases[7] >= 1) {myarraynext[k] = true;}
                        else if (cases[7] <= 0) {myarraynext[k] = false;}
                        else {myarraynext[k] = random.nextDouble() < cases[7];}
                    }
            }
            System.out.println();
            if (params.width >= 0) System.arraycopy(myarraynext, 0, myarray, 0, params.width);
        }
    }
}