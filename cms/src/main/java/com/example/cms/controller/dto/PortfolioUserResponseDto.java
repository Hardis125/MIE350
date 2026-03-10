package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PortfolioUserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Long> projectIds = new ArrayList<>();
    private List<Long> jobApplicationIds = new ArrayList<>();
}
