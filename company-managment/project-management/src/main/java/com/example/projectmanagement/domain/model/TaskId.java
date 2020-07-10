package com.example.projectmanagement.domain.model;

import com.example.sharedkernel.domain.base.DomainObjectId;

public class TaskId extends DomainObjectId {
    public TaskId(){
        super(DomainObjectId.randomId(TaskId.class).getId());
    }
    public TaskId(String id) {
        super(id);
    }
}
