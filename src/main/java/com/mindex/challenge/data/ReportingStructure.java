package com.mindex.challenge.data;

import com.mindex.challenge.service.EmployeeService;

/**
 * Data class used to house data about {@link Employee} reporting structure.
 */
public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    /**
     * Constructor used to build the reporting structure. By default, the reporting structure must have an {@link Employee}
     * and from there we can calculate and store the total number of reports.
     * @param employee - The {@link Employee} we are building the structure for.
     * @param employeeService - Needed to read any reports' value from the database, to ensure all reports are found.
     */
    public ReportingStructure(Employee employee, EmployeeService employeeService) {
        this.employee = employee;

        // If the direct report list is null or empty, then we simply return 0.
        if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            this.numberOfReports = 0;
        } else {
            int totalNumberOfReports = 0;
            totalNumberOfReports += employee.getDirectReports().size();

            for (Employee report : employee.getDirectReports()) {

                /*
                 First, try to build out the full structure of reports if the reporting list is null.
                 This is an edge case for when data is loaded via json and only the Id is provided.
                 We then use the employee service to read the remaining data.
                */
                if (report.getDirectReports() == null) {
                    report = employeeService.read(report.getEmployeeId());
                }

                // If it is still empty, that means the employee does not have any direct reports.
                if (report.getDirectReports() == null || report.getDirectReports().isEmpty()) {
                    continue;
                } else {
                    // If there are direct reports for the current report, we need to go through them to make sure all reports are accounted for.
                    ReportingStructure subEmployeeReportingStructure = new ReportingStructure(report, employeeService);
                    totalNumberOfReports += subEmployeeReportingStructure.getNumberOfReports();
                } 
            }

            this.numberOfReports = totalNumberOfReports;
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getNumberOfReports() {
        return this.numberOfReports;
    }
}
