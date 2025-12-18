package project.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_database {
    public static void createTables(Connection conn) throws SQLException {
        String createState ="""
            CREATE TABLE IF NOT EXISTS state (
                state_id SMALLINT PRIMARY KEY,
                state_name VARCHAR(100) NOT NULL  
            )
            """;

        String createMagnitude = """
            CREATE TABLE IF NOT EXISTS magnitude (
                magnitude_type_id SMALLINT PRIMARY KEY,
                magnitude_type_name VARCHAR(5) NOT NULL
            )    
            """;

        String createEarthquake = """
            CREATE TABLE IF NOT EXISTS earthquake (
                id_earthquake INT PRIMARY KEY,
                noun INT NOT NULL,
                magnitude_type_id SMALLINT NOT NULL,
                magnitude REAL NOT NULL,
                state_id SMALLINT NOT NULL,
                time VARCHAR(10) NOT NULL,
                CONSTRAINT FK_earth_magn 
                    FOREIGN KEY (magnitude_type_id) 
                    REFERENCES magnitude(magnitude_type_id),
                CONSTRAINT FK_earth_state 
                    FOREIGN KEY (state_id) 
                    REFERENCES state(state_id)
            )    
            """;

        try (Statement st = conn.createStatement()) {
            st.execute(createState);
            st.execute(createMagnitude);
            st.execute(createEarthquake);
        }
    }
}
