package com.example.CapsProject.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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

    @Transactional
    public List<UserMission> assignAllMissionsToUser(String email) {
        UserForEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        var today = LocalDate.now();

        var todays = userMissionRepository.findAllByUserForEntityAndDate(user, today);
        if (!todays.isEmpty()) return todays;

        var missions = dailyMissionRepository.findAll();
        var toSave = missions.stream().map(m -> {
            var um = new UserMission();
            um.setUserForEntity(user);
            um.setDailyMission(m);
            um.setDate(today);
            um.setSuccess(false);
            return um;
        }).toList();

        return userMissionRepository.saveAll(toSave);
    }

    // 2) 과제 성공 처리 & 점수 상승
    @Transactional
    public void completeMission(String email) {
        UserForEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
            var today = LocalDate.now();
        UserMission mission = userMissionRepository.findByUserForEntityAndDate(user, LocalDate.now())
                .orElseThrow(() -> new IllegalArgumentException("오늘 미션이 없음"));

        if (!mission.isSuccess()) {
            return;
        }
            mission.setSuccess(true);
            user.setScore(user.getScore() + 10); // 성공시 점수 10점 상승 예시
            userRepository.save(user);
            userMissionRepository.save(mission);

    }

    @Transactional
public void completeAllTodayMissions(String email, String missionName) {
    // 1) 유저 조회
    UserForEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));

    // 2) 오늘 날짜(서울 타임존)
    LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

    // 3) 오늘 처리 대상: 시스템에 등록된 모든 DailyMission
    List<DailyMission> allMissions = dailyMissionRepository.findAll();

    int earned = 0;
    List<UserMission> toSave = new ArrayList<>();

    for (DailyMission mission : allMissions) {
        // 4) 유저의 오늘자 해당 미션 찾아보고, 없으면 새로 생성(초기 success=false)
        UserMission um = userMissionRepository
                .findByUserForEntityAndDateAndDailyMission(user, today, mission)
                .orElseGet(() -> {
                    UserMission created = new UserMission();
                    created.setUserForEntity(user);
                    created.setDailyMission(mission);
                    created.setDate(today);
                    created.setSuccess(false);
                    return created; // 여기서는 즉시 save 하지 않고, 아래에서 일괄 저장
                });

        // 5) 아직 완료가 아니면 완료 처리 + 점수 누적
        if (!um.isSuccess()) {
            um.setSuccess(true);
            earned += 10;          // 미션 1건당 +10 (필요하면 상수/설정으로 분리)
            toSave.add(um);        // 변경/신규 엔티티만 모아서 저장
        }
    }

    // 6) 점수/미션 일괄 저장
    if (earned > 0) {
        user.setScore(user.getScore() + earned);
        userRepository.save(user);
        userMissionRepository.saveAll(toSave);
    }
}
}
