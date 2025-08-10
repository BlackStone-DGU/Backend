package com.example.CapsProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// DTO 예시
public class UserRankingDto {
    private String email;
    private String name;
    private int score;
    private String affiliationName;

    // 생성자, getter, setter


// 서비스에서 변환
public UserRankingDto(String email,String name,int score, String affiliationName){
    this.email = email;
    this.name = name;
    this.score = score;
    this.affiliationName = affiliationName;
}
}
