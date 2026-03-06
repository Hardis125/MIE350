package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String overview;

    private String result;

    @ManyToOne
    @JoinColumn(name = "userId")
    private PortfolioUser portfolioUser;

    @ManyToMany
    @JoinTable(
            name = "project_skills",
            joinColumns = @JoinColumn(name = "projectId"),
            inverseJoinColumns = @JoinColumn(name = "skillId")
    )
    private List<Skill> skills = new ArrayList<>();

    @ManyToMany(mappedBy = "linkedProjects")
    private List<JobApplication> linkedApplications = new ArrayList<>();
}