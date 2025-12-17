package project.database;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class Data_filling {
    private static String csv_file;

    public Data_filling(String csv_file) {
        Data_filling.csv_file = csv_file;
    }

    public void fillingTablesDB(Connection conn) throws SQLException, IOException, CsvValidationException {
        Map<String, Short> states = new HashMap<>();
        Map<String, Short> magnitudeType = new HashMap<>();

        String insertStateSQL = 
            "INSERT OR IGNORE INTO state (state_id, state_name) VALUES (?, ?)";
        String insertMagnitudesSQL = 
            "INSERT OR IGNORE INTO magnitude (magnitude_type_id, magnitude_type_name) VALUES (?, ?)";
        String insertEarthquakes = 
            """
            INSERT INTO earthquake (id_earthquake, noun, magnitude_type_id, magnitude, state_id, time)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement psState = conn.prepareStatement(insertStateSQL);
             PreparedStatement psMag = conn.prepareStatement(insertMagnitudesSQL);
             PreparedStatement psEq = conn.prepareStatement(insertEarthquakes);
             FileReader fr = new FileReader(csv_file, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReaderBuilder(fr)
                .withSkipLines(1)
                .build()
        ) {
            String[] row;
            int eqID = 1;

            while ((row = reader.readNext()) != null) {
                String noun = row[1];
                String magnitude_type_name = row[2];
                String magnitude_level = row[3];
                String stateName = row[4];
                String timeStr = row[5];

                int noun_metres = Integer.parseInt(noun);
                float magnitude = Float.parseFloat(magnitude_level);
                Timestamp time = Timestamp.valueOf(timeStr.replace("T", " ").replace("Z", ""));

                Short stateID = states.get(stateName);
                if (stateID == null) {
                    stateID = (short) (states.size() + 1);
                    states.put(stateName, stateID);
                    psState.setShort(1, stateID);
                    psState.setString(2, stateName);
                    psState.executeUpdate();
                }

                Short magTypeID = magnitudeType.get(magnitude_type_name);
                if (magTypeID == null) {
                    magTypeID = (short) (magnitudeType.size() + 1);
                    magnitudeType.put(magnitude_type_name, magTypeID);
                    psMag.setShort(1, magTypeID);
                    psMag.setString(2, magnitude_type_name);
                    psMag.executeUpdate();
                }
                
                psEq.setInt(1, eqID++);
                psEq.setInt(2, noun_metres);
                psEq.setShort(3, magTypeID);
                psEq.setFloat(4, magnitude);
                psEq.setShort(5, stateID);
                psEq.setTimestamp(6, time);
                psEq.executeUpdate();
            }
        }
    }
}