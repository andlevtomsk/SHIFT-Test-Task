package test;

import service.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tests {

    /**
     * This function returns whether two files have same content or not
     *
     * @param path1, path2
     */
    public static boolean compareFiles(Path path1, Path path2) {
        try (BufferedReader firstReader = Files.newBufferedReader(path1);
             BufferedReader secondReader = Files.newBufferedReader(path2)) {

            long lineNumber = 1;
            String line1 = "", line2 = "";

            while ((line1 = firstReader.readLine()) != null) {
                line2 = secondReader.readLine();
                if (line2 == null || !line1.equals(line2)) {
                    firstReader.close();
                    secondReader.close();
                    return false;
                }
                lineNumber++;
            }
            if (secondReader.readLine() == null) {
                firstReader.close();
                secondReader.close();
                return true;
            } else {
                firstReader.close();
                secondReader.close();
                return false;
            }
        } catch (IOException e) {
            System.out.println("The wrong path(-s) was(-ere) set in Test class. The program will continue working, but the result can be wrong.");
            return false;
        }
    }

    public static void main(String[] args) {
        Main.main(new String[]{"src/test/first/in1.txt", "src/test/first/in2.txt", "-f", "-p", "sample-", "-o", "src/test/first/output"});
        Main.main(new String[]{"-f", "src/test/second/in1.txt", "-p", "NEW", "-o", "src/test/second/output"});
        Main.main(new String[]{"-f", "src/test/third/in1.txt", "src/test/third/in2.txt", "src/test/third/in3.txt"});
        Main.main(new String[]{"-a", "-f", "src/test/fourth/in1.txt", "src/test/fourth/in2.txt", "-o", "src/test/first/output", "-p", "sample-", "src/test/fourth/in3.txt"});
        Main.main(new String[]{"-a", "-f", "-o", "src/test/second/output", "src/test/fifth/in1.txt", "src/test/fifth/in2.txt", "-p", "NEW", "src/test/fifth/in3.txt"});

        Path outputFirstIntegersPath = Paths.get("src/test/first/output/sample-integers.txt");
        Path outputFirstFloatsPath = Paths.get("src/test/first/output/sample-floats.txt");
        Path outputFirstStringsPath = Paths.get("src/test/first/output/sample-strings.txt");

        Path outputSecondIntegersPath = Paths.get("src/test/second/output/NEWintegers.txt");
        Path outputSecondFloatsPath = Paths.get("src/test/second/output/NEWfloats.txt");
        Path outputSecondStringsPath = Paths.get("src/test/second/output/NEWstrings.txt");

        Path sampleFirstIntegersPath = Paths.get("src/test/first/output/answer-integers.txt");
        Path sampleFirstFloatsPath = Paths.get("src/test/first/output/answer-floats.txt");
        Path sampleFirstStringsPath = Paths.get("src/test/first/output/answer-strings.txt");

        Path sampleSecondIntegersPath = Paths.get("src/test/second/output/answer-integers.txt");
        Path sampleSecondFloatsPath = Paths.get("src/test/second/output/answer-floats.txt");
        Path sampleSecondStringsPath = Paths.get("src/test/second/output/answer-strings.txt");


        if (compareFiles(outputFirstIntegersPath, sampleFirstIntegersPath)) {
            System.out.println("First integers file is correct");
        } else {
            System.out.println("First integers file isn't correct");
        }

        if (compareFiles(outputFirstFloatsPath, sampleFirstFloatsPath)) {
            System.out.println("First floats file is correct");
        } else {
            System.out.println("First floats file isn't correct");
        }

        if (compareFiles(outputFirstStringsPath, sampleFirstStringsPath)) {
            System.out.println("First strings file is correct");
        } else {
            System.out.println("First strings file isn't correct");
        }

        if (compareFiles(outputSecondIntegersPath, sampleSecondIntegersPath)) {
            System.out.println("Second integers file is correct");
        } else {
            System.out.println("Second integers file isn't correct");
        }

        if (compareFiles(outputSecondFloatsPath, sampleSecondFloatsPath)) {
            System.out.println("Second floats file is correct");
        } else {
            System.out.println("Second floats file isn't correct");
        }

        if (compareFiles(outputSecondStringsPath, sampleSecondStringsPath)) {
            System.out.println("Second strings file is correct");
        } else {
            System.out.println("Second strings file isn't correct");
        }
    }
}
