package service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Distributor {

    private String pathToResults;
    private String namePrefix;
    private final Boolean addToExistingFiles;
    private String integersFileName;
    private String floatsFileName;
    private String stringsFileName;
    private final ArrayList<File> files;
    private File integersFile;
    private File floatsFile;
    private File stringsFile;


    public Distributor(ArrayList<File> files, String pathToResults, String namePrefix, Boolean addToExistingFiles) {
        this.files = files;
        this.pathToResults = pathToResults;
        this.namePrefix = namePrefix;
        this.addToExistingFiles = addToExistingFiles;
    }

    /**
     * This function checks whether the string is long or not
     */
    private boolean isLong(String s) {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * This function checks whether the string is double or not
     */
    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s.replace(',', '.'));
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * This method writes the text in a file with the specific name
     *
     * @param file, text, name
     */
    private void write(File file, StringBuilder text, String name) {
        if (text.isEmpty()) {
            return;
        }
        try {
            FileWriter fw = new FileWriter(file, addToExistingFiles);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(text));
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("You set the wrong path to the output file or wrong name.\nPlease enter correct the path to output file without \"-o\" and then on the next line enter correct prefix without \"-p\".");
            Scanner sc = new Scanner(System.in);
            pathToResults = sc.nextLine();
            String newNamePrefix = sc.nextLine();
            name = name.replace(namePrefix, newNamePrefix);
            updateNamesAndFiles(newNamePrefix);
            sc.close();
            write(new File(pathToResults + File.separatorChar + name), text, name);
        } catch (IOException e) {
            System.out.println("There were some problems with writing to a file or closing Writer. The program continues to work, but the result may be incorrect.");
        }
    }

    /**
     * This method updates wrong files names to a new
     */
    private void updateNamesAndFiles(String newNamePrefix) {
        integersFileName = integersFileName.replace(namePrefix, newNamePrefix);
        floatsFileName = floatsFileName.replace(namePrefix, newNamePrefix);
        stringsFileName = stringsFileName.replace(namePrefix, newNamePrefix);
        namePrefix = newNamePrefix;

        integersFile = new File(pathToResults + File.separatorChar + integersFileName);
        floatsFile = new File(pathToResults + File.separatorChar + floatsFileName);
        stringsFile = new File(pathToResults + File.separatorChar + stringsFileName);
    }

    /**
     * This function distributes lines by files and returns statistics
     */
    public Statistics distribute() {
        if (files.isEmpty()) {
            System.out.println("You have not specified the files for analysis. Enter the correct command one more time");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            String[] arguments = line.split(" ");
            sc.close();
            Main.main(arguments);
            System.exit(0);
        }
        integersFileName = this.namePrefix + "integers.txt";
        floatsFileName = this.namePrefix + "floats.txt";
        stringsFileName = this.namePrefix + "strings.txt";

        StringBuilder integersFileText = new StringBuilder();
        StringBuilder floatsFileText = new StringBuilder();
        StringBuilder stringsFileText = new StringBuilder();

        int countLong = 0;
        int countDouble = 0;
        int countString = 0;
        long maxLong = 0;
        long minLong = 0;
        BigDecimal averageLongValue;
        double maxDouble = 0;
        double minDouble = 0;
        BigDecimal averageDoubleValue;
        int maxLength = 0;
        int minLength = 0;
        long sumLong = 0;
        double sumDouble = 0;

        MultipleReader reader = new MultipleReader(files);

        boolean lineFound = true;
        while (lineFound) {
            lineFound = false;
            for (int i = 0; i < files.size(); i++) {
                String s;
                try {
                    s = reader.getReader(i).readLine();
                    if (s != null) {
                        if (isLong(s)) {
                            long num = Long.parseLong(s);
                            if (countLong == 0) {
                                maxLong = num;
                                minLong = num;
                            }
                            if (num > maxLong) {
                                maxLong = num;
                            } else if (num < minLong) {
                                minLong = num;
                            }

                            sumLong += num;
                            countLong++;
                            integersFileText.append(s).append('\n');
                        } else if (isDouble(s)) {
                            double num = Double.parseDouble(s.replace(',', '.'));
                            if (countDouble == 0) {
                                maxDouble = num;
                                minDouble = num;
                            }
                            if (num > maxDouble) {
                                maxDouble = num;
                            } else if (num < minDouble) {
                                minDouble = num;
                            }

                            sumDouble += num;
                            countDouble++;
                            floatsFileText.append(s).append('\n');
                        } else {
                            int len = s.length();
                            if (countString == 0) {
                                maxLength = len;
                                minLength = len;
                            }
                            if (len > maxLength) {
                                maxLength = len;
                            } else if (len < minLength) {
                                minLength = len;
                            }

                            countString++;
                            stringsFileText.append(s).append('\n');
                        }
                        lineFound = true;
                    }
                } catch (IOException e) {
                    System.out.println("Can't read the line from the file(s). Only those lines that could be read were analyzed.\n");
                }
            }
        }

        reader.close();

        try {
            averageLongValue = BigDecimal.valueOf(sumLong / countLong);
        } catch (ArithmeticException e) {
            averageLongValue = BigDecimal.valueOf(0);
        }

        if (countDouble != 0) {
            averageDoubleValue = BigDecimal.valueOf(sumDouble / countDouble);
        } else {
            averageDoubleValue = BigDecimal.valueOf(0);
        }

        Statistics statistics = new Statistics(countLong, countDouble, countString, sumLong, maxLong, minLong, maxDouble,
                minDouble, sumDouble, maxLength, minLength, averageLongValue, averageDoubleValue);


        integersFile = new File(pathToResults + File.separatorChar + integersFileName);
        floatsFile = new File(pathToResults + File.separatorChar + floatsFileName);
        stringsFile = new File(pathToResults + File.separatorChar + stringsFileName);

        write(integersFile, integersFileText, integersFileName);
        write(floatsFile, floatsFileText, floatsFileName);
        write(stringsFile, stringsFileText, stringsFileName);

        return statistics;
    }
}
