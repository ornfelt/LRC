package evenrunners;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static javax.swing.JOptionPane.*;

/**
 * Lonely Runner Program
 *
 * @author Jonas Örnfelt
 */
public class LonelyRunner {

    Sort sorter = new Sort();
    IO io = new IO();
    String fp = "LonelyRunnerList.txt";
    String fpTwo = "LonelyRunnerPeriodList.txt";
    String fpThree = "AnimationList.txt";
    String input;
    String[] arrSplit;
    public static int seperate = 10000;
    boolean neverLonelyFinal = true;
    DecimalFormat df = new DecimalFormat("#.#####");
    double[] aniRunners = new double[10];
    ArrayList<Double> aniResults = new ArrayList<>();
//        Constant needed to adjust velocities in the animation. Calculated with brute force (may need to be adjusted, as it differs depending on the computer running the code.
    public static double aniConstant = 0.00738741411584;
    boolean inputDone = false;

    public void kRunners(int currentRunner, boolean firstRun, int preK, double[] preV) throws IOException, InterruptedException {

        int k = preK;
        double[] v = preV;

        if (firstRun) {
            boolean oneCheck = false;
            while (!oneCheck) {
                String kvalue = showInputDialog(null, "Choose value for k", "8");
                k = Integer.parseInt(kvalue);
                if (k > 1) {
                    oneCheck = true;
                }
            }
            ArrayList<String> kList = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                kList.add(Integer.toString(i + 1));
            }
            String choiceText = kList.toString().replace("[", "").replace("]", "");

            while (!inputDone) {
                input = showInputDialog(null, "Enter different velocities for " + k + " runners. Seperate with comma(,) or try with the example given. \n"
                        + "The velocities are divided by 10 in the animation for simplicity's sake (1 = 0.1 etc.)", choiceText);
//                Splitting velocites from input, based on comma
                arrSplit = input.split(",");
//                if-statement preventing having more than k runners
                if (arrSplit.length == k) {
                    inputDone = true;
                }
            }
            /**
             * Saves all velocities in double array
             */
            double[] vInput = new double[arrSplit.length];

            for (int i = 0; i < arrSplit.length; i++) {
                String runner = arrSplit[i];
                vInput[i] = Double.parseDouble(runner);
            }
            v = vInput;

//            0 = yes, 1 = no, 2 = cancel
            int fileOption = showConfirmDialog(null, "Do you wish to delete old textfiles? (Doesn't include generated data)", "Select an option...", YES_NO_OPTION);

            if (fileOption == 0) {
                io.deleteFile(fp);
                io.deleteFile(fpTwo);
                io.deleteFile(fpThree);
            } else if (fileOption == 2) {
                showMessageDialog(null, "Goodbye!");
                System.exit(0);
            }
        }
        v = sorter.sort(v);
        double runnerToCheck = v[currentRunner];
        double runnerReplace = v[0];

        v[0] = runnerToCheck;
        v[currentRunner] = runnerReplace;

//        for (int i = 0; i < v.length; i++) {
//            System.out.println("Velocities sorted: " + v[i]);
//        }
        io.printArray(fp, v);
        io.printArray(fpThree, v);

