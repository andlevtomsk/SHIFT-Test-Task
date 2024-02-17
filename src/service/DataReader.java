package service;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataReader {
    private ArrayList<File> files;
    private ArrayList<ArrayList<String>> data;
    private int maxFileLength;

    public DataReader(ArrayList<File> files) {
        this.files = files;
        maxFileLength = 0;
    }

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    public int getMaxFileLength() {
        return maxFileLength;
    }

    /**
     * This method reads and stores data in a special form.
     * A two-dimensional data ArrayList in each line contains elements included in a specific file.
     *
     * @throws IOException, if FileReader can't read the line or close itself or if file is not found.
     */
    //ПРОВЕРИТЬ ВЫШЕ
    public void read() throws IOException {
        data = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            data.add(new ArrayList<>());
            try {
                BufferedReader reader = new BufferedReader(new FileReader(files.get(i)));
                String s;
                while ((s = reader.readLine()) != null) { //обрабатывать ioexception?..
                    data.get(i).add(s);
                }
                if (data.get(i).size() > maxFileLength) {
                    maxFileLength = data.get(i).size();
                }
                reader.close(); //обрабатывать ioexception?..
            } catch (FileNotFoundException e) {
                System.out.println("You set the wrong path to the input file(s).\nPlease correct the command you entered.");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                String[] arguments = line.split(" ");
                Main.main(arguments);
            }
        }
    }
}
