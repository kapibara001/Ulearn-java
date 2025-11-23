package model;
import java.math.BigDecimal;

public class Employee {
    private String name;
    private int id;
    private BigDecimal salary;
    static int count;

    public Employee(String name, int salary) {
        count++;
        this.name = name;
        this.salary = salary;
        this.id = count;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}