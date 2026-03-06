package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class JobApplicationDto {

    @NotBlank(message = "Company name is required")
    private String company;

    @NotBlank(message = "Role is required")
    private String role;

    private String dates;
    private String status;
    private String notes;

    @NotNull(message = "An application must be tied to a user")
    private Long userId;

    private List<Long> projectIds;
}