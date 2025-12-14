package project;

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
        try (FileReader fileReader = new FileReader(this.path.toString())) {
            return new CSVReader(fileReader);
        } catch (IOException e) {
            throw new IOException("Error creating CSVReader", e);
        }
    }

    public List<String[]> readAllData() throws Exception {
        try (CSVReader csvReader = createCSVReader()) {
            return csvReader.readAll();
        } catch (IOException | CsvException e) {
            throw new Exception("Error reading CSV file", e);
        }
    }

//     public static void main(String[] args) throws IOException, CsvException {
//         Data_parser dp = new Data_parser();

//         List<String[]> allData = dp.readAllData();

//         System.out.println(allData.get(1)[0]);
//     };
}