package com.Springboot.example.repository;

import com.Springboot.example.model.TriggerInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TriggerInformationRepository extends JpaRepository<TriggerInformation,Long> {
}