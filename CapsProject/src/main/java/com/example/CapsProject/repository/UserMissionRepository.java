package com.example.CapsProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CapsProject.entity.UserMission;
import com.example.CapsProject.entity.DailyMission;
import com.example.CapsProject.entity.UserForEntity;

import java.util.Optional;
import java.util.List;
import java.time.LocalDate;


public interface UserMissionRepository extends JpaRepository<UserMission,Long> {
    Optional<UserMission> findByUserForEntityAndDate(
            UserForEntity userForEntity, LocalDate date);

    // 오늘 날짜의 사용자-미션 전체 목록 (모두 띄우기용)
    List<UserMission> findAllByUserForEntityAndDate(
            UserForEntity user, LocalDate date);

    // 특정 미션 존재 여부(유니크 보조용)
    Optional<UserMission> findByUserForEntityAndDateAndDailyMission(
            UserForEntity user, LocalDate date, DailyMission dailyMission);

    
    
}
