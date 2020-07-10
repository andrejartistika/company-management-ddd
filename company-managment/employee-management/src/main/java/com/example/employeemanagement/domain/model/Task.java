package com.example.employeemanagement.domain.model;

import lombok.Getter;

@Getter
public class Task {

    private TaskId id;
    private TaskStatus taskStatus;
    private String taskName;
    private Project project;
    private boolean isActive;
}
