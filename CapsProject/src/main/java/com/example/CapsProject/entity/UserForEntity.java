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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliation_id",nullable = false)
    private Affiliation affiliation;

    private int score = 0;//랭킹을 위한 점수

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    @Builder
    public UserForEntity(String email, String password, String name, Affiliation affiliation) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.affiliation = affiliation;
    }
}
