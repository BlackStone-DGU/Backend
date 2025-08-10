package com.example.CapsProject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.CapsProject.service.DailyMissionService;
import com.example.CapsProject.entity.UserMission;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MissionController {
    private final DailyMissionService dailyMissionService;

    @GetMapping("/today")
    public ResponseEntity<?> getTodayMission(@RequestParam String email){
        UserMission mission = dailyMissionService.assignRandomMissionToUser(email);
        return ResponseEntity.ok(mission.getDailyMission().getName());
    }

    @PostMapping("/success")
    public ResponseEntity<?> completeMission(@RequestParam String email){
        dailyMissionService.completeMission(email);
        return ResponseEntity.ok("미션성공! 점수 +10");
    }
}