        double dv[] = dVelocity(v, v.length, 0);
        io.printArray(fp, dv);

//        Loop that transforms values in dv to dvRatio arrays that are being looped as well
        for (int i = 0; i <= dv.length - 1; i++) {
            double dvRatio[] = dvRatio(dv, dv.length, i);
            io.printDoubleText(fp, dv[i]);
            io.printDoubleTextForPeriodList(fpTwo, dv[i]);
            io.printDvRatio(fp, dvRatio);
            calcRunner(k, dvRatio.length, dvRatio);
        }
        if (neverLonelyFinal) {
            System.out.println("Runner never lonely ever!!!");
        } else {
//            System.out.println("Runner lonely at some point.");
        }
        runnerAnimation(v, aniResults, currentRunner, neverLonelyFinal);
    }

    //    length = arr.length, r = the velocity that dV will be calculated from
    private double[] dVelocity(double[] n, int length, int r) {
        ArrayList<Double> tempList = new ArrayList<>();
        for (int i = 0; i < n.length; i++) {
            tempList.add(n[i]);
        }

        double tempValue = tempList.remove(r);

        double dv[] = new double[length - 1];
        for (int i = 0; i < n.length - 1; i++) {
            dv[i] = tempList.get(i) - tempValue;
            dv[i] = Math.abs(dv[i]);
//            System.out.println("dv i: " + dv[i]);
        }

        return dv;
    }

    private double[] dvRatio(double[] n, int length, int r) {
        ArrayList<Double> tempList = new ArrayList<>();
        ArrayList<Double> dvTempList = new ArrayList<>();
        for (int i = 0; i < n.length; i++) {
            tempList.add(n[i]);
        }
        double tempValue = tempList.remove(r);

        for (int i = 0; i < length - 1; i++) {
            dvTempList.add(tempList.get(i) / tempValue);
        }
        double dvRatio[] = new double[dvTempList.size()];
        for (int i = 0; i < dvTempList.size(); i++) {
            dvRatio[i] = dvTempList.get(i);
//            System.out.println("dvRatio i: " + dvRatio[i]);
        }
        return dvRatio;
    }

    private void calcRunner(int k, int length, double dvRatio[]) throws IOException {
        int r = 0;
        double one = 1;
        double kthResult;
        double result[] = new double[k * seperate];
        double resultCheck[] = new double[k * seperate];
        double dvTotal = 0;
        ArrayList<Integer> notLonely = new ArrayList<>();
        notLonely.add(seperate);
        ArrayList<Integer> pReachedValue = new ArrayList<>();
        ArrayList<Double> neededRatio = new ArrayList<>();
        ArrayList<Integer> tempNotLonelyList = new ArrayList<>();
        neededRatio.add((double) seperate);
        int[] resultInt = new int[k * 100];
        df.setRoundingMode(RoundingMode.CEILING);
        boolean pCheck = true;

//        Here, the Δv/Δv ratio is being checked against the conjectures rules
        for (int i = 0; i < length; i++) {
            int p = 0;
            boolean pReached = false;
            boolean notLonelyAdded = false;

            int j = 0;
            while (pReached == false) {

                double kth = (double) k;

                kth = (one / kth) + j;
                kthResult = kth;
//                System.out.println("kth equals: " + kth);
//                System.out.println("dvR i equals: " + dvRatio[j]);

                result[r] = dvRatio[i] * kth;
//                System.out.println("result equals: " + result);
                kth = kth - j;
                int tempoInt = (int) result[r];
                resultInt[j] = (int) result[r];
                resultCheck[j] = result[r] - resultInt[j];
//                mathround to fix 
                if (p != 0) {
                    for (int l = 0; l < j; l++) {
//                        System.out.println("resultCheck j equals: " + ((double) Math.round(resultCheck[j] * 10000d) / 10000d));
//                        System.out.println("resultCheck l equals: " + ((double) Math.round(resultCheck[l] * 10000d) / 10000d));
                        if (((double) Math.round(resultCheck[j] * 10000d) / 10000d) == ((double) Math.round(resultCheck[l] * 10000d) / 10000d)) {
                            pReached = true;
                            notLonely.add(seperate);
                            if (notLonelyAdded == true) {
//                                System.out.println("pReached, " + "period: " + p);
                                pReachedValue.add(p);
//                                neededRatio-list redundant for Δv/Δv < 1 (?)
//                                neededRatio.add(dvRatio[i]);
//                                if (p % 2 == 0) {
                                String tempPrintString = "For Δv/Δv: " + dvRatio[i] + "\n period: " + p + "\n  Values where t equals not lonely: ";
                                for (int m = 0; m < tempNotLonelyList.size(); m++) {
                                    if (tempNotLonelyList.get(m) != seperate) {
                                        tempPrintString += tempNotLonelyList.get(m) + ", ";
                                    }
                                }
                                io.printString(fpTwo, tempPrintString);
//                                }
                                tempNotLonelyList.clear();
                            }
                            break;
                        }
                    }
                }
                if (pReached == false) {
//                System.out.println("tempoInt equals: " + tempoInt);

                    if ((double) (Math.round((result[r] - tempoInt) * 10000d) / 10000d) < kth || (double) (Math.round((result[r] - tempoInt) * 10000d) / 10000d) > (kth * (k - 1))) {
                        notLonely.add(p);
//                        The value added to notLonely, is also added to a temporary list that will print to a textfile
                        tempNotLonelyList.add(p);
                        notLonelyAdded = true;
//                        System.out.println("notLonely latest add: " + notLonely.get(notLonely.size() - 1));
//                        System.out.println("Runner NOT lonely at: " + result[r] + " for Δv: " + (i + 1) + " * " + kthResult);
                    } else {
//                        System.out.println("Runner lonely at: " + result[r] + " for Δv " + (i + 1) + " * " + kthResult);
                    }
                    p++;
                    r++;
                }
                j++;
            }
        }
        for (int i = 0; i < dvRatio.length; i++) {
            dvTotal += dvRatio[i];
//            System.out.println("dvTotal equals: " + dvTotal);
        }
        for (int i = 0; i < dvRatio.length; i++) {
            if (dvRatio[i] % 1 != 0) {
                pCheck = false;
            }
        }

        if (!pReachedValue.isEmpty()) {
            if (checkLoneliness(notLonely, pReachedValue, k) == true) {
                System.out.println("Runner never lonely for this check!");
                io.printString(fp, "Runner never lonely for this check!");
            } else {
                System.out.println("Runner lonely at some point.");
                neverLonelyFinal = false;
            }
//            For the case where p = 1
        } else if (notLonely.contains(seperate) && dvTotal % 1 == 0 && pCheck) {
//            System.out.println("Δv Should be 1, Period equals 1.");
            System.out.println("Runner lonely at some point.");
            neverLonelyFinal = false;
            double tempOne = 1.0;
            aniResults.add((tempOne / k));
        }
    }

    private boolean checkLoneliness(ArrayList<Integer> n, ArrayList<Integer> p, int k2) throws IOException {
//        p = pReached period
//        n = value when runner is not lonely

//        List containing the results that will be checked
        ArrayList<Integer> numberList = new ArrayList<>();
//        Variable with max-period that's being looped so that a high enough value is controlled for the list
        int maxP = (p.get(p.size() - 1)) * 10;
        int period = 0;
        io.printPeriodReachedList(fp, p);
        io.printList(fp, n);
        io.printList(fpTwo, n);

        while (!p.isEmpty() || !n.isEmpty()) {
//            Firstly, the value where the runner isn't lonely is added to the result list.
//            The result list always starts with 0 to seperate the results and so that the for loop works properly.
//            When the first value in n[] is 0, a 0 is added to the result list to seperate the results, then the 0 is removed as well as the next value in n[]
            if (n.get(0) == seperate && !p.isEmpty()) {
//                numberList.add(0);
                n.remove(0);
                period = p.remove(0);
//                System.out.println("period equals: " + period);
            }
//            Workaround for when period = 1
            while (n.get(0) == seperate && period == 1) {
                n.remove(0);
            }
            numberList.add(n.remove(0));
//            System.out.println("Latest value in numberlist: " + numberList.get(numberList.size() - 1));

            if (numberList.get(numberList.size() - 1) != seperate && period == 1) {
                aniResults.add((double) seperate);
                return true;
            }
//            for (int j = 1; numberList.get(numberList.size() - 1) < maxP; j++) {
            while (numberList.get(numberList.size() - 1) < maxP) {
//                System.out.println("Last value in result list: " + numberList.get(numberList.size() - 1));
                numberList.add((numberList.get(numberList.size() - 1)) + period);
            }
//            System.out.println("Absolute last value in result list: " + numberList.get(numberList.size() - 1));
        }
//        for (int i = 0; i < numberList.size(); i++) {
//            System.out.println("numberList i equals: " + numberList.get(i));
//        }

        boolean checkList = true;
        for (int i = 0; i <= maxP; i++) {
            if (!numberList.contains(i)) {
                checkList = false;
                String ioPrint = "checklist set to false because of: " + i;
//                System.out.println(ioPrint);
                io.printString(fp, ioPrint);
                io.printInt(fpThree, i);
//                Two double variables added to easier show what's happening...
                double kth2 = ((double) 1 / k2);
                double aniAdd = ((double) i + kth2);
                aniResults.add(aniAdd);
                break;
            } else {
//                System.out.println("checkList set to true for i: " + i);
            }
        }
        if (checkList == true) {
            io.printInt(fpThree, seperate);
            aniResults.add((double) seperate);
            return true;
        } else {
            return false;
        }
    }

//    Function that starts animation
    public void runnerAnimation(double[] aV, ArrayList aniR, int btnValue, boolean neverLonelyAni) throws InterruptedException {
        Circle.startAnimation(aV, aniR, btnValue, neverLonelyAni);
    }
}
