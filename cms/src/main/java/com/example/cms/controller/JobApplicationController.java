// This is for JobApplicationController
//DTO field is projectId
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
        application.setCompany(jobApplicationDto.getCompany());
        application.setRole(jobApplicationDto.getRole());
        application.setDates(jobApplicationDto.getDates());
        application.setStatus(jobApplicationDto.getStatus());
        application.setNotes(jobApplicationDto.getNotes());

        PortfolioUser user = portfolioUserRepository.findById(jobApplicationDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", jobApplicationDto.getUserId()));
        application.setPortfolioUser(user);

        List<Project> linkedProjects = new ArrayList<>();
        if (jobApplicationDto.getProjectIds() != null) {
            for (Long projectId : jobApplicationDto.getProjectIds()) {
                Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new ResourceNotFoundException("Project", projectId));
                linkedProjects.add(project);
            }
        }
        application.setLinkedProjects(linkedProjects);

        return jobApplicationRepository.save(application);
    }

    @PutMapping("/{id}")
    public JobApplication updateJobApplication(@PathVariable Long id,
                                               @Valid @RequestBody JobApplicationDto jobApplicationDto) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", id));

        application.setCompany(jobApplicationDto.getCompany());
        application.setRole(jobApplicationDto.getRole());
        application.setDates(jobApplicationDto.getDates());
        application.setStatus(jobApplicationDto.getStatus());
        application.setNotes(jobApplicationDto.getNotes());

        PortfolioUser user = portfolioUserRepository.findById(jobApplicationDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", jobApplicationDto.getUserId()));
        application.setPortfolioUser(user);

        List<Project> linkedProjects = new ArrayList<>();
        if (jobApplicationDto.getProjectIds() != null) {
            for (Long projectId : jobApplicationDto.getProjectIds()) {
                Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new ResourceNotFoundException("Project", projectId));
                linkedProjects.add(project);
            }
        }
        application.setLinkedProjects(linkedProjects);

        return jobApplicationRepository.save(application);
    }

    @DeleteMapping("/{id}")
    public void deleteJobApplication(@PathVariable Long id) {
        if (!jobApplicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("JobApplication", id);
        }
        jobApplicationRepository.deleteById(id);
    }
}