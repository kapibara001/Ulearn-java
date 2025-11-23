public class TransferRun implements Runnable {

    private static Bank bank; //поле банка
    private int count; //количество аккаутов
    private int operationsCount; //количество операций, которые нужно совершить
    private static final java.util.Random random = new java.util.Random();

    public TransferRun(Bank bank, int count, int operationsCount){
        TransferRun.bank = bank;
        this.count = count;
        this.operationsCount = operationsCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < operationsCount; i++) {
            String fromAccountNum = String.valueOf(random.nextInt(count));
            String toAccountNum = String.valueOf(random.nextInt(count));
            
            if (fromAccountNum.equals(toAccountNum)) {
                continue;
            }
        
            long amount = 1000 + random.nextLong() % 100000;
            if (amount <= 0) {
                amount = Math.abs(amount);
            }
            
            bank.transfer(fromAccountNum, toAccountNum, amount);
        }
    }
}