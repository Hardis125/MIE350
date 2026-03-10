package com.example.cms.controller;

import com.example.cms.controller.dto.ProjectDto;
import com.example.cms.controller.dto.ProjectResponseDto;
import com.example.cms.controller.exceptions.ResourceNotFoundException;
import com.example.cms.model.entity.JobApplication;
import com.example.cms.model.entity.PortfolioUser;
import com.example.cms.model.entity.Project;
import com.example.cms.model.entity.Skill;
import com.example.cms.model.repository.JobApplicationRepository;
import com.example.cms.model.repository.PortfolioUserRepository;
import com.example.cms.model.repository.ProjectRepository;
import com.example.cms.model.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PortfolioUserRepository portfolioUserRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @GetMapping
    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjectResponseDto getProjectById(@PathVariable Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
        return toDto(project);
    }

    @PostMapping
    public ProjectResponseDto createProject(@Valid @RequestBody ProjectDto projectDto) {
        Project project = new Project();
        applyDtoToEntity(project, projectDto);

        Project savedProject = projectRepository.save(project);
        return toDto(savedProject);
    }

    @PutMapping("/{id}")
    public ProjectResponseDto updateProject(@PathVariable Long id,
                                            @Valid @RequestBody ProjectDto projectDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));

        applyDtoToEntity(project, projectDto);

        Project savedProject = projectRepository.save(project);
        return toDto(savedProject);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));

        for (JobApplication application : jobApplicationRepository.findAll()) {
            if (application.getLinkedProjects() != null &&
                application.getLinkedProjects().remove(project)) {
                jobApplicationRepository.save(application);
            }
        }

        for (Skill skill : new ArrayList<>(project.getSkills())) {
            skill.getProjects().remove(project);
        }
        project.getSkills().clear();

        if (project.getPortfolioUser() != null && project.getPortfolioUser().getProjects() != null) {
            project.getPortfolioUser().getProjects().remove(project);
        }

        projectRepository.delete(project);
    }

    private void applyDtoToEntity(Project project, ProjectDto dto) {
        project.setTitle(dto.getTitle());
        project.setOverview(dto.getOverview());
        project.setResult(dto.getResult());

        PortfolioUser user = portfolioUserRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));

        if (project.getPortfolioUser() != null &&
            !project.getPortfolioUser().getId().equals(user.getId())) {
            project.getPortfolioUser().getProjects().remove(project);
        }

        project.setPortfolioUser(user);

        if (user.getProjects() != null && !user.getProjects().contains(project)) {
            user.getProjects().add(project);
        }

        List<Skill> newSkills = getSkillsFromIds(dto.getSkillIds());
        syncProjectSkills(project, newSkills);
    }

    private List<Skill> getSkillsFromIds(List<Long> skillIds) {
        List<Skill> skills = new ArrayList<>();

        if (skillIds == null || skillIds.isEmpty()) {
            return skills;
        }

        for (Long skillId : skillIds) {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new ResourceNotFoundException("Skill", skillId));
            skills.add(skill);
        }

        return skills;
    }

    private void syncProjectSkills(Project project, List<Skill> newSkills) {
        for (Skill oldSkill : new ArrayList<>(project.getSkills())) {
            oldSkill.getProjects().remove(project);
        }
        project.getSkills().clear();

        for (Skill skill : newSkills) {
            if (!project.getSkills().contains(skill)) {
                project.getSkills().add(skill);
            }
            if (skill.getProjects() != null && !skill.getProjects().contains(project)) {
                skill.getProjects().add(project);
            }
        }
    }

    private ProjectResponseDto toDto(Project project) {
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setOverview(project.getOverview());
        dto.setResult(project.getResult());

        if (project.getPortfolioUser() != null) {
            dto.setUserId(project.getPortfolioUser().getId());
        }

        if (project.getSkills() != null) {
            dto.setSkillIds(
                    project.getSkills().stream()
                            .map(Skill::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}