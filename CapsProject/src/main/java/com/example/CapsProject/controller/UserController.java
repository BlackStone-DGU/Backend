package com.example.CapsProject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.example.CapsProject.service.*;
import com.example.CapsProject.dto.AffiliationRankingDto;
import com.example.CapsProject.dto.LoginRequestDto;
import com.example.CapsProject.dto.SignupRequestDto;
import com.example.CapsProject.dto.UserRankingDto;
import com.example.CapsProject.entity.UserForEntity;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto dto){
        try{userService.signup(dto);

        return ResponseEntity.ok("회원가입 성공");
    } catch (IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok("로그인 성공");
    }

    //점수 갱신
    @PostMapping("/score/update")
    public ResponseEntity<String> updateScore(@RequestParam String email, @RequestParam int plusScore){
        userService.updateScore(email,plusScore);
        return ResponseEntity.ok("점수 갱신 완료");
    }

    //랭킹 리스트 
    @GetMapping("/ranking")
    public ResponseEntity<List<UserRankingDto>> getAllRanking() {
        List<UserRankingDto> ranking = userService.getAllRankingDto();
        return ResponseEntity.ok(ranking);
    }
    
    //소속 분류
    @GetMapping("/users/affiliation")
    public ResponseEntity<List<UserForEntity>> getUsersByAffiliation(@RequestParam String affiliationName){
        List<UserForEntity> users = userService.getUsersByAffiliation(affiliationName);
        return ResponseEntity.ok(users);
    }

    //소속집단 평균 점수
    @GetMapping("/affiliation-ranking")
    public ResponseEntity<List<AffiliationRankingDto>> getAffiliationRanking(){
        return ResponseEntity.ok(userService.getAffiliationRanking());
    }
}

