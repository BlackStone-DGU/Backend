package com.example.CapsProject.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserForEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private double weight;

    private double bmi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation_id",nullable = false)
    private Affiliation affiliation;

    @Column(nullable = false)
    private String studentId;

    private int score = 0;//랭킹을 위한 점수

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    @PrePersist
    @PreUpdate
    public void calculateBmi(){
        if(height > 0 && weight > 0){
            this.bmi = weight/((height/100.0)*(height/100.0));
        }
    }

    @Builder
    public UserForEntity(String email, String password, String name, 
        double height, double weight, Affiliation affiliation, String studentId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.affiliation = affiliation;
        this.studentId = studentId;
    }
}
