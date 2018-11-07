package com.apap.tutorial8.service;

import java.util.Optional;

import com.apap.tutorial8.model.PilotModel;

/**
 * PilotService
 */
public interface PilotService {
    Optional<PilotModel> getPilotDetailByLicenseNumber(String licenseNumber);

    PilotModel addPilot(PilotModel pilot);

    void deletePilotByLicenseNumber(String licenseNumber);

    Optional<PilotModel> getPilotDetailById(long id);
}