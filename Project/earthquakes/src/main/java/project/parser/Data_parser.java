package project.parser;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Data_parser {
    private Path path;

    public Data_parser() {
        this.path = Path.of("Землетрясения.csv").toAbsolutePath();
    }

    public CSVReader createCSVReader() throws IOException {
        return new CSVReader(new FileReader(this.path.toString()));
    }

    public List<String[]> readAllData() throws IOException, CsvException {
        try (CSVReader csvReader = createCSVReader()) {
            List<String[]> data = csvReader.readAll();

            return data;
        }
    }
}