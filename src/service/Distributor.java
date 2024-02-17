package service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Distributor {

    private String pathToResults; //-o
    private String namePrefix; //-p
    private Boolean addToExistingFiles; //-a
    private Params params;
    private DataReader dataReader;

    private String integersFileName;
    private String floatsFileName;
    private String stringsFileName;

    private File integersFile;
    private File floatsFile;
    private File stringsFile;


    public Distributor(DataReader dataReader, Params params, String pathToResults, String namePrefix, Boolean addToExistingFiles) {
        this.dataReader = dataReader;
        this.params = params;
        this.pathToResults = pathToResults;
        this.namePrefix = namePrefix;
        this.addToExistingFiles = addToExistingFiles;
    }

    /**
     * This method checks whether the string is long or not
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
     * This method checks whether the string is double or not
     */
    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * This method writes the text in a file with the specific name
     *
     * @param file, text, name
     * @throws IOException, if file is not found or writer writing or closing can't be done
     */
    // ПРОВЕРИТЬ ЕЩЕ РАЗ ТО, ЧТО ВЫШЕ!!
    private void write(File file, StringBuilder text, String name) throws IOException {
        if (text.isEmpty()) {
            return;
        }
        try {
            FileWriter fw = new FileWriter(file, addToExistingFiles);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(text)); //обрабатывать ioexception?..
            bw.close(); //обрабатывать ioexception?..
        } catch (FileNotFoundException e) {
            System.out.println("You set the wrong path to the output file or wrong name.\nPlease enter correct the path to output file without \"-o\" and then on the next line enter correct prefix without \"-p\".");
            Scanner sc = new Scanner(System.in);
            pathToResults = sc.nextLine();
            String newNamePrefix = sc.nextLine();
            name = name.replace(namePrefix, newNamePrefix);
            updateNamesAndFiles(newNamePrefix);
            write(new File(pathToResults + File.separatorChar + name), text, name);
            }
    }

    /**
     * This method updates wrong files names to new
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
     * This method writes the text in a file with the specific name
     *
     * @throws IOException, as it contains a writer call that throws IOException
     */
    public void distribute() throws IOException {
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

        ArrayList<ArrayList<String>> data = dataReader.getData();
        int maxFileLength = dataReader.getMaxFileLength();

        long sumLong = 0;
        double sumDouble = 0;
        for (int i = 0; i < maxFileLength; i++) {
            for (int j = 0; j < data.size(); j++) {
                if (i >= data.get(j).size()) {
                    break;
                }

                String s = data.get(j).get(i);

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
                    double num = Double.parseDouble(s);
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

            }
        }

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

        params.setCountLong(countLong);
        params.setCountDouble(countDouble);
        params.setCountString(countString);
        params.setMaxLong(maxLong);
        params.setMinLong(minLong);
        params.setMaxDouble(maxDouble);
        params.setMinDouble(minDouble);
        params.setMaxLength(maxLength);
        params.setMinLength(minLength);
        params.setAverageLongValue(averageLongValue);
        params.setAverageDoubleValue(averageDoubleValue);

        integersFile = new File(pathToResults + File.separatorChar + integersFileName);
        floatsFile = new File(pathToResults + File.separatorChar + floatsFileName);
        stringsFile = new File(pathToResults + File.separatorChar + stringsFileName);

        write(integersFile, integersFileText, integersFileName);
        write(floatsFile, floatsFileText, floatsFileName);
        write(stringsFile, stringsFileText, stringsFileName);

    }
}
