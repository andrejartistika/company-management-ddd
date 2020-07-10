package com.example.projectmanagement.domain.model;

import com.example.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "project")
public class Project extends AbstractEntity<ProjectId> {
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "about")
    private String about;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    public Project() {
    }

    public Project(ProjectId id, String name, String about) {
        super(id);
        this.projectName = name;
        this.about = about;
    }
    public Project(String name, String about){
        super(new ProjectId());
        this.projectName = name;
        this.about = about;
    }

    public void addNewTask(Task task){
        tasks.add(task);
    }

    public Optional<Task> findTask(TaskId taskId){
        return tasks.stream().filter(task -> task.getId().getId().equals(taskId.getId())).findFirst();
    }
    public void changeStatusOfTask(TaskId taskId, TaskStatus status){
        Optional<Task> optionalTask = findTask(taskId);
        if(optionalTask.isPresent()){
            optionalTask.get().changeStatus(status);
        }
        else throw new IllegalArgumentException("TaskId not present in project with id " + id().getId());
    }


    public List<Task> getActiveTasks(){
        return tasks.stream().filter(task -> task.isActive()).collect(Collectors.toList());
    }

    public List<Task> getInactiveTasks(){
        return tasks.stream().filter(task -> !task.isActive()).collect(Collectors.toList());
    }

}
