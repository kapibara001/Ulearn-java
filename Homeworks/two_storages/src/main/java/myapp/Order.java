package myapp;

public class Order {
    private String orderName;
    private boolean isVip;
    private Box boxOrderInfo;
    private Sack sackOrderInfo;

    public Order(String ordername, boolean isVip, Box box) {
        this.orderName = ordername.trim().toLowerCase();
        this.isVip = isVip;
        this.boxOrderInfo = box;
    }

    public Order(String ordername, boolean isVip, Sack sack) {
        this.orderName = ordername.trim().toLowerCase();
        this.isVip = isVip;
        this.sackOrderInfo = sack;
    }

    public String getOrderName() {
        return orderName;
    }

    public boolean getIsVip() {
        return isVip;
    }
}
