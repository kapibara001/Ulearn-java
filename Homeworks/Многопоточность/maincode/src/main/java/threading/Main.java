package threading;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Создаем список игроков
        List<FootballPlayer> players = Arrays.asList(
            new FootballPlayer("Лионель Месси", 50),
            new FootballPlayer("Криштиану Роналду", 45),
            new FootballPlayer("Килиан Мбаппе", 180),
            new FootballPlayer("Эрлинг Холланд", 170),
            new FootballPlayer("Винсиус Жуниор", 120),
            new FootballPlayer("Кевин Де Брейне", 80),
            new FootballPlayer("Мохаммед Салах", 90),
            new FootballPlayer("Роберт Левандовски", 60),
            new FootballPlayer("Неймар", 70),
            new FootballPlayer("Харри Кейн", 100)
        );

        TransferMarket market = new TransferMarket(players);

        List<FootballClub> clubs = Arrays.asList(
            new FootballClub("Реал Мадрид", 300, market),
            new FootballClub("Барселона", 200, market),
            new FootballClub("Манчестер Сити", 400, market),
            new FootballClub("ПСЖ", 250, market),
            new FootballClub("Бавария", 180, market),
            new FootballClub("Челси", 150, market)
        );

        ExecutorService executor = Executors.newFixedThreadPool(clubs.size());

        System.out.println("НАЧАЛО ТРАНСФЕРНОГО ОКНА");
        System.out.println("Доступные игроки:");
        for (FootballPlayer player : players) {
            System.out.println("  - " + player);
        }
        System.out.println();

        for (FootballClub club : clubs) {
            executor.execute(club);
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("\nТРАНСФЕРНОЕ ОКНО ЗАКРЫТО");
        
        printFinalStatistics(players, clubs);
    }

    private static void printFinalStatistics(List<FootballPlayer> players, List<FootballClub> clubs) {
        System.out.println("\nИТОГОВАЯ СТАТИСТИКА");
        
        System.out.println("\nСтатус игроков:");
        for (FootballPlayer player : players) {
            String status = player.isSold ? "ПРОДАН" : "НЕ ПРОДАН";
            System.out.println("  - " + player.name + ": " + status);
        }
        
        System.out.println("\nРезультаты клубов:");
        for (FootballClub club : clubs) {
            System.out.println("  - " + club.getName() + 
                             ": игроков куплено - " + club.getPlayersBought() + 
                             ", остаток бюджета - " + club.getRemainingBudgetValue() + " млн");
        }
    }
}