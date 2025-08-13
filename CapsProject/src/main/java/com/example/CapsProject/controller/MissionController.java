package com.example.CapsProject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.CapsProject.entity.UserForEntity;
import com.example.CapsProject.entity.UserMission;
import com.example.CapsProject.service.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MissionController {
    private final UserQueryService userQueryService;
    private final MissionAssignmentService missionAssignmentService;
    private final DailyMissionService dailyMissionService;

    @GetMapping("/today")
    public List<MissionAssignmentService.MissionWithReps> getTodayMissions(
            @RequestParam String email) {

        UserForEntity user = userQueryService.getByEmailOrThrow(email);
        return missionAssignmentService.assignFor(user);
    }

    @PostMapping("/success")
    public ResponseEntity<?> completeAllTodayMissions(@RequestParam String email, @RequestParam String missionName){
        dailyMissionService.completeAllTodayMissions(email, missionName);
        return ResponseEntity.ok("미션성공! 점수 +10");
    }
}
