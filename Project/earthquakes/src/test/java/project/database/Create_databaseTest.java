package project.database;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_databaseTest {

    @Test
    public void testCreateTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            Create_database.createTables(conn);

            // Check if tables exist
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name IN ('state', 'magnitude', 'earthquake')");
                int count = 0;
                while (rs.next()) {
                    count++;
                }
                assertEquals(3, count);
            }
        }
    }
}