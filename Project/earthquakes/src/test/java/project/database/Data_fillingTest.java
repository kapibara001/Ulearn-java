package project.database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.opencsv.exceptions.CsvValidationException;

public class Data_fillingTest {

    @TempDir
    Path tempDir;

    @Test
    public void testFillingTablesDB() throws SQLException, IOException, CsvValidationException {
        // Create a temporary CSV file
        Path csvFile = tempDir.resolve("test.csv");
        String csvContent = "ID,Глубина в метрах,Тип магнитуды,Магнитуда,Штат,Время\n" +
                            "1,1000,md,3.0,California,2023-01-01T12:00:00Z\n" +
                            "2,2000,ml,4.0,Texas,2023-01-02T12:00:00Z\n";
        Files.writeString(csvFile, csvContent);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            // Create tables first
            Create_database.createTables(conn);

            Data_filling df = new Data_filling(csvFile.toString());
            df.fillingTablesDB(conn);

            // Check if data is inserted
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM earthquake");
                assertTrue(rs.next());
                assertEquals(2, rs.getInt("count"));
            }
        }
    }
}