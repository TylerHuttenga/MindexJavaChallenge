package com.mindex.challenge.data;

import com.mindex.challenge.service.EmployeeService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class used to test functionality housed in the ReportingStructure data object.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureTest {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Test used to verify that 0 direct reports are returned for both empty and null lists.
     */
    @Test
    public void testZeroDirectReports() {
        // First, create the employee.
        Employee testEmployee = createEmployee("Test_First", "Test_Last", "Testing", "I guess QA?");

        // Next, create the reporting structure, without creating a direct reports list.
        ReportingStructure reportingStructureNullList = new ReportingStructure(testEmployee, employeeService);
        assertNotNull(reportingStructureNullList);

        //Since no direct reports list was set, the number of reports is 0.
        assertEquals(reportingStructureNullList.getNumberOfReports(), 0);

        // Then, create an empty direct reports list.
        testEmployee.setDirectReports(new ArrayList<Employee>());

        // Finally, set the Employee's direct results list to be empty instead of null. the result should still be 0 direct reports.
        ReportingStructure reportingStructureEmptyList = new ReportingStructure(testEmployee, employeeService);
        assertNotNull(reportingStructureEmptyList);
        assertEquals(reportingStructureEmptyList.getNumberOfReports(), 0);
    }

    /**
     * Test used to verify valid employee reporting structures.
     */
    @Test
    public void testValidDirectReports() {
        // First, create the employees.
        Employee testEmployeeBoss = createEmployee("Boss_First", "Boss_Last", "Bossing", "Boss");
        Employee testEmployeeWorker1 = createEmployee("Worker_1_First", "Worker_2_Last", "Working", "Worker 1");
        Employee testEmployeeWorker2 = createEmployee("Worker_2_First", "Worker_2_Last", "Working", "Worker 2");
        Employee testEmployeeWorker3 = createEmployee("Worker_3_First", "Worker_3_Last", "Working", "Worker 3");
        Employee testEmployeeWorker4 = createEmployee("Worker_4_First", "Worker_4_Last", "Working", "Worker 4");
        Employee testEmployeeWorker5 = createEmployee("Worker_5_First", "Worker_5_Last", "Working", "Worker 5");
        Employee testEmployeeWorker6 = createEmployee("Worker_6_First", "Worker_6_Last", "Working", "Worker 6");

        /* Next, set up the reporting structure. The final structure will be of the form:
                                               testEmployeeBoss
                                              /                \
                                  testEmployeeWorker1         testEmployeeWorker2
                                 /                  \                            \
                       testEmployeeWorker3  testEmployeeWorker4            testEmployeeWorker5
                      /
           testEmployeeWorker6
        */

        testEmployeeBoss.setDirectReports(Arrays.asList(testEmployeeWorker1, testEmployeeWorker2));
        testEmployeeWorker1.setDirectReports(Arrays.asList(testEmployeeWorker3, testEmployeeWorker4));
        testEmployeeWorker2.setDirectReports(Arrays.asList(testEmployeeWorker5));
        testEmployeeWorker3.setDirectReports(Arrays.asList(testEmployeeWorker6));
        testEmployeeWorker4.setDirectReports(new ArrayList<Employee>());
        testEmployeeWorker5.setDirectReports(new ArrayList<Employee>());
        testEmployeeWorker6.setDirectReports(new ArrayList<Employee>());

        // Finally, verify each employee's reporting structure number is correct

        // Since all employees are direct reports to the boss, the total should be 6.
        ReportingStructure reportingStructureBoss = new ReportingStructure(testEmployeeBoss, employeeService);
        assertNotNull(reportingStructureBoss);
        assertEquals(reportingStructureBoss.getNumberOfReports(), 6);

        // Worker 1 has 3 direct reports, testEmployeeWorker3, testEmployeeWorker4. and testEmployeeWorker6.
        ReportingStructure reportingStructureWorker1 = new ReportingStructure(testEmployeeWorker1, employeeService);
        assertNotNull(reportingStructureWorker1);
        assertEquals(reportingStructureWorker1.getNumberOfReports(), 3);

        // Worker 2 has 1 direct report, testEmployeeWorker5.
        ReportingStructure reportingStructureWorker2 = new ReportingStructure(testEmployeeWorker2, employeeService);
        assertNotNull(reportingStructureWorker2);
        assertEquals(reportingStructureWorker2.getNumberOfReports(), 1);

        // Worker 3 has 1 direct report, testEmployeeWorker6.
        ReportingStructure reportingStructureWorker3 = new ReportingStructure(testEmployeeWorker3, employeeService);
        assertNotNull(reportingStructureWorker3);
        assertEquals(reportingStructureWorker3.getNumberOfReports(), 1);

        // Worker 4, Worker 5, and Worker 6 all have no direct reports.
        ReportingStructure reportingStructureWorker4 = new ReportingStructure(testEmployeeWorker4, employeeService);
        assertNotNull(reportingStructureWorker4);
        assertEquals(reportingStructureWorker4.getNumberOfReports(), 0);

        ReportingStructure reportingStructureWorker5 = new ReportingStructure(testEmployeeWorker5, employeeService);
        assertNotNull(reportingStructureWorker5);
        assertEquals(reportingStructureWorker5.getNumberOfReports(), 0);

        ReportingStructure reportingStructureWorker6 = new ReportingStructure(testEmployeeWorker6, employeeService);
        assertNotNull(reportingStructureWorker6);
        assertEquals(reportingStructureWorker6.getNumberOfReports(), 0);
    }

    /**
     * Helper method used to create the Employee objects to test on.
     * @param firstName - The Employee's first name.
     * @param lastName - The Employee's last name.
     * @param department - The Employee's department.
     * @param position - the Employee's position.
     * @return The newly created Employee object.
     */
    private Employee createEmployee(String firstName, String lastName, String department, String position) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDepartment(department);
        employee.setPosition(position);
        employee.setEmployeeId(UUID.randomUUID().toString());

        return employee;
    }
}
