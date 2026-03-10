package com.example.cms.controller;

import com.example.cms.controller.dto.JobApplicationDto;
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
    public List<JobApplication> getAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    @GetMapping("/{id}")
    public JobApplication getJobApplicationById(@PathVariable Long id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", id));
    }

    @PostMapping
    public JobApplication createJobApplication(@Valid @RequestBody JobApplicationDto jobApplicationDto) {
        JobApplication application = new JobApplication();
        applyDtoToEntity(application, jobApplicationDto);
        return jobApplicationRepository.save(application);
    }

    @PutMapping("/{id}")
    public JobApplication updateJobApplication(@PathVariable Long id,
                                               @Valid @RequestBody JobApplicationDto jobApplicationDto) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", id));

        applyDtoToEntity(application, jobApplicationDto);
        return jobApplicationRepository.save(application);
    }

    @DeleteMapping("/{id}")
    public void deleteJobApplication(@PathVariable Long id) {
        if (!jobApplicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("JobApplication", id);
        }
        jobApplicationRepository.deleteById(id);
    }

    private void applyDtoToEntity(JobApplication application, JobApplicationDto dto) {
        application.setCompany(dto.getCompany());
        application.setRole(dto.getRole());
        application.setDates(dto.getDates());
        application.setStatus(dto.getStatus());
        application.setNotes(dto.getNotes());

        PortfolioUser user = portfolioUserRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));
        application.setPortfolioUser(user);

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
}