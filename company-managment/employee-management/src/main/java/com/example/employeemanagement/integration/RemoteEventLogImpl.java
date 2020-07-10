package com.example.employeemanagement.integration;

import com.example.sharedkernel.domain.base.RemoteEventLog;
import com.example.sharedkernel.infra.eventlog.StoredDomainEvent;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class RemoteEventLogImpl implements RemoteEventLog {
    List<StoredDomainEvent> events;
    public RemoteEventLogImpl(ResponseEntity<List<StoredDomainEvent>> response) {
        this.events = response.getBody();
    }
    @Override
    public List<StoredDomainEvent> events() {
        return this.events;
    }
}
