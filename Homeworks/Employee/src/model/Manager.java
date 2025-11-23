package model;

import java.math.BigDecimal;

public class Manager extends Employee {
    private double department;

    public Manager(String name, BigDecimal salary) {
        super(name, salary);
    }
}
