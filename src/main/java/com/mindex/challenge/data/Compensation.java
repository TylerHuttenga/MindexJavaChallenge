package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;

/**
 * Data class used to house data about {@link Employee} compensation.
 */
public class Compensation {

    private Employee employee;
    private float salary;
    private Date effectiveDate;

    public Compensation() {
    }

    public Employee getEmployee() {
        return employee;
    }

    @JsonProperty("employee")
    public void setEmployee(Employee newEmployee) {
        this.employee = newEmployee;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
