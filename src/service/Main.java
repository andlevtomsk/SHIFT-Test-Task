package service;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

public class Main {

    /**
     * This method read command that user have entered. All data save in params
     */
    private static Params readConsoleCommand(String[] args) {
        Params.Builder builder = new Params.Builder();

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

        return builder.build();
    }

    /**
     * This method write all statistics that were given by distributor
     */
    private static void writeStatistics(Statistics statistics, Params params) {

        if (params.getStatisticsType() == 1) {
            System.out.printf("%d integers have been added to the integers file\n", statistics.countLong());
            System.out.printf("%d floats have been added to the floats file\n", statistics.countDouble());
            System.out.printf("%d strings have been added to the strings file\n\n", statistics.countString());
        } else if (params.getStatisticsType() == 2) {
            System.out.printf("%d integers have been added to the integers file\n", statistics.countLong());
            if (statistics.countLong() != 0) {
                System.out.printf("The minimal integer number is %d\n", statistics.minLong());
                System.out.printf("The maximal integer number is %d\n", statistics.maxLong());
                System.out.printf("The integers sum is %d\n", statistics.sumLong());
                System.out.printf("The average integer number is %.2f\n\n", statistics.averageLongValue());
            } else {
                System.out.print("Since there are no integers, there are no statistics on the minimum, maximum and average values\n\n");
            }
            System.out.printf("%d floats have been added to the floats file\n", statistics.countDouble());
            if (statistics.countDouble() != 0) {
                System.out.printf("The minimal real number is %s\n", statistics.minDouble());
                System.out.printf("The maximal real number is %s\n", statistics.maxDouble());
                System.out.printf("The floats sum is %.2f\n", statistics.sumDouble());
                System.out.printf("The average real number is %.2f\n\n", statistics.averageDoubleValue());
            } else {
                System.out.print("Since there are no floats, there are no statistics on the minimum, maximum and average values\n\n");
            }
            System.out.printf("%d strings have been added to the strings file\n", statistics.countString());
            if (statistics.countString() != 0) {
                System.out.printf("The maximal length is %d symbols\n", statistics.maxLength());
                System.out.printf("The minimal length is %d symbols\n\n", statistics.minLength());
            } else {
                System.out.print("Since there are no strings, there are no statistics on the minimum, maximum and average values\n\n");
            }
        }
    }

    public static void main(String[] args) {

        Params params = readConsoleCommand(args);

        Distributor distributor = new Distributor(params.getFiles(), params.getPathToResults(), params.getNamePrefix(), params.getAddToExistingFiles());
        Statistics statistics = distributor.distribute();

        writeStatistics(statistics, params);

    }
}
