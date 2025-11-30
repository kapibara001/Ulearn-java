package threading;

public class FootballPlayer {
    public String name;
    public int price;
    public boolean isSold;

    public FootballPlayer(String name, int price) {
        this.name = name;
        this.price = price;
        this.isSold = false;
    }

    @Override
    public String toString() {
        return name + " (" + price + " млн)";
    }
}