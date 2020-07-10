package com.example.employeemanagement.domain.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Project {
    private ProjectId id;
    private String projectName;
    private String about;
    private List<Task> tasks;

}
