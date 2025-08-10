package com.example.CapsProject.repository;


import com.example.CapsProject.entity.Affiliation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation,Long> {
    Optional<Affiliation> findByName(String name);

}
