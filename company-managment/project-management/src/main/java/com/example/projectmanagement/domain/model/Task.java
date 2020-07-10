package com.example.projectmanagement.domain.model;

import com.example.sharedkernel.domain.base.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "task")
public class Task extends AbstractEntity<TaskId> {

    @Column(name = "order_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @ManyToOne
    @JsonIgnore
    private Project project;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public Task() {
    }

    public Task(TaskId id, TaskStatus taskStatus, String taskName, Project project) {
        super(id);
        this.taskStatus = taskStatus;
        this.taskName = taskName;
        if(this.taskStatus == TaskStatus.Inactive){
            this.isActive = false;
        }
        else {
            this.isActive = true;
        }
        this.project = project;
    }
    public Task( TaskStatus taskStatus, String taskName, Project project) {
        super(new TaskId());
        this.taskStatus = taskStatus;
        this.taskName = taskName;
        if(this.taskStatus == TaskStatus.Inactive){
            this.isActive = false;
        }
        else {
            this.isActive = true;
        }
        this.project = project;
    }

    public void setTaskName(@NonNull String taskName){
        if(!taskName.isEmpty()){
            this.taskName = taskName;
        }
    }

    public void changeStatus(@NonNull  TaskStatus nextStatus) {
        switch (nextStatus){
            case Inactive:
                if(taskStatus == TaskStatus.Done){
                    taskStatus = nextStatus;
                    this.isActive = false;
                }
                else {
                    throw new IllegalArgumentException("Cannot change task status to inactive from " + taskStatus.name());
                }

                return;
            case Done:
                if(taskStatus == TaskStatus.ReadyForTest){
                    taskStatus = nextStatus;
                }
                else {
                    throw new IllegalArgumentException("Cannot change task status to done from " + taskStatus.name());
                }

                return;
            case ReadyForTest:
                if(taskStatus == TaskStatus.InProgress || taskStatus == TaskStatus.Done){
                    taskStatus = nextStatus;
                }
                else {
                    throw new IllegalArgumentException("Cannot change task status to ready for test from " + taskStatus.name());
                }
                return;
            case InProgress:
                if(taskStatus == TaskStatus.ToDo || taskStatus == TaskStatus.ReadyForTest  ){
                    taskStatus = nextStatus;
                }
                else {
                    throw new IllegalArgumentException("Cannot change task status to in progress from " + taskStatus.name());
                }
                return;
            case ToDo:
                taskStatus = nextStatus;
                return;
            case Refinement:
                taskStatus = nextStatus;


            default:
                throw new  IllegalArgumentException("status invalid");

        }

    }

}
