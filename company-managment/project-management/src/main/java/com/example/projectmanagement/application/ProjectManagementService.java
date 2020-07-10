package com.example.projectmanagement.application;

import com.example.projectmanagement.domain.model.*;
import com.example.projectmanagement.domain.repository.ProjectRepository;
import com.example.projectmanagement.domain.repository.TaskRepository;
import net.bytebuddy.description.NamedElement;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectManagementService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;


    public ProjectManagementService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public List<Project> findAllProjects(){
        return this.projectRepository.findAll();
    }



    public void createProject(String name, String about ){
        Project project = new Project(name, about);
        this.projectRepository.save(project);
    }

    public void addTaskToProject(ProjectId projectId, Task t){
        Optional<Project> project = findProjectById(projectId);
        if(project.isPresent()){
            project.get().addNewTask(t);
            this.taskRepository.save(t);
        }
        else {
            throw new IllegalArgumentException("ProjectId doesn't exist in the database");
        }

    }

//    public List<Task> findAllTasks() {
//        return this.taskRepository.findAll();
//    }

    public Optional<Project> findProjectById(ProjectId projectId) {
        return this.projectRepository.findById(projectId);
    }

//    public Optional<Task> findTaskById(TaskId taskId) {
//        return this.taskRepository.findById(taskId);
//    }

    public void changeTaskStatus(ProjectId projectId, TaskId taskId, TaskStatus nextStatus){

        Optional<Project> projOpt = this.findProjectById(projectId);
        if(projOpt.isPresent()){
            projOpt.get().changeStatusOfTask(taskId, nextStatus);
        }

        else throw new IllegalArgumentException("TaskId doesn't exist in the database");

    }

    public List<Task> findAllTasksFromProject(ProjectId projectId) {
        Optional<Project> projOpt = this.findProjectById(projectId);
        if(projOpt.isPresent()){
            return projOpt.get().getTasks();
        }
        else throw new IllegalArgumentException("ProjectId doesn't exist in the database");
    }

    public List<Task> findAllActiveTasksFromProject(ProjectId projectId) {
        Optional<Project> projOpt = this.findProjectById(projectId);
        if(projOpt.isPresent()){
            return projOpt.get().getActiveTasks();
        }
        else throw new IllegalArgumentException("ProjectId doesn't exist in the database");
    }

    public List<Task> findAllInactiveTasksFromProject(ProjectId projectId) {
        Optional<Project> projOpt = this.findProjectById(projectId);
        if(projOpt.isPresent()){
            return projOpt.get().getInactiveTasks();
        }
        else throw new IllegalArgumentException("ProjectId doesn't exist in the database");
    }

}
