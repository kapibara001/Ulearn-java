public class Program {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

    public abstract class Client {
        protected double balance = 0;

        public double getAmount() {
            return balance;
        }

        public abstract void put(double amount);
        public abstract void take(double amount);
    }

    public class PhysicalPerson extends Client {
        @Override
        public void put(double amount) {
            if (amount <= 0) return;
            balance += amount;
        }

        @Override
        public void take(double amount) {
            if (amount <= 0 || amount > balance) return;
            balance -= amount;
        }
    }

    public class LegalPerson extends PhysicalPerson {
        @Override
        public void take(double amount) {
            double total = amount * 1.01; 
            if (amount <= 0 || total > balance) return;
            balance -= total;
        }
    }

    public class IndividualBusinessman extends PhysicalPerson {
        @Override
        public void put(double amount) {
            if (amount <= 0) return;

            if (amount < 1000) balance += amount * 0.99;   
            else balance += amount * 0.995;               
        }
    }
}
