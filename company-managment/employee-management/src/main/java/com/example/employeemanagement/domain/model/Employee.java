package com.example.employeemanagement.domain.model;




import com.example.sharedkernel.domain.base.AbstractEntity;
import com.example.sharedkernel.domain.demographics.Address;
import com.example.sharedkernel.domain.demographics.FullName;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "employee")
public class Employee extends AbstractEntity<UserId> {

    @Version
    private Long version;

    @Embedded
    private Address privateAddress;

    @Embedded
    private FullName fullName;

    @OneToMany(mappedBy = "employee")
    private List<Timesheet> timesheetList;

    @Embedded
    @AttributeOverride(name="id", column = @Column(name="project_id"))
    private ProjectId projectId;

    @Column(name = "has_tasks_permissions")
    private boolean hasTasksPermissions;


    public Employee() {

    }
    public Employee(UserId id) {
        super(id);
    }
    public Employee(UserId id, RoleName roleName) {
        super(id);
        if(roleName == RoleName.MANAGER || roleName == RoleName.ADMINISTRATOR){
            hasTasksPermissions = true;
        }
        else hasTasksPermissions = false;
    }
    public Employee(UserId id, Address privateAddress, FullName fullName){
        super(id);
        this.privateAddress = privateAddress;
        this.fullName = fullName;
        this.hasTasksPermissions = false;
    }
    public Employee(Address privateAddress, FullName fullName){
        this.privateAddress = privateAddress;
        this.fullName = fullName;
        this.hasTasksPermissions = false;
    }
    public Employee(Address privateAddress, FullName fullName, boolean hasTasksPermissions){
        this.privateAddress = privateAddress;
        this.fullName = fullName;
        this.hasTasksPermissions = hasTasksPermissions;
    }

    public void setProject(@NonNull ProjectId projectId){
        this.projectId = projectId;
    }
    public void setAddress(@NonNull Address newAddress) {
        this.privateAddress = newAddress;
    }
    public void setFullName(@NonNull FullName fullName) {
        this.fullName = fullName;
    }


}
