package com.example.CapsProject.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import lombok.RequiredArgsConstructor;


import com.example.CapsProject.repository.DailyMissionRepository;
import com.example.CapsProject.entity.DailyMission;
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DailyMissionRepository dailyMissionRepository;

    @Override
    public void run(String... args) throws Exception{
        if(dailyMissionRepository.count()==0){
            dailyMissionRepository.save(new DailyMission("팔굽혀펴기"));
            dailyMissionRepository.save(new DailyMission("윗몸일으키기"));
            dailyMissionRepository.save(new DailyMission("스쿼트"));
            dailyMissionRepository.save(new DailyMission("달리기"));
        }
    }

}
