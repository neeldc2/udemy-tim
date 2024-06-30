package threads;

import java.text.MessageFormat;

// Udemy Tim
public class SynchronizationTest {
    public static void main(String[] args) {
        BankAccount companyAccount = new BankAccount(10000);

        Thread thread1 = new Thread(() -> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(() -> companyAccount.deposit(5000));
        Thread thread3 = new Thread(() -> companyAccount.withdraw(2500));

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        System.out.println("Final Balance : " + companyAccount.getBalance());
    }
}

class BankAccount {

    private double balance;
    //private volatile double balance; // This does not work if synchronized is missing.

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        double originalBalance = balance;
        balance += amount;
        System.out.println(
                MessageFormat.format("""
                        Amount is deposited {2}
                        Original Balance is {0} and new Balance is {1}
                        """, originalBalance, balance, amount));
    }

    public void depositWithSynchronizedBlock(double amount) {
        try {
            System.out.println("Talking to a bank employee");
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // synchronize only the critical section
        // Here, talking to bank employees happen in parallel for all threads.
        synchronized (this) {
            double originalBalance = balance;
            balance += amount;
            System.out.println(
                    MessageFormat.format("""
                            Amount is deposited {2}
                            Original Balance is {0} and new Balance is {1}
                            """, originalBalance, balance, amount));
        }
    }

    public synchronized void withdraw(double amount) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (amount <= balance) {
            double originalBalance = balance;
            balance -= amount;
            System.out.println(
                    MessageFormat.format("""
                            Amount is withdrawn {2}
                            Original Balance is {0} and new Balance is {1}
                            """, originalBalance, balance, amount));
        } else {
            System.out.println(
                    MessageFormat.format("""
                            Amount {2} cannot be withdrawn.
                            Balance is {1}
                            """, balance));
        }
    }
}
