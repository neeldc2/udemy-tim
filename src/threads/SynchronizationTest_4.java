package threads;

public class SynchronizationTest_4 {
    public static void main(String[] args) {
        Employee tim = new Employee("A", 100D);

        Thread thread1 = new Thread(() -> tim.setSalaryWithLock(200D));
        Thread thread2 = new Thread(() -> tim.setNameWithLock("AA"));

        thread1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        thread2.start();
    }
}
