package com.example.employeemanagement.integration;

import com.example.sharedkernel.domain.base.DomainEvent;
import com.example.sharedkernel.infra.eventlog.RemoteEventTranslator;
import com.example.sharedkernel.infra.eventlog.StoredDomainEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCreatedEventTranslator implements RemoteEventTranslator {

    private final ObjectMapper objectMapper;

    UserCreatedEventTranslator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(StoredDomainEvent storedDomainEvent) {
        return storedDomainEvent.domainEventClassName().equals("com.example.usermanagement.domain.events.UserCreated");
    }

    @Override
    public Optional<DomainEvent> translate(StoredDomainEvent remoteEvent) {
        return Optional.of(remoteEvent.toDomainEvent(objectMapper, UserCreatedEvent.class));
    }
}
