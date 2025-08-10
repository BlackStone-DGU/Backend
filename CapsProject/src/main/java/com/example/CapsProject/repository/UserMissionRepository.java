package com.example.CapsProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CapsProject.entity.UserMission;
import com.example.CapsProject.entity.UserForEntity;

import java.util.Optional;
import java.time.LocalDate;


public interface UserMissionRepository extends JpaRepository<UserMission,Long> {
    Optional<UserMission> findByUserForEntityAndDate(UserForEntity userForEntity, LocalDate date);
}
