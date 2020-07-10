package com.example.usermanagement.domain.model;

import com.example.sharedkernel.domain.base.DomainObjectId;

public class RoleId extends DomainObjectId {
    public RoleId() {
        super(DomainObjectId.randomId(RoleId.class).toString());
    }
    public RoleId(String id) {
        super(id);
    }
}
