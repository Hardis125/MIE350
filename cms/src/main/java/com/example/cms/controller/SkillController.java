package com.example.cms.controller;

import com.example.cms.controller.dto.SkillDto;
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

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @GetMapping("/{id}")
    public Skill getSkillById(@PathVariable Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", id));
    }

    @PostMapping
    public Skill createSkill(@Valid @RequestBody SkillDto skillDto) {
        Skill skill = new Skill();
        skill.setName(skillDto.getName());

        return skillRepository.save(skill);
    }

    @PutMapping("/{id}")
    public Skill updateSkill(@PathVariable Long id,
                             @Valid @RequestBody SkillDto skillDto) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", id));

        skill.setName(skillDto.getName());

        return skillRepository.save(skill);
    }

    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", id));

        for (Project project : new ArrayList<>(skill.getProjects())) {
            project.getSkills().remove(skill);
            projectRepository.save(project);
        }

        skillRepository.delete(skill);
    }
}