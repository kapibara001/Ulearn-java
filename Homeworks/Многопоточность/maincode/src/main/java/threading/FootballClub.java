package threading;

public class FootballClub implements Runnable {
    private String name;
    private int budget;
    private TransferMarket market;
    private int remainingBudget;
    private int playersBought;

    public FootballClub(String name, int budget, TransferMarket market) {
        this.name = name;
        this.budget = budget;
        this.market = market;
        this.remainingBudget = budget;
        this.playersBought = 0;
    }

    public String getName() {
        return name;
    }

    public int getRemainingBudget() {
        return remainingBudget;
    }

    public int getPlayersBought() {
        return playersBought;
    }

    public int getRemainingBudgetValue() {
        return remainingBudget;
    }

    @Override
    public void run() {
        System.out.println(name + " начинает покупки с бюджетом " + budget + " млн");
        
        while (remainingBudget > 0) {
            FootballPlayer player = market.buyPlayer(this);
            if (player != null) {
                remainingBudget -= player.price;
                playersBought++;
                System.out.println(name + " осталось бюджета: " + remainingBudget + " млн");
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {
                break;
            }
        }
        
        System.out.println(name + " завершил трансферную кампанию: " + 
                          playersBought + " игроков куплено, осталось бюджета: " + 
                          remainingBudget + " млн");
    }
}