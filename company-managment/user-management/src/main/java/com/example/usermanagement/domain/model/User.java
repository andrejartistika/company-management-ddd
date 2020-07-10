package com.example.usermanagement.domain.model;


import com.example.sharedkernel.domain.base.AbstractEntity;
import com.example.usermanagement.application.helpers.crypto.Crypto;
import com.example.usermanagement.application.helpers.strings.Strings;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;


@Entity
@Table(name = "user")
@Getter
public class User extends AbstractEntity<UserId> {

    @Version
    private Long version;

    @Column(name = "username", nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String hashPassword;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Role role;

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    public User() {}

    public User(UserId userId, String username, String plainPassword, Role role) throws IllegalArgumentException {
        super(userId);
        this.userName = username;
        changePassword(plainPassword);
        this.role = role;
        this.createdOn = Instant.now();
    }
    public User(@NonNull  String username, @NonNull String plainPassword, Role role) throws IllegalArgumentException {
        this.userName = username;
        changePassword(plainPassword);
        this.role = role;
        this.createdOn = Instant.now();

    }

    public boolean authenticate(@NonNull String password){

        return Crypto.matches(password, this.hashPassword);
    }

    public boolean canAccessUserList(){
        return this.role.getRoleName() == RoleName.ADMINISTRATOR;
    }

    public void changePassword(@NonNull String password) throws  IllegalArgumentException {
        if (Strings.validatePassword(password)) {
            this.hashPassword = Crypto.encrypt(password);
        }else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isCurrent(UserId userId){
        return userId.getId().equals(this.getId());
    }






}
