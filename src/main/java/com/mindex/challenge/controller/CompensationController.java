package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller used to house calls to create and read {@link Compensation}s.
 */
@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /**
     * Creates a GET endpoint used to read data for existing {@link Compensation}s.
     * @param id - The {@link String} Employee Id we are getting the compensation for.
     * @return The found {@link Compensation} object if found, or throw an error if the given Id was not found.
     */
    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for employee id [{}]", id);

        return compensationService.read(id);
    }

    /**
     * Creates a POST endpoint used to create and store a new {@link Compensation} object.
     * @param compensation - The {@link Compensation} we are creating.
     * @return The successfully created {@link Compensation} object.
     */
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for employee id [{}]", compensation.getEmployee().getEmployeeId());

        return compensationService.create(compensation);
    }

}
