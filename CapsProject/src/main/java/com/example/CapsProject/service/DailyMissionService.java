package com.example.CapsProject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.CapsProject.entity.DailyMission;
import com.example.CapsProject.entity.UserMission;
import com.example.CapsProject.entity.UserForEntity;
import com.example.CapsProject.repository.DailyMissionRepository;
import com.example.CapsProject.repository.UserMissionRepository;
import com.example.CapsProject.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyMissionService {
    private final DailyMissionRepository dailyMissionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;

    // 1) 랜덤으로 일일미션 할당
    public UserMission assignRandomMissionToUser(String email) {
        UserForEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        List<DailyMission> missions = dailyMissionRepository.findAll();

        // 랜덤 선택
        DailyMission randomMission = missions.get((int) (Math.random() * missions.size()));

        // 오늘 과제가 이미 있다면 리턴
        LocalDate today = LocalDate.now();
        Optional<UserMission> existing = userMissionRepository.findByUserForEntityAndDate(user, today);
        if (existing.isPresent()) {
            return existing.get();
        }

        UserMission userMission = new UserMission();
        userMission.setUserForEntity(user);
        userMission.setDailyMission(randomMission);
        userMission.setDate(today);
        userMission.setSuccess(false);

        return userMissionRepository.save(userMission);
    }

    // 2) 과제 성공 처리 & 점수 상승
    @Transactional
    public void completeMission(String email) {
        UserForEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        UserMission mission = userMissionRepository.findByUserForEntityAndDate(user, LocalDate.now())
                .orElseThrow(() -> new IllegalArgumentException("오늘 미션이 없음"));

        if (!mission.isSuccess()) {
            mission.setSuccess(true);
            user.setScore(user.getScore() + 10); // 성공시 점수 10점 상승 예시
            userRepository.save(user);
            userMissionRepository.save(mission);
        }
    }
}
