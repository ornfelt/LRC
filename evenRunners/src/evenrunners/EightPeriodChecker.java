package evenrunners;

import java.io.IOException;
import java.util.ArrayList;
import static javax.swing.JOptionPane.*;

/**
 *
 * @author Jonas Örnfelt
 */
public class EightPeriodChecker {

    String eightPeriodFP = "SeventhPeriodList.txt";
    String oddValueList = "OddValueList.txt";
    ArrayList<Double> resultList = new ArrayList<>();
    IO io = new IO();

//    Function that initializes check up till maxvalue is reached
    public void startCheck(int maxValue, int period) throws IOException {

//        for (int i = 0; i < maxValue; i++) {
        double[] eightValues = new double[maxValue];

        for (int j = 0; j < maxValue; j++) {
            eightValues[j] = (double) (j + 1) / 8;
//                System.out.println("eightValues j: " + eightValues[j]);
        }
        checkPeriod(eightValues, period);
//        }
    }

//    Function that compares decimals of 8p to the decimal range: < 8/64, or > 56+(64*i)/64 and < 72+(64*i)/64
    private void checkPeriod(double[] values, int p) throws IOException {
//        General case: (where i >= 0): (1+8i/8) * Δv/8 --> (1+8i)*Δv / 64

        double[] deltaV = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            deltaV[i] = i + 1;
//            System.out.println("deltaV i: " + deltaV[i]);
        }

        for (int i = 0; i < values.length; i++) {
            double[] results = new double[p];
            for (int j = 0; j < p; j++) {
                results[j] = (double) (1 + p * j) / 8 * values[i];
//                System.out.println("results j: " + results[j]);
            }
            resultList = checkRange(results, deltaV[i]);
            System.out.println("resultList: " + resultList + " " + resultList.size());
            if (!resultList.isEmpty()) {
                io.printEightPeriodList(eightPeriodFP, resultList);
            }
        }
        String oddValueSizeInput = showInputDialog(null, "How many values do you want to check for the odd value calculation?", "50");
        int oddValueSize = Integer.parseInt(oddValueSizeInput);
        oddValueCalc(oddValueSize);
    }

//    Function that checks the decimal-range: < 8/64, or > 56+(64*i)/64 and < 72+(64*i)/64
    private ArrayList<Double> checkRange(double[] r, double dV) {
        ArrayList<Double> rList = new ArrayList<>();
//        No clue why it's necessary to declare one as 1, instead of writing double oneDivK = 1/8; Work-around works tho...
        double one = 1;
        double oneDivK = one / 8;
        boolean dVAdded = false;

//        int rangeValue = 0;
//
//        while (true) {
//            if (((72 + (64 * rangeValue)) / 64) > ((1 + 8 * 8) * dV) / 64) {
//                break;
//            }
//            rangeValue++;
//        }
//        rangeValue++;
//            System.out.println("rangeValue: " + rangeValue);
//        double[] rangeValues = new double[rangeValue * 2];
//        for (int j = 0; j < rangeValue; j++) {
//            rangeValues[j] = (double) (56 + (64 * j)) / 64;
//            rangeValues[j + 1] = (double) (72 + (64 * j)) / 64;
////                System.out.println("rangeValues j + j+1 " + rangeValues[j] + " " + rangeValues[j + 1]);
//        }
//
//        for (int j = 0; j < r.length; j++) {
//            int rangeCounter = 0;
//            if (dV > 8) {
//                for (int k = 0; k < rangeValue - 1; k++) {
//                    if (r[j] >= rangeValues[k] && r[j] <= rangeValues[k + 1 + rangeCounter]) {
//                        rList.add(r[j]);
//                        rList.add(dV);
//                        rList.add((double) LonelyRunner.seperate);
////                        System.out.println("rList: " + rList);
//                    }
//                    rangeCounter++;
//                }
//            } else {
//
//                for (int k = 0; k < rangeValue - 1; k++) {
//                    if (r[j] <= oneDivK || (r[j] >= rangeValues[k] && r[j] <= rangeValues[k + 1 + rangeCounter])) {
//                        rList.add(r[j]);
//                        rList.add(dV);
//                        rList.add((double) LonelyRunner.seperate);
//                        break;
////                        System.out.println("rList: " + rList);
//                    }
//                    rangeCounter++;
//                }
//            }
        for (int i = 0; i < r.length; i++) {
            double rDec = r[i] - (int) r[i];
            if (rDec <= oneDivK || rDec >= oneDivK * 7) {
                if (!dVAdded) {
                    rList.add(dV);
                    dVAdded = true;
                }
                rList.add((double) i);
                rList.add(r[i]);
                rList.add((double) LonelyRunner.seperate);
            }
        }

        return rList;
    }

    private void oddValueCalc(int size) {
        ArrayList<String> oddValueToPrint = new ArrayList<>();
        double oddValues[] = new double[size*2];
        for (int i = 1; i < (size * 2)-2; i++) {
            if (i == 1) {
                oddValues[i - 1] = (double) (i);
                System.out.println("oddValues i: " + oddValues[i - 1]);
            } else {
                oddValues[i - 2] = (double) (i);
                System.out.println("oddValues i: " + oddValues[i - 2]);
            }
            i++;

        }

        for (int i = 0; i < size; i++) {
            double oddValuesResult[] = new double[size];
            for (int j = 0; j < size; j++) {
                oddValuesResult[j] = ((((oddValues[i] * (i + 1)) / oddValues[i]) + (1 / 8)) * ((j + 1) / 8));
                oddValueToPrint.add(String.valueOf(oddValues[i]) + " * " + String.valueOf(i + 1) + " / " + String.valueOf(oddValues[i]) + " + " + String.valueOf(1 / 8) + " * " + String.valueOf((j + 1) / 8));
            }
            System.out.println(oddValueToPrint);
        }

//        (8/4 + 1/8) * 1/8 = 8/32 + 1/64 --> 1/4 + 1/64 = NO 2
    }
}
