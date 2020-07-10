package com.example.projectmanagement.domain.model;

import com.example.sharedkernel.domain.base.DomainObjectId;

public class ProjectId extends DomainObjectId {
    public ProjectId() {
        super(DomainObjectId.randomId(ProjectId.class).getId());
    }
    public ProjectId(String id) {
        super(id);
    }
}
