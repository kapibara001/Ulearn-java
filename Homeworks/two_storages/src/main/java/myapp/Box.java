package myapp;

public class Box {
    private final double width;
    private final double height;
    private final double length;
    private final boolean isBreakUp;

    public Box(double width, double height, double length, boolean isBreakUp) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.isBreakUp = isBreakUp;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public boolean getIsBreakUp() {
        return isBreakUp;
    }
}
