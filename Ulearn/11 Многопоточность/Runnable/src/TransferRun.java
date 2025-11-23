public class TransferRun implements Runnable {

    private static Bank bank; //поле банка
    private int count; //количество аккаутов
    private int operationsCount; //количество операций, которые нужно совершить

    public TransferRun(Bank bank, int count, int operationsCount){
        TransferRun.bank = bank;
        this.count = count;
        this.operationsCount = operationsCount;
    }

    @Override
    public void run() {
        //TODO реализуйте здесь <operationsCount> случайных операций
    }
}


public interface Runnable {

    
}