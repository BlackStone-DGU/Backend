package com.example.CapsProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class UserMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserForEntity userForEntity;

    @ManyToOne
    private DailyMission dailyMission;

    private LocalDate date;

    private boolean success;

}
