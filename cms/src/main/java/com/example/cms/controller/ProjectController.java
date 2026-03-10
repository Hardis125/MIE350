package com.example.cms.controller;

import com.example.cms.controller.dto.ProjectDto;
import com.example.cms.controller.exceptions.ResourceNotFoundException;
import com.example.cms.model.entity.PortfolioUser;
import com.example.cms.model.entity.Project;
import com.example.cms.model.repository.PortfolioUserRepository;
import com.example.cms.model.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PortfolioUserRepository portfolioUserRepository;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @PostMapping
    public Project createProject(@Valid @RequestBody ProjectDto projectDto) {
        Project newProject = new Project();
        newProject.setTitle(projectDto.getTitle());
        newProject.setOverview(projectDto.getOverview());
        newProject.setResult(projectDto.getResult());

        PortfolioUser user = portfolioUserRepository.findById(projectDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", projectDto.getUserId()));

        newProject.setPortfolioUser(user);
        return projectRepository.save(newProject);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project", id);
        }
        projectRepository.deleteById(id);
    }
}