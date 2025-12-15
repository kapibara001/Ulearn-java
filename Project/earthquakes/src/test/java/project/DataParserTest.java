package project;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.opencsv.exceptions.CsvException;

public class DataParserTest {

    @Test
    void testConstructor() {
        Data_parser dp = new Data_parser();

        assertNotNull(dp);
    }

    @Test
    void testReadAllData() throws IOException, CsvException {
        Data_parser dp = new Data_parser();
        var data = dp.readAllData();
        
        assertNotNull(data);
        assertTrue(data.size() > 0);

        assertEquals("ID", data.get(0)[0]);
    }
}