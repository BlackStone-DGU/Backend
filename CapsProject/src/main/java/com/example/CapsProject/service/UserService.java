package com.example.CapsProject.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.CapsProject.repository.*;
import com.example.CapsProject.dto.*;
import com.example.CapsProject.entity.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AffiliationRepository affiliationRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        String affiliationName = dto.getAffiliation().getName();

        Affiliation affiliation = affiliationRepository.findByName(affiliationName)
            .orElseGet(()->affiliationRepository.save(Affiliation.builder().name(affiliationName).build()));

        UserForEntity userForEntity = UserForEntity.builder()
            .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .affiliation(affiliation)
                .build();


        userRepository.save(userForEntity);
    }

    public UserForEntity login(LoginRequestDto dto){
        UserForEntity userForEntity = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(()-> new IllegalArgumentException("존재하지 않은 이메일입니다."));

        if (!passwordEncoder.matches(dto.getPassword(),userForEntity.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return userForEntity;
    }

    public void updateScore(String email, int plusScore){
        UserForEntity userForEntity = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("해당 이메일의 사용자가 없습니다."));
        userForEntity.setScore(userForEntity.getScore()+plusScore);
        userRepository.save(userForEntity);
    }
    public List<UserForEntity> getAllRanking(){
        return userRepository.findAllByOrderByScoreDesc();
    }

    public void signUpAffiliation(String email, String password, String name, String affiliationName) {
        Affiliation affiliation = affiliationRepository.findByName(affiliationName)
            .orElseGet(()->affiliationRepository.save(Affiliation.builder().name(affiliationName).build()));
        if (affiliation == null) {
            // 소속 집단이 없으면 생성
            affiliation = affiliationRepository.save(Affiliation.builder().name(affiliationName).build());
        }
        UserForEntity user = UserForEntity.builder()
                .email(email)
                .password(password)
                .name(name)
                .affiliation(affiliation)
                .build();
        userRepository.save(user);
    }

    //사용자의 랭킹 반환
    public List<UserRankingDto> getAllRankingDto() {
    List<UserForEntity> users = userRepository.findAllByOrderByScoreDesc();
    return users.stream()
            .map(u -> new UserRankingDto(
                u.getEmail(),
                u.getName(),
                u.getScore(),
                u.getAffiliation().getName()  // affiliationName
            ))
            .collect(Collectors.toList());
    }
    //특정 소속에 속한 사용자 반환
    public List<UserForEntity> getUsersByAffiliation(String affiliationName){
        Affiliation affiliation = affiliationRepository.findByName(affiliationName)
            .orElseGet(()->affiliationRepository.save(Affiliation.builder().name(affiliationName).build()));
        return userRepository.findByAffiliation(affiliation);
    }
    //특정 소속의 평균 랭킹을 계산해서 반환 
    public List<AffiliationRankingDto> getAffiliationRanking(){
        List<Object[]> result = userRepository.findAffiliationRanking();
        List<AffiliationRankingDto> ranking = new ArrayList<>();
        for(Object[] row:result){
            Affiliation affiliationEntity = (Affiliation) row[0];
            String affiliationName = affiliationEntity.getName();
            Double avgScore = (Double) row[1];
            ranking.add(new AffiliationRankingDto(affiliationName, avgScore));
        }
        return ranking;
    }
 
}
