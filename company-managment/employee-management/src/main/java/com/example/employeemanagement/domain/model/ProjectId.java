package com.example.employeemanagement.domain.model;

import com.example.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;


@Embeddable
public class ProjectId extends DomainObjectId {
    public ProjectId() {
        super(DomainObjectId.randomId(ProjectId.class).toString());
    }
    public ProjectId(String id) {
        super(id);
    }
}
