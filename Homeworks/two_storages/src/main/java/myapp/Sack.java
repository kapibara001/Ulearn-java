package myapp;

public class Sack {
    private final double weight;
    public final CargoType type;
    enum CargoType {
        SAND, CEMENT, PLASTER // Песок цемент штукатурка
    }

    public Sack(double weight, CargoType type) {
        this.weight = weight;
        this.type = type;
    }

    public double getWeigth() {
        return weight;
    }

    public CargoType getType() {
        return type;
    }

    @Override
    public String toString() {
        return weight + " " + type;
    }
}
