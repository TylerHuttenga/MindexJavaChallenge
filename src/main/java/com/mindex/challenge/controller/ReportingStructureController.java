package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller used to house calls to read {@link ReportingStructure}s.
 */
@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * Creates a GET endpoint used to get an {@link Employee}'s full reports count.
     * @param id - The {@link Employee}'s Id we are getting the reporting structure for.
     * @return A {@link String} denoting the employee's name, as well as the number of direct reports.
     */
    @GetMapping("/structure/{id}")
    public String read(@PathVariable String id) {
        LOG.debug("Received reporting structure read request for id [{}]", id);

        Employee employee = employeeService.read(id);

        ReportingStructure reportingStructure = new ReportingStructure(employee, employeeService);

        return "Employee " + employee.getFirstName() + " " + employee.getLastName() + " has " + reportingStructure.getNumberOfReports() + " direct reports.";
    }
}
 