package com.example.cms.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class JobApplicationDto {

    @NotBlank(message = "Company name is required")
    @Size(max = 150, message = "Company name must be under 150 characters")
    private String company;

    @NotBlank(message = "Role is required")
    @Size(max = 150, message = "Role must be under 150 characters")
    private String role;

    @NotNull(message = "Application date is required")
    @PastOrPresent(message = "Application date cannot be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationDate;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "Applied|Interview|Rejected|Offer",
            message = "Status must be one of: Applied, Interview, Rejected, Offer"
    )
    private String status;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    @NotNull(message = "An application must be tied to a user")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    private List<@Positive(message = "Each project ID must be a positive number") Long> projectIds;
}