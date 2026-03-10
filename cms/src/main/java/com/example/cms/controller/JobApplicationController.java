package com.example.cms.controller;

import com.example.cms.controller.dto.JobApplicationDto;
import com.example.cms.controller.dto.JobApplicationResponseDto;
import com.example.cms.controller.exceptions.ResourceNotFoundException;
import com.example.cms.model.entity.JobApplication;
import com.example.cms.model.entity.PortfolioUser;
import com.example.cms.model.entity.Project;
import com.example.cms.model.repository.JobApplicationRepository;
import com.example.cms.model.repository.PortfolioUserRepository;
import com.example.cms.model.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/job-applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private PortfolioUserRepository portfolioUserRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<JobApplicationResponseDto> getAllJobApplications() {
        return jobApplicationRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public JobApplicationResponseDto getJobApplicationById(@PathVariable Long id) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", id));
        return toDto(application);
    }

    @PostMapping
    public JobApplicationResponseDto createJobApplication(@Valid @RequestBody JobApplicationDto jobApplicationDto) {
        JobApplication application = new JobApplication();
        applyDtoToEntity(application, jobApplicationDto);

        JobApplication savedApplication = jobApplicationRepository.save(application);
        return toDto(savedApplication);
    }

    @PutMapping("/{id}")
    public JobApplicationResponseDto updateJobApplication(@PathVariable Long id,
                                                          @Valid @RequestBody JobApplicationDto jobApplicationDto) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", id));

        applyDtoToEntity(application, jobApplicationDto);

        JobApplication savedApplication = jobApplicationRepository.save(application);
        return toDto(savedApplication);
    }

    @DeleteMapping("/{id}")
    public void deleteJobApplication(@PathVariable Long id) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", id));

        if (application.getPortfolioUser() != null &&
            application.getPortfolioUser().getJobApplications() != null) {
            application.getPortfolioUser().getJobApplications().remove(application);
        }

        if (application.getLinkedProjects() != null) {
            application.getLinkedProjects().clear();
        }

        jobApplicationRepository.delete(application);
    }

    private void applyDtoToEntity(JobApplication application, JobApplicationDto dto) {
        application.setCompany(dto.getCompany());
        application.setRole(dto.getRole());
        application.setDates(dto.getDates());
        application.setStatus(dto.getStatus());
        application.setNotes(dto.getNotes());

        PortfolioUser user = portfolioUserRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));

        if (application.getPortfolioUser() != null &&
            !application.getPortfolioUser().getId().equals(user.getId())) {
            application.getPortfolioUser().getJobApplications().remove(application);
        }

        application.setPortfolioUser(user);

        if (user.getJobApplications() != null && !user.getJobApplications().contains(application)) {
            user.getJobApplications().add(application);
        }

        List<Project> linkedProjects = getProjectsFromIds(dto.getProjectIds(), user.getId());
        application.setLinkedProjects(linkedProjects);
    }

    private List<Project> getProjectsFromIds(List<Long> projectIds, Long userId) {
        List<Project> linkedProjects = new ArrayList<>();

        if (projectIds == null || projectIds.isEmpty()) {
            return linkedProjects;
        }

        for (Long projectId : projectIds) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project", projectId));

            if (project.getPortfolioUser() == null ||
                    !project.getPortfolioUser().getId().equals(userId)) {
                throw new IllegalArgumentException(
                        "Project " + projectId + " does not belong to user " + userId
                );
            }

            linkedProjects.add(project);
        }

        return linkedProjects;
    }

    private JobApplicationResponseDto toDto(JobApplication application) {
        JobApplicationResponseDto dto = new JobApplicationResponseDto();
        dto.setId(application.getId());
        dto.setCompany(application.getCompany());
        dto.setRole(application.getRole());
        dto.setDates(application.getDates());
        dto.setStatus(application.getStatus());
        dto.setNotes(application.getNotes());

        if (application.getPortfolioUser() != null) {
            dto.setUserId(application.getPortfolioUser().getId());
        }

        if (application.getLinkedProjects() != null) {
            dto.setProjectIds(
                    application.getLinkedProjects().stream()
                            .map(Project::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}