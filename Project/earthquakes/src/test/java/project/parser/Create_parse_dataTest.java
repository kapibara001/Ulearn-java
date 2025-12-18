package project.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import com.opencsv.exceptions.CsvValidationException;

public class Create_parse_dataTest {

    @TempDir
    Path tempDir;

    @Test
    public void testCheckDateBase() throws SQLException, CsvValidationException, IOException {
        // Create a temporary CSV file
        Path csvFile = tempDir.resolve("Землетрясения.csv");
        String csvContent = "ID,Глубина в метрах,Тип магнитуды,Магнитуда,Штат,Время\n" +
                            "1,1000,md,3.0,California,2023-01-01T12:00:00Z\n";
        Files.writeString(csvFile, csvContent);

        String dbUrl = "jdbc:sqlite:" + tempDir.resolve("test.db").toString();

        Create_parse_data cpd = new Create_parse_data();
        cpd.checkDateBase(csvFile);
    }
}