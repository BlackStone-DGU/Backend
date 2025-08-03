package com.example.CapsProject.repository;


import com.example.CapsProject.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation,Long> {
    Affiliation findByName(String name);

}
