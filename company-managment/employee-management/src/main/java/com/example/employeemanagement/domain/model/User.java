package com.example.employeemanagement.domain.model;


import com.example.sharedkernel.domain.base.AbstractEntity;

import lombok.Getter;

import javax.persistence.*;
import java.time.Instant;



@Getter
public class User  {

    private String userId;

    private String userName;

    private Role role;

    private Instant createdOn;


}
