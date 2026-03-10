package com.example.cms.model.repository;

import com.example.cms.model.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByCompanyContainingIgnoreCase(String company);
    List<JobApplication> findByStatusContainingIgnoreCase(String status);
}