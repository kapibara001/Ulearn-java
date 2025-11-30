package example;

public class Account {
    private long money;
    private String accNumber;
    private boolean isBlocked = false;

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public void changeIsBlocked(boolean new_status) {
        if (this.isBlocked == new_status) {
            return;
        } else {
            this.isBlocked = new_status;
        }
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }
}
