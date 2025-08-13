package com.example.CapsProject.service;

import org.springframework.stereotype.Service;

import com.example.CapsProject.entity.*;
import com.example.CapsProject.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;

    public UserForEntity getByEmailOrThrow(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(()->new IllegalArgumentException("사용자를 찾을수 없습니다."));
    }

}
