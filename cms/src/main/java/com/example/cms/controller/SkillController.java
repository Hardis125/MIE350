package com.example.cms.controller;

import com.example.cms.controller.dto.SkillDto;
import com.example.cms.controller.dto.SkillResponseDto;
import com.example.cms.controller.exceptions.ResourceNotFoundException;
import com.example.cms.model.entity.Project;
import com.example.cms.model.entity.Skill;
import com.example.cms.model.repository.ProjectRepository;
import com.example.cms.model.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<SkillResponseDto> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SkillResponseDto getSkillById(@PathVariable Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", id));
        return toDto(skill);
    }

    @PostMapping
    public SkillResponseDto createSkill(@Valid @RequestBody SkillDto skillDto) {
        Skill skill = new Skill();
        skill.setName(skillDto.getName());

        Skill savedSkill = skillRepository.save(skill);
        return toDto(savedSkill);
    }

    @PutMapping("/{id}")
    public SkillResponseDto updateSkill(@PathVariable Long id,
                                        @Valid @RequestBody SkillDto skillDto) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", id));

        skill.setName(skillDto.getName());

        Skill savedSkill = skillRepository.save(skill);
        return toDto(savedSkill);
    }

    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", id));

        for (Project project : new ArrayList<>(skill.getProjects())) {
            project.getSkills().remove(skill);
            projectRepository.save(project);
        }

        skill.getProjects().clear();
        skillRepository.delete(skill);
    }

    private SkillResponseDto toDto(Skill skill) {
        SkillResponseDto dto = new SkillResponseDto();
        dto.setId(skill.getId());
        dto.setName(skill.getName());

        if (skill.getProjects() != null) {
            dto.setProjectIds(
                    skill.getProjects().stream()
                            .map(Project::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}