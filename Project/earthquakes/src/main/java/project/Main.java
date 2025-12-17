package project;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

import com.opencsv.exceptions.CsvValidationException;

import project.parser.Create_parse_data;
import project.view_data.Schedule;

public class Main {
    public static void main(String[] args) throws CsvValidationException, SQLException, IOException {
        String path = Path.of("Землетрясения.csv").toAbsolutePath().toString();
        Create_parse_data cpd = new Create_parse_data();
        cpd.checkDateBase(path);
        
        Schedule schedule = new Schedule();
        schedule.createMainGUI();
    }
}