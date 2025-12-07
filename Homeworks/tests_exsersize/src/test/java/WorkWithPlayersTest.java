import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import example.Player;
import example.WorkWithPlayers;

public class WorkWithPlayersTest {
    String filepath = "src/test/java/testfile.csv";
    WorkWithPlayers workWithPlayers = new WorkWithPlayers(filepath);

    @Test
    void testGetPlayers() {
        List<Player> players = workWithPlayers.getPlayers();
        assertEquals(5, players.size());
    }
    
    @Test
    void testGetCountWithoutAgency() {
        int count = workWithPlayers.getCountWithoutAgency();

        assertEquals(2, count);
    }

    @Test
    void testGetMaxDefenderGoalsCount() {
        int maxGoals = workWithPlayers.getMaxDefenderGoalsCount();
        // DEFENDER: Max(3), Ivan(1), Bob(2) => максимум 3
        assertEquals(10, maxGoals);
    }

    @Test
    void testGetTheExpensiveGermanPlayerPosition() {
        String positionRu = workWithPlayers.getTheExpensiveGermanPlayerPosition();
        assertEquals("Защитник", positionRu);
    }

    @Test
    void testGetPlayersByPosition() {
        Map<String, String> byPosition = workWithPlayers.getPlayersByPosition();

        assertEquals(3, byPosition.size());

        assertEquals("Maxim, Hans", byPosition.get("Защитники"));
        assertEquals("Alexey", byPosition.get("Полузащитники"));
        assertEquals("Ivan, Leo", byPosition.get("Нападающие"));
        assertEquals(null, byPosition.get("Вратари"));
    }

    @Test
    void testGetTeams() {
        Set<String> teams = workWithPlayers.getTeams();

        // Уникальные команды
        assertEquals(5, teams.size());
        assertTrue(teams.contains("Bayern"));
        assertFalse(teams.contains("Zent"));
    }

    @Test
    void testGetTop5TeamsByGoalsCount() {
        Map<String, Integer> top5 = workWithPlayers.getTop5TeamsByGoalsCount();

        assertEquals(5, top5.size());

        var keysInOrder = new ArrayList<>(top5.keySet());
        assertEquals(List.of(
            "Barcelona", "Spartak", "Bayern", "CSKA", "Zenit"  // 4
        ), keysInOrder);

        assertEquals(20, top5.get("Barcelona"));
        assertEquals(null, top5.get("Liverpool"));
    }

    @Test
    void testGetAgencyWithMinPlayersCount() {
        String agency = workWithPlayers.getAgencyWithMinPlayersCount();
        assertEquals("Agency3", agency);
    }

    @Test
    void testGetTheRudestTeam() {
        String team = workWithPlayers.getTheRudestTeam();
        assertEquals("Bayern", team);
    }

    // Тест данных, которые будут использоваться в диаграмме
    @Test
    void testGetTop10TeamsByTransferValue() {
        Map<String, Integer> top10 = workWithPlayers.getTop10TeamsByTransferValue();
        assertEquals(5, top10.size());

        var keysInOrder = new ArrayList<>(top10.keySet());
        assertEquals(List.of(
                "Bayern",
                "Barcelona",
                "Spartak",
                "CSKA",
                "Zenit"
        ), keysInOrder);

        assertEquals(180, top10.get("Barcelona"));
        assertEquals(200, top10.get("Bayern"));
        assertEquals(null, top10.get("Dortmund"));
    }
}

// Для тестов надо в терминал ввести mvn clean test jacoco:report