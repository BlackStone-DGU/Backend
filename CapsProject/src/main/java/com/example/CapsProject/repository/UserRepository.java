package com.example.CapsProject.repository;

import java.util.List;
import java.util.Optional;

import com.example.CapsProject.entity.Affiliation;
import com.example.CapsProject.entity.UserForEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserForEntity, Long> {
    boolean existsByEmail(String email);

    Optional<UserForEntity> findByEmail(String email);

    List<UserForEntity> findAllByOrderByScoreDesc();

    List<UserForEntity> findByAffiliation(Affiliation affilliation);

    @Query("SELECT u.affiliation, AVG(u.score) FROM UserForEntity u GROUP BY u.affiliation ORDER BY AVG(u.score) DESC")
    List<Object[]> findAffiliationRanking();
}
