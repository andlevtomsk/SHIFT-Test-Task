package service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Params {

    private String pathToResults; //-o
    private String namePrefix; //-p
    private boolean addToExistingFiles; //-a
    private byte staticticsType; //0 - no; 1 - short, 2 - full
    private ArrayList<File> files = new ArrayList<>();

    //statistics
    private int countLong;
    private int countDouble;
    private int countString;
    private long maxLong;
    private long minLong;
    private double maxDouble;
    private double minDouble;
    private int maxLength;
    private int minLength;
    private BigDecimal averageLongValue;
    private BigDecimal averageDoubleValue;

    public BigDecimal getAverageLongValue() {
        return averageLongValue;
    }

    public void setAverageLongValue(BigDecimal averageLongValue) {
        this.averageLongValue = averageLongValue;
    }

    public BigDecimal getAverageDoubleValue() {
        return averageDoubleValue;
    }

    public void setAverageDoubleValue(BigDecimal averageDoubleValue) {
        this.averageDoubleValue = averageDoubleValue;
    }

    public Params(String adress, String namePrefix, Boolean addToExistingFiles, byte staticticsType, ArrayList<File> files) {
        this.pathToResults = adress;
        this.namePrefix = namePrefix;
        this.addToExistingFiles = addToExistingFiles;
        this.staticticsType = staticticsType;
        this.files = files;
    }

    public int getCountLong() {
        return countLong;
    }

    public void setCountLong(int countLong) {
        this.countLong = countLong;
    }

    public int getCountDouble() {
        return countDouble;
    }

    public void setCountDouble(int countDouble) {
        this.countDouble = countDouble;
    }

    public int getCountString() {
        return countString;
    }

    public void setCountString(int countString) {
        this.countString = countString;
    }

    public long getMaxLong() {
        return maxLong;
    }

    public void setMaxLong(long maxLong) {
        this.maxLong = maxLong;
    }

    public long getMinLong() {
        return minLong;
    }

    public void setMinLong(long minLong) {
        this.minLong = minLong;
    }

    public double getMaxDouble() {
        return maxDouble;
    }

    public void setMaxDouble(double maxDouble) {
        this.maxDouble = maxDouble;
    }

    public double getMinDouble() {
        return minDouble;
    }

    public void setMinDouble(double minDouble) {
        this.minDouble = minDouble;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public String getPathToResults() {
        return pathToResults;
    }

    public void setPathToResults(String pathToResults) {
        this.pathToResults = pathToResults;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public Boolean getAddToExistingFiles() {
        return addToExistingFiles;
    }

    public void setAddToExistingFiles(Boolean addToExistingFiles) {
        this.addToExistingFiles = addToExistingFiles;
    }

    public byte getStaticticsType() {
        return staticticsType;
    }

    public void setStaticticsType(byte staticticsType) {
        this.staticticsType = staticticsType;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public static class Builder {

        private String pathToResults = System.getProperty("user.dir"); //-o
        private String namePrefix = ""; //-p
        private Boolean addToExistingFiles = false; //-a
        private byte staticticsType = 0; //0 - no; 1 - short, 2 - full

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
