package com.example.employeemanagement.domain.model;

import com.example.sharedkernel.domain.base.AbstractEntity;
import com.example.sharedkernel.domain.time.Date;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "timesheet")
public class Timesheet extends AbstractEntity<TimesheetId> {

    @Version
    private Long version;

    @Column(name="hours_worked")
    private float hoursWorked;
    @Embedded
    private Date date;

    @ManyToOne
    private Employee employee;

    // should add tasks here
    @Embedded
    @AttributeOverride(name="id", column = @Column(name="task_id", nullable = false))
    private TaskId taskId;

    public Timesheet(){}

    public Timesheet(TimesheetId id, Employee employee, float hoursWorked, Date date, TaskId taskId){
        super(id);
        this.hoursWorked = hoursWorked;
        this.date = date;
        this.taskId = taskId;
    }
    public Timesheet (Employee employee, float hoursWorked, Date date, TaskId taskId){
        super();
        this.hoursWorked = hoursWorked;
        this.date = date;
        this.taskId = taskId;
    }

}
