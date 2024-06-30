package threads;

// This is to show the disadvantage of synchronized block. SynchronizationTest_4 shows how to overcome it.
public class SynchronizationTest_3 {
    public static void main(String[] args) {
        Employee tim = new Employee("A", 100D);

        Thread thread1 = new Thread(() -> tim.setSalary(200D));
        Thread thread2 = new Thread(() -> tim.setName("AA"));

        thread1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        thread2.start();
    }
}

class Employee {
    private String name;
    private Double salary;

    public Employee(String name,Double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void setSalary(Double newSalary) {
        System.out.println("Old Salary is " + this.salary);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }

        synchronized (this) {
            this.salary = newSalary;
        }

        System.out.println("New Salary is " + this.salary);
    }

    public void setName(String newName) {
        System.out.println("Old name is " + this.name);

        synchronized (this) {
            this.name = newName;

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {

            }
        }

        System.out.println("New name is " + this.name);
    }

    public void setSalaryWithLock(Double newSalary) {
        System.out.println("Old Salary is " + this.salary);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }

        synchronized (this.salary) {
            this.salary = newSalary;
        }

        System.out.println("New Salary is " + this.salary);
    }

    public void setNameWithLock(String newName) {
        System.out.println("Old name is " + this.name);

        synchronized (this.name) {
            this.name = newName;

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {

            }
        }

        System.out.println("New name is " + this.name);
    }
}
