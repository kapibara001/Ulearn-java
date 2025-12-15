package project;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.view_data.Schedule;

public class ScheduleTest {

    private Schedule schedule;

    @BeforeEach // Выполняется перед каждым @Test
    void setUp() throws Exception {
        schedule = new Schedule();
        // Mock data
        List<String[]> mockData = Arrays.asList(
            new String[]{"ID","Глубина в метрах","Тип магнитуды","Магнитуда","Штат","Время"},
            new String[]{"1","1000","md","2.5","West Virginia","2010-01-01T00:00:00Z"},
            new String[]{"2","2000","md","3.0","West Virginia","2010-02-01T00:00:00Z"},
            new String[]{"3","1500","md","2.0","Ohio","2013-01-01T00:00:00Z"}
        );

        Field dataField = Schedule.class.getDeclaredField("data");

        dataField.setAccessible(true);
        dataField.set(schedule, mockData);
    }

    @Test
    void testGetEarthquakesPerYear() {
        var result = schedule.getEarthquakesPerYear();

        assertEquals(2, result.get(2010));
        assertEquals(1, result.get(2013));
        assertNull(result.get(2011));
    }

    @Test
    void testAvergeMagnutude() {
        double avg = schedule.avergeMagnutude("West Virginia");

        assertEquals(2.75, avg, 0.01);
    }

    @Test
    void testTheDeepestEarthquake() {
        String state = schedule.theDeepestEarthquake(2013);

        assertEquals("ohio", state);
    }

    @Test
    void testTheDeepestEarthquakeNoData() {
        String state = schedule.theDeepestEarthquake(2020);
        
        assertEquals("Нет данных за 2020 год", state);
    }
}