package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JobApplicationResponseDto {
    private Long id;
    private String company;
    private String role;
    private String dates;
    private String status;
    private String notes;
    private Long userId;
    private List<Long> projectIds = new ArrayList<>();
}