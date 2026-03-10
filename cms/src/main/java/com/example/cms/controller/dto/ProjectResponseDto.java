package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProjectResponseDto {
    private Long id;
    private String title;
    private String overview;
    private String result;
    private Long userId;
    private List<Long> skillIds = new ArrayList<>();
}