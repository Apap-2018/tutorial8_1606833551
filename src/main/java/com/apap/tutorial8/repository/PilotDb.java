package com.apap.tutorial8.repository;

import java.util.Optional;

import com.apap.tutorial8.model.PilotModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PilotDb
 */
@Repository
public interface PilotDb extends JpaRepository<PilotModel, Long> {
    Optional<PilotModel> findByLicenseNumber(String licenseNumber);

    void deleteByLicenseNumber(String licenseNumber);
}