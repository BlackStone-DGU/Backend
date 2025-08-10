package com.example.CapsProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import com.example.CapsProject.entity.DailyMission;

public interface DailyMissionRepository extends JpaRepository<DailyMission,Long>{
    List<DailyMission> findAll();
    Optional<DailyMission> findByName(String name);
}
