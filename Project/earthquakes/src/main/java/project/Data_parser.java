package project;

import com.opencsv.CSVReader;

public class Data_parser {
    private String line;
    private int year;

    public Data_parser(String line, int year) {
        this.line = line;
        this.year = year;
    }

    public void print() {
        System.out.println(line + " " + year);
    }
}
