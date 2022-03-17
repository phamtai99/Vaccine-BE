package com.project.vaccine.repository;

import com.project.vaccine.entity.VaccinationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationTypeRepository extends JpaRepository<VaccinationType , Integer> {
}
