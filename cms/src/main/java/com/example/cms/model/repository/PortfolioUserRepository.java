package com.example.cms.model.repository;

import com.example.cms.model.entity.PortfolioUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioUserRepository extends JpaRepository<PortfolioUser, Long> {
}