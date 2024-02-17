package service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;

public class Main {

    private static Params.Builder builder;
    private static Params params;
    private static DataReader dataReader;
    private static Distributor distributor;


    /**
     * This method read command that user have entered. All data save in params
     */
    private static void readConsoleCommand(String[] args) {
        Iterator<String> iterator = Arrays.stream(args).iterator();
        while (iterator.hasNext()) {
            String a = iterator.next();
            if (a.contentEquals("-o")) {
                String s = iterator.next();
                builder.setPathToResults(s);
            } else if (a.contentEquals("-p")) {
                builder.setNamePrefix(iterator.next());
            } else if (a.contentEquals("-a")) {
                builder.setAddToExistingFiles(true);
            } else if (a.contentEquals("-s")) {
                builder.setStatisticsType((byte) 1);
            } else if (a.contentEquals("-f")) {
                builder.setStatisticsType((byte) 2);
            } else {
                builder.addFile(new File(a));
            }
        }
    }

    /**
     * This method write all statistics that were given by distributor
     */
    private static void writeStatistics() {

        DecimalFormat formatter = new DecimalFormat("0.00");

        if (params.getStaticticsType() == 1) {
            System.out.println("Integers file contains " + params.getCountLong() + " numbers\n" +
                    "Floats file contains " + params.getCountDouble() + " numbers\n" +
                    "Strings file contains " + params.getCountString() + " lines");
        } else if (params.getStaticticsType() == 2) {
            System.out.println("Integers file contains " + params.getCountLong() + " numbers");
            if (params.getCountLong() != 0) {
                System.out.println("The minimal integer number is " + params.getMinLong() + '\n' +
                        "The maximal integer number is " + params.getMaxLong() + '\n' +
                        "The average integer value is " + formatter.format(params.getAverageLongValue()) + "\n");
            } else {
                System.out.println("Since there are no integers, there are no statistics on the minimum, maximum and average values\n");
            }

            System.out.println("Floats file contains " + params.getCountDouble() + " numbers");
            if (params.getCountDouble() != 0) {
                System.out.println("The minimal real number is " + params.getMinDouble() + '\n' +
                        "The maximal real number is " + params.getMaxDouble() + '\n' +
                        "The average real value is " + formatter.format(params.getAverageDoubleValue()) + "\n");
            } else {
                System.out.println("Since there are no floats, there are no statistics on the minimum, maximum and average values\n");
            }

            System.out.println("Strings file contains " + params.getCountString() + " lines");
            if (params.getCountString() != 0) {
                System.out.println("The maximal length is " + params.getMaxLength() + " symbols\n" +
                        "The minimal length is " + params.getMinLength() + " symbols");
            } else {
                System.out.println("Since there are no strings, there are no statistics on the minimum, maximum and average values.");
            }
        }
    }

    public static void main(String[] args) throws IOException {

        builder = new Params.Builder();

        readConsoleCommand(args);

        params = builder.build();

        dataReader = new DataReader(params.getFiles());
        dataReader.read();

        distributor = new Distributor(dataReader, params, params.getPathToResults(), params.getNamePrefix(), params.getAddToExistingFiles());
        distributor.distribute();

        writeStatistics();

    }
}
