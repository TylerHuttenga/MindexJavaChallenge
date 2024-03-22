package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

/**
 * Simple service class for handling creation and reading of {@link Compensation}s.
 */
public interface CompensationService {

    /**
     * Used to create and persist a new {@link Compensation}.
     * @param compensation - The new {@link Compensation} to be persisted.
     * @return The newly persisted compensation object.
     */
    Compensation create(Compensation compensation);

    /**
     * Used to return a {@link Compensation} based off the provided employee id {@link String}.
     * @param employeeId - The {@link String} employee id we wish to get compensation data for.
     * @return The related {@link Compensation} data for the given employee id, if found. Throws a {@link RuntimeException} otherwise.
     */
    Compensation read(String employeeId);
}
