package com.example.usermanagement.domain.events;

import com.example.sharedkernel.domain.base.DomainEvent;
import com.example.usermanagement.domain.model.RoleName;
import com.example.usermanagement.domain.model.User;
import com.example.usermanagement.domain.model.UserId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Objects;
@Getter
public class UserCreated implements DomainEvent {

    @JsonProperty("id")
    private final UserId userId;
    @JsonProperty("occurredOn")
    private final Instant occurredOn;
    @JsonProperty("roleName")
    private final RoleName roleName;


    @JsonCreator
    public UserCreated(@JsonProperty("id") @NonNull UserId userId,
                        @JsonProperty("occurredOn") @NonNull Instant occurredOn,  @JsonProperty("roleName") RoleName roleName) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.occurredOn = Objects.requireNonNull(occurredOn, "occurredOn must not be null");
        this.roleName = roleName;

    }
    @NonNull
    public UserId userId() {
        return userId;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
