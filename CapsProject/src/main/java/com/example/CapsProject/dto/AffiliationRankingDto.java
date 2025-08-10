package com.example.CapsProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AffiliationRankingDto {
    private String affiliation;
    private Double avgScore;
}
