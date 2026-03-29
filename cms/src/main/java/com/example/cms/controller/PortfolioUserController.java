package com.example.cms.controller;

import com.example.cms.controller.dto.PortfolioUserDto;
import com.example.cms.controller.dto.PortfolioUserResponseDto;
import com.example.cms.controller.exceptions.ResourceNotFoundException;
import com.example.cms.model.entity.JobApplication;
import com.example.cms.model.entity.PortfolioUser;
import com.example.cms.model.entity.Project;
import com.example.cms.model.entity.Skill;
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
@RequestMapping("/users")
public class PortfolioUserController {

    @Autowired
    private PortfolioUserRepository portfolioUserRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @GetMapping
    public List<PortfolioUserResponseDto> getAllUsers() {
        return portfolioUserRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PortfolioUserResponseDto getUserById(@PathVariable Long id) {
        PortfolioUser user = portfolioUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return toDto(user);
    }

    @PostMapping
    public PortfolioUserResponseDto createUser(@Valid @RequestBody PortfolioUserDto userDto) {
        String normalizedEmail = userDto.getEmail().trim();

        if (portfolioUserRepository.existsByEmailIgnoreCase(normalizedEmail)){
            throw new IllegalArgumentException("Email already exists");
        }

        PortfolioUser user = new PortfolioUser();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(normalizedEmail);

        PortfolioUser savedUser = portfolioUserRepository.save(user);
        return toDto(savedUser);
    }

    @PutMapping("/{id}")
    public PortfolioUserResponseDto updateUser(@PathVariable Long id,
                                               @Valid @RequestBody PortfolioUserDto userDto) {
        PortfolioUser user = portfolioUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        String normalizedEmail = userDto.getEmail().trim();

        if(!user.getEmail().equalsIgnoreCase(normalizedEmail) &&
            portfolioUserRepository.existsByEmailIgnoreCase(normalizedEmail)) {
                throw new IllegalArgumentException("Email already exists");
            }

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(normalizedEmail);

        PortfolioUser savedUser = portfolioUserRepository.save(user);
        return toDto(savedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        PortfolioUser user = portfolioUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        for (JobApplication application : new ArrayList<>(user.getJobApplications())) {
            application.getLinkedProjects().clear();
            application.setPortfolioUser(null);
            jobApplicationRepository.delete(application);
        }

        for (Project project : new ArrayList<>(user.getProjects())) {
            for (Skill skill : new ArrayList<>(project.getSkills())) {
                skill.getProjects().remove(project);
            }
            project.getSkills().clear();
            project.setPortfolioUser(null);
            projectRepository.delete(project);
        }

        portfolioUserRepository.delete(user);
    }

    private PortfolioUserResponseDto toDto(PortfolioUser user) {
        PortfolioUserResponseDto dto = new PortfolioUserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());

        if (user.getProjects() != null) {
            dto.setProjectIds(
                    user.getProjects().stream()
                            .map(Project::getId)
                            .collect(Collectors.toList())
            );
        }

        if (user.getJobApplications() != null) {
            dto.setJobApplicationIds(
                    user.getJobApplications().stream()
                            .map(JobApplication::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}