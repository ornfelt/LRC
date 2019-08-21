package evenrunners;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.*;

/**
 *
 * @author Jonas Örnfelt
 */
public class EvenRunners {

    static double[] first;

    /**
     * Program for The Lonely Runner Conjecture
     * @param args
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] firstChoices = {"Run Lonely Runner Program", "Run Cheat Program (k+1 runners, instead of k)", "Run Generate Data Program", "Run Eight-period Checker"};

        String firstInput = (String) showInputDialog(null, "Choose the program you'd like to run", "Lonely Runner", QUESTION_MESSAGE, null, firstChoices, firstChoices[0]);

        if (firstInput.equals(firstChoices[0])) {
            LonelyRunner lonelyRunnerChoice = new LonelyRunner();
            lonelyRunnerChoice.kRunners(0, true, 0, first);
        } else if (firstInput.equals(firstChoices[1])) {
            testRunner testRunnerChoice = new testRunner();
            testRunnerChoice.kRunners(0, true, 0, first);
        } else if (firstInput.equals(firstChoices[2])) {
            GenerateData generateDataChoice = new GenerateData();
            generateDataChoice.kRunners(0, true, 0, first);

//            0 = yes, 1 = no, 2 = cancel
            int generateInput = showConfirmDialog(null, "Do you want to open the generated data document?", "Select an option", YES_NO_OPTION);
            if (generateInput == 0) {
                IO io = new IO();
                String fileToOpen[] = new String[]{"cmd", "/c", "C:\\Program Files\\Notepad++\\notepad++.exe ", "GenerateDataPeriodList.txt"};
                io.openTextFile(fileToOpen);
            } else if (generateInput == 1) {
                runAgain();
            } else {
                showMessageDialog(null, "Goodbye!");
                System.exit(0);
            }

        } else if (firstInput.equals(firstChoices[3])) {
            EightPeriodChecker eightPeriodChoice = new EightPeriodChecker();
            String eightGenerateSize = showInputDialog(null, "How many values do you want to check?", "200");
            int eightSize = Integer.parseInt(eightGenerateSize);

            eightPeriodChoice.startCheck(eightSize, 8);

//            0 = yes, 1 = no, 2 = cancel
            int eightGenerateInput = showConfirmDialog(null, "Do you want to open the generated data document?", "Select an option", YES_NO_OPTION);
            if (eightGenerateInput == 0) {
                IO io = new IO();
                String fileToOpen[] = new String[]{"cmd", "/c", "C:\\Program Files\\Notepad++\\notepad++.exe ", "EightPeriodList.txt"};
                io.openTextFile(fileToOpen);
            } else if (eightGenerateInput == 1) {
                runAgain();
            } else {
                showMessageDialog(null, "Goodbye!");
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public static void runAgain() {
//        0 = yes, 1 = no, 2 = cancel
        int runAgainOption = showConfirmDialog(null, "Do you want to run any of the programs again?", "Select an option...", YES_NO_OPTION);
        String[] runAgainArray = {"", ""};

        if (runAgainOption == 0) {
            try {
                EvenRunners.main(runAgainArray);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Circle.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showMessageDialog(null, "Goodbye!");
            System.exit(0);
        }
    }
}
