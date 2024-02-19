package service;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MultipleReader {

    private final ArrayList<BufferedReader> readers;

    public MultipleReader(ArrayList<File> files) {
        readers = initializeReaders(files);
    }

    public BufferedReader getReader(int i) {
        return readers.get(i);
    }

    /**
     * This function returns ArrayList of BufferedReaders which can read the specified files
     */
    private ArrayList<BufferedReader> initializeReaders(ArrayList<File> files) {
        ArrayList<BufferedReader> readers = new ArrayList<>();
        for (File file : files) {
            try {
                readers.add(new BufferedReader(new FileReader(file)));
            } catch (FileNotFoundException e) {
                System.out.println("You set the wrong path to the input file(s).\nPlease correct the command you entered.");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                String[] arguments = line.split(" ");
                sc.close();
                Main.main(arguments);
            }
        }
        return readers;
    }

    /**
     * This method closes all BufferedReaders in MultipleReader
     */
    public void close() {
        for (BufferedReader reader : readers) {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("There were some problems with closing Reader. The program continues to work, but the result may be incorrect.");
            }
        }
    }
}
