import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;

public class Сhampionship {
    public static void main(String[] args) throws Exception {
        WorkWithPlayers workPlayers = new WorkWithPlayers("src/fakePlayers.csv");

        // 1
        System.out.println("Игроков без агенства: " + workPlayers.getCountWithoutAgency() + "\n");

        // 2
        System.out.println("Максимальное количество голов, забитых защитником: " + workPlayers.getMaxDefenderGoalsCount() + "\n");

        // 3
        System.out.println("Позиция самого дорогого немецкого игрока: " + workPlayers.getTheExpensiveGermanPlayerPosition() + "\n");

        // 4
        System.out.println("Игроки по позициям на поле: " + workPlayers.getPlayersByPosition() + "\n");

        // 5
        System.out.println("Все команды, участвующие в чемпионате: ");
        for (String team : workPlayers.getTeams()) {
            System.out.println(team);
        }
        System.out.println("");

        // 6
        System.out.println("Топ 5 команд по голам: " + workPlayers.getTop5TeamsByGoalsCount());
        System.out.println("");

        // 7
        System.out.println("Агенство с наименьшей суммой игроков: " + workPlayers.getAgencyWithMinPlayersCount());
        System.out.println("");

        System.out.println("Команда с наибольшим количеством красных карточек у игрока: " + workPlayers.getTheRudestTeam()); 

        // Задание 2
        BarChart.showTop10TeamsChart(workPlayers);
    }
}