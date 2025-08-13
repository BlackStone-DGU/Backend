package com.example.CapsProject.service;

import com.example.CapsProject.entity.*;
import com.example.CapsProject.repository.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionAssignmentService {

    private final DailyMissionRepository dailyMissionRepository;

    public double decideFactorByBmi(double bmi){
         if (bmi < 18.5) return 0.8;
        if (bmi < 25.0) return 1.0;      // 18.5–24.9
        if (bmi < 30.0) return 0.9;      // 25.0–29.9
        return 0.7;                      // ≥30.0
    }

     public List<MissionWithReps> assignFor(UserForEntity user) {
        double bmi = user.getBmi(); // 저장형. 저장 안 한다면 height/weight로 계산
        double factor = decideFactorByBmi(bmi);

        List<DailyMission> missions = dailyMissionRepository.findAll();
        return missions.stream()
                .map(m -> new MissionWithReps(m.getName(),
                        Math.max(1, (int)Math.round(m.getBaseReps() * factor))))
                .toList();
    }

    // 응답 DTO
    public record MissionWithReps(String name, int reps) {}
}
