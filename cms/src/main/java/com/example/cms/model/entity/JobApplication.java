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
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String role;
    private String dates;
    private String status;

    @Column(length = 1000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "userId")
    private PortfolioUser portfolioUser;

    @ManyToMany
    @JoinTable(
            name = "application_projects",
            joinColumns = @JoinColumn(name = "applicationId"),
            inverseJoinColumns = @JoinColumn(name = "projectId")
    )
    private List<Project> linkedProjects = new ArrayList<>();
}