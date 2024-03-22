package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test class used to test logic in {@link CompensationServiceImpl}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    /**
     * Test used to ensure creation and reading of a new {@link Compensation}.
     */
    @Test
    public void testCreateReadCompensation() {
        // First, build out the Compensation and it's Employee.
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("12345");
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(testEmployee);
        testCompensation.setSalary(100.0f);
        testCompensation.setEffectiveDate(new Date());

        // Next, create the new compensation.
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertNotNull(createdCompensation);
        assertCompensationEquivalence(createdCompensation, testCompensation);

        // Finally, ensure that the compensation was persisted and is now readable.
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, testEmployee.getEmployeeId()).getBody();
        assertNotNull(readCompensation);
        assertCompensationEquivalence(readCompensation, testCompensation);
    }

    /**
     * Simple test to ensure that an error is thrown when trying to get Compensation data for an employee without the data set yet.
     */
    @Test
    public void testErrorHandling() {

        String dummyId = "123";

        try {
            restTemplate.getForEntity(compensationIdUrl, Compensation.class, dummyId).getBody();
        } catch (java.lang.RuntimeException ex) {
            assertTrue(ex.getMessage().contains("Invalid employeeId: " + dummyId));
        }
    }

    /**
     * Helper function used to determine if 2 {@link Compensation}s are equivalent.
     * @param expected - The {@link Compensation} we are verifying.
     * @param actual - The {@link Compensation} we are verifying against.
     */
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
        assertEquals(expected.getSalary(), actual.getSalary(), 0.0f);
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }

    /**
     * Helper function used to determine if 2 {@link Employee}s are equivalent.
     * @param expected - The {@link Employee} we are verifying.
     * @param actual - The {@link Employee} we are verifying against.
     */
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
