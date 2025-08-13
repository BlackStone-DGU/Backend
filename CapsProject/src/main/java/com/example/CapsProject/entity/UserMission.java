package com.example.CapsProject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
    name = "user_mission",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_for_entity_id","date","daily_mission_id"})
)
public class UserMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_for_entity_id", nullable = false)
    private UserForEntity userForEntity;

    @ManyToOne
    @JoinColumn(name = "daily_mission_id", nullable = false)
    private DailyMission dailyMission;

    private LocalDate date;

    private boolean success;

}
