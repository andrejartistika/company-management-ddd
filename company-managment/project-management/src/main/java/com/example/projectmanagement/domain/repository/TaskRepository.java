package com.example.projectmanagement.domain.repository;

import com.example.projectmanagement.domain.model.Task;
import com.example.projectmanagement.domain.model.TaskId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, TaskId> {
}
