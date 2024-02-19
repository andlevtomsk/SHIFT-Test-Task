package service;

import java.io.File;
import java.util.ArrayList;

public class Params {

    private final String pathToResults;
    private final String namePrefix;
    private final boolean addToExistingFiles; //add - true; create new - false;
    private final byte statisticsType; //0 - no; 1 - short, 2 - full;
    private final ArrayList<File> files;


    public Params(String address, String namePrefix, Boolean addToExistingFiles, byte statisticsType, ArrayList<File> files) {
        this.pathToResults = address;
        this.namePrefix = namePrefix;
        this.addToExistingFiles = addToExistingFiles;
        this.statisticsType = statisticsType;
        this.files = files;
    }

    public String getPathToResults() {
        return pathToResults;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public Boolean getAddToExistingFiles() {
        return addToExistingFiles;
    }

    public byte getStatisticsType() {
        return statisticsType;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public static class Builder {

        private String pathToResults = System.getProperty("user.dir");
        private String namePrefix = "";
        private Boolean addToExistingFiles = false;
        private byte staticticsType = 0;

        private ArrayList<File> files = new ArrayList<>();


        public Builder setPathToResults(String pathToResults) {
            this.pathToResults = pathToResults;
            return this;
        }

        public Builder setNamePrefix(String namePrefix) {
            this.namePrefix = namePrefix;
            return this;
        }

        public Builder setAddToExistingFiles(Boolean addToExistingFiles) {
            this.addToExistingFiles = addToExistingFiles;
            return this;
        }

        public Builder setStatisticsType(byte staticticsType) {
            this.staticticsType = staticticsType;
            return this;
        }

        public Builder addFile(File file) {
            this.files.add(file);
            return this;
        }

        public Params build() {
            return new Params(pathToResults, namePrefix, addToExistingFiles, staticticsType, files);
        }
    }
}
