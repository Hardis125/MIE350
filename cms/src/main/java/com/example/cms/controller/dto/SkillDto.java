package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SkillDto {

    @NotBlank(message = "Skill name is required")
    @Size(max = 100, message = "Skill name must be under 100 characters")
    private String name;
}

