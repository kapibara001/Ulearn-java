package project;

// Test commit for CI trigger
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

import com.opencsv.exceptions.CsvValidationException;

import project.parser.Create_parse_data;
import project.view_data.Schedule;

public class Main {
    public static void main(String[] args) throws CsvValidationException, SQLException, IOException {
        Path path = Path.of("Землетрясения.csv").toAbsolutePath();
        Create_parse_data cpd = new Create_parse_data();
        cpd.checkDateBase(path);
        
        Schedule schedule = new Schedule();
        schedule.createMainGUI();
    }
}