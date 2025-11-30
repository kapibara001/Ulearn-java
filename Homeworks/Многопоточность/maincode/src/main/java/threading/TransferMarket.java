package threading;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransferMarket {
    private List<FootballPlayer> players;
    private Lock marketLock;

    public TransferMarket(List<FootballPlayer> players) {
        this.players = players;
        this.marketLock = new ReentrantLock();
    }

    public FootballPlayer buyPlayer(FootballClub club) {
        marketLock.lock();
        try {
            for (FootballPlayer player : players) {
                if (!player.isSold && player.price <= club.getRemainingBudget()) {
                    player.isSold = true;
                    System.out.println(club.getName() + " покупает " + player.name + " за " + player.price + " млн");
                    return player;
                }
            }
            return null;
        } finally {
            marketLock.unlock();
        }
    }

    public boolean hasAvailablePlayers(FootballClub club) {
        marketLock.lock();
        try {
            for (FootballPlayer player : players) {
                if (!player.isSold && player.price <= club.getRemainingBudget()) {
                    return true;
                }
            }
            return false;
        } finally {
            marketLock.unlock();
        }
    }
}