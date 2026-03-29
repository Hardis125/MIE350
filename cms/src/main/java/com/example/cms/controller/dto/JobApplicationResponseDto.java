package com.example.cms.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JobApplicationResponseDto {
    private Long id;
    private String company;
    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationDate;
    
    private String status;
    private String notes;
    private Long userId;
    private List<Long> projectIds = new ArrayList<>();
}