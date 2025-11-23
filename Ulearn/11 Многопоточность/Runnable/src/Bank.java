import java.util.Map;
import java.util.HashMap;
import java.util.Random;


public class Bank {
    private Map<String, Account> accounts;
    private final Random random = new Random();

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        synchronized (accounts) {
            Account fromAccount = accounts.get(fromAccountNum);
            Account toAccount = accounts.get(toAccountNum);

            if (fromAccount == null || toAccount == null) {
                System.out.println("Ошибка в номере счета.");
                return;
            }

            if (fromAccount.getMoney() < amount) {
                System.out.println("Недостаточно средств на счете.");
                return;
            }

            if (fromAccount.getIsBlocked() || toAccount.getIsBlocked()) {
                System.out.println("Перевод заблокирован.");
                return;
            }
            
            fromAccount.setMoney(fromAccount.getMoney() - amount);
            System.out.println("На аккаунте списано " + amount + " рублей. Отстаток: " + getBalance(fromAccountNum));
            
            toAccount.setMoney(toAccount.getMoney() + amount);
            System.out.println("Пополнение " + amount + " рублей. Остаток: " + getBalance(fromAccountNum));
        }

        if (amount > 50000) {
            try {
                if (isFraud(fromAccountNum, toAccountNum, amount)) {
                    synchronized (accounts) {
                        Account fromAccount = accounts.get(fromAccountNum);
                        Account toAccount = accounts.get(toAccountNum);
                        fromAccount.changeIsBlocked(true);
                        toAccount.changeIsBlocked(true);

                        System.out.println("Счета заблокированы из-за подозрительной активности до выяснения обстоятельств.");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public long getBalance(String accountNum) {
        Account account = accounts.get(accountNum);
        return account.getMoney();
    }

    // Сумма всех денег на всех счетах банка
    public long getSumAllAccounts() {
        long sum = 0;
        for (Account account : accounts.values()) {
            sum += account.getMoney();
        }

        return sum;
    }

    public void setAccounts(int count){
        accounts = new HashMap<>();
        for (int i = 0; i < count; i++) {
            Account account = new Account();
            account.setAccNumber(String.valueOf(i));
            account.setMoney(200000);
            accounts.put(account.getAccNumber(), account);
        }
    }
}