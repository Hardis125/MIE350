package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SkillResponseDto {
    private Long id;
    private String name;
    private List<Long> projectIds = new ArrayList<>();
}