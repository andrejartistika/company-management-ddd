package com.example.projectmanagement.domain.repository;

import com.example.projectmanagement.domain.model.Project;
import com.example.projectmanagement.domain.model.ProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, ProjectId> {
}
