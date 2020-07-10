package com.example.employeemanagement.domain.model;

import com.example.sharedkernel.domain.base.DomainObjectId;

public class TimesheetId extends DomainObjectId {
    public TimesheetId() {
        super(DomainObjectId.randomId(TimesheetId.class).toString());
    }
    public TimesheetId(String id) {
        super(id);
    }
}
