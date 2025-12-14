package project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Data_parser {
    private Path path;

    public Data_parser() {
        this.path = Path.of("Землетрясения.csv").toAbsolutePath();
    }

    public CSVReader createCSVReader() throws FileNotFoundException {
        FileReader fileReader = new FileReader(this.path.toString());
        return new CSVReader(fileReader);
    }

    public List<String[]> readAllData() throws IOException, CsvException {
        try (CSVReader csvReader = createCSVReader()) {
            return csvReader.readAll();
        }
    }

    public static void main(String[] args) throws IOException, CsvException {
        Data_parser dp = new Data_parser();

        List<String[]> allData = dp.readAllData();

        System.out.println(allData.get(1)[0]);
    };
}