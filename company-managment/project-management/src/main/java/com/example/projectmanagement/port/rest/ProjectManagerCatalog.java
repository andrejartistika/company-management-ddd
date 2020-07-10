package com.example.projectmanagement.port.rest;

import com.example.projectmanagement.application.ProjectManagementService;
import com.example.projectmanagement.domain.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectManagerCatalog {
    private final ProjectManagementService projectManagementService;

    public ProjectManagerCatalog(ProjectManagementService projectManagementService) {
        this.projectManagementService = projectManagementService;
    }

    @GetMapping
    public List<Project> findAll(){
        return this.projectManagementService.findAllProjects();
    }
    @GetMapping("/{id}")
    public Project findById (@PathVariable("id") String projectId){
        Optional<Project> projectOptional = this.projectManagementService.findProjectById(new ProjectId(projectId));
        if(projectOptional.isPresent()){
            return projectOptional.get();
        }
        else throw new IllegalArgumentException("Project id doesn't exist in the database");
    }
    @GetMapping("/{id}/tasks/get")
    public List<Task> getAllTasksFromProject(@PathVariable("id") String projectId){
        return this.projectManagementService.findAllActiveTasksFromProject(new ProjectId(projectId));
    }
    @GetMapping("/{id}/tasks/get/active")
    public List<Task> getAllActiveTasksFromProject(@PathVariable("id") String projectId){
        return this.projectManagementService.findAllActiveTasksFromProject(new ProjectId(projectId));
    }

    @PostMapping("/create")
    public void createNewProject(@RequestParam  String name, @RequestParam String about){
        this.projectManagementService.createProject(name, about);
    }

    @PostMapping("/{id}/tasks/create")
    public void addTaskToProject(@PathVariable("id") String projectId, @RequestParam String taskName){
        Optional<Project> projectOptional = this.projectManagementService.findProjectById(new ProjectId(projectId));
        if(projectOptional.isPresent()){
            Task task = new Task(TaskStatus.Refinement, taskName, projectOptional.get());
            this.projectManagementService.addTaskToProject(new ProjectId(projectId), task);
        }
        else throw new IllegalArgumentException("Project id doesn't exist in the database");
    }

    @PostMapping("/{id}/tasks/change_status")
    public void taskChangeState(@PathVariable("id") String projectId, @RequestParam String taskId, @RequestParam String nextTaskState){
        this.projectManagementService.changeTaskStatus(new ProjectId(projectId), new TaskId(taskId), TaskStatus.valueOf(nextTaskState));

    }
}
