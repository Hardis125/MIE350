package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ProjectDto {

    @NotBlank(message = "Project title cannot be empty")
    @Size(max = 100, message = "Title must be under 100 characters")
    private String title;

    @NotBlank(message = "Project overview is required")
    @Size(max = 1000, message = "Overview cannot exceed 1000 characters")
    private String overview;

    private String result;

    @NotNull(message = "A project must belong to a user")
    private Long userId;

    private List<Long> skillIds;
}
