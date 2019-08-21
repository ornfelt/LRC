package evenrunners;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonas Örnfelt
 */
public class IO {

    public void printList(String filePath, List text) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print(text + "\n");
        textToWrite.close();
    }

    public void printArray(String filePath, double[] text) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print("-------------------------------------------------------------------------\n");
        for (int i = 0; i < text.length; i++) {
            textToWrite.print(text[i] + ", ");
        }
        textToWrite.print("\n");
        textToWrite.close();
    }

    public void printDvRatio(String filePath, double[] text) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print("\n" + "The following values of Δv/Δv are now being checked: ");
        for (int i = 0; i < text.length; i++) {
            textToWrite.print(text[i] + ", ");
        }
        textToWrite.print("\n");
        textToWrite.close();
    }

    public void printPeriodReachedList(String filePath, ArrayList text) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print("This is the list containing period-values, followed by the list containing values of t when the runner is not lonely: " + text + "\n");
        textToWrite.close();
    }

    public void printDoubleText(String filePath, double text) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print("\n" + "Δv being checked: " + text);
        textToWrite.close();
    }

    public void printDoubleTextForPeriodList(String filePath, double text) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print("------------------------------------------------------------------------------------------------------------------------------\n" + "Δv being checked: " + text + "\n" + "------------------------------------------------------------------------------------------------------------------------------\n");
        textToWrite.close();
    }

    public void printString(String filePath, String text) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print(text + "\n");
        textToWrite.close();
    }

    public void printNeededRatioList(String filePath, ArrayList text) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print("This is the neededRatio-list: " + text + "\n");
        textToWrite.close();
    }

    public void printInt(String filePath, int numb) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print(numb + ", ");
        textToWrite.close();
    }

    public void deleteFile(String fp) {
        try {
            Files.deleteIfExists(Paths.get(fp));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");

        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
        System.out.println("Deletion successful.");
    }

    public void openTextFile(String[] fileName) throws IOException {
//        ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "GenerateDataPeriodList.txt");
//        pb.start();

        try {
            Runtime.getRuntime().exec(fileName);
        } catch (Exception e) {

        }
    }

    public void printEightPeriodList(String filePath, List text) throws IOException {
        double value;
        ArrayList<String> lonelyList = new ArrayList<>();
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print("For Δv: " + text.remove(0) + "\n");
        for (int i = 0; i < text.size(); i++) {
            value = Double.parseDouble(text.get(i + 1).toString());
            textToWrite.print("     Not lonely at: " + text.get(i) + " because of: " + (value * 64) + "/" + "64" + " = " + text.get(i + 1) + "\n");
            lonelyList.add(text.get(i).toString());
            i += 2;
        }
        textToWrite.print("------------------------------------------------------------------------------------------------------------------------------\n");
        textToWrite.close();
        printEightPeriodString("EightPeriodLonelyList.txt", lonelyList);
    }

    private void printEightPeriodString(String filePath, List text) throws IOException {
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
        textToWrite.print(text + "\n");
        textToWrite.close();
    }
}
