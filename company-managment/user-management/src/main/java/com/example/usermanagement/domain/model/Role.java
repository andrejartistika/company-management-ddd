package com.example.usermanagement.domain.model;

import com.example.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="role")
public class Role extends AbstractEntity<RoleId> {

    @Column(name = "role_name", nullable = false)
    @Enumerated (EnumType.STRING)
    private RoleName name;

    @OneToMany(mappedBy = "role")
    private List<User> userList;

    public Role(){
    }
    public Role(RoleName roleName){
        super(new RoleId());

        this.name = roleName;
    }
    public Role(String roleName){
        super(new RoleId());
        roleName = roleName.toUpperCase();
        switch (roleName){
            case "MEMBER":
                this.name = RoleName.MEMBER;
                return;
            case "MANAGER":
                this.name = RoleName.MANAGER;
                return;

            case "ADMINISTRATOR":
                this.name = RoleName.ADMINISTRATOR;
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    public RoleName getRoleName() {
        return name;
    }

}
