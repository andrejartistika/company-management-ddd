package com.example.employeemanagement.domain.model;

import com.example.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class TaskId extends DomainObjectId {
    public TaskId() {
        super(DomainObjectId.randomId(TaskId.class).toString());
    }
    public TaskId(String id) {
        super(id);
    }
}
