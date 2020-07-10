package com.example.employeemanagement.port.rest;

import com.example.employeemanagement.application.EmployeeManagementService;
import com.example.employeemanagement.domain.model.*;
import com.example.sharedkernel.domain.demographics.Address;
import com.example.sharedkernel.domain.demographics.CityName;
import com.example.sharedkernel.domain.demographics.FullName;
import com.example.sharedkernel.domain.demographics.State;
import com.example.sharedkernel.domain.time.Date;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmloyeeManagementController {

    EmployeeManagementService employeeManagementService;
    EmloyeeManagementController( EmployeeManagementService employeeManagementService){
        this.employeeManagementService = employeeManagementService;
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable("id") String id){
        return this.employeeManagementService.findById(new UserId(id)).get();
    }

    @PostMapping("/{id}/set_info")
    public void setEmployeeInfo(@PathVariable("id") String id, @RequestParam String homeAddress, @RequestParam String cityName, @RequestParam String stateName, @RequestParam  String firstName, @RequestParam  String lastName){
        UserId userId = new UserId(id);
        Address address = new Address(homeAddress, new CityName(cityName), State.valueOf(stateName));
        FullName fullName = new FullName(firstName, lastName);
        this.employeeManagementService.setEmployeeInformation(userId, address, fullName);

    }

    @PostMapping("/{id}/set_project")
    public void setEmployeeProject(@PathVariable("id") String id, @RequestParam String projectId){
        this.employeeManagementService.assignProjectToEmployee(new UserId(id), new ProjectId(projectId) );
    }

    @PostMapping("/{id}/change_task/{task_id}")
    public void setEmployeeProject(@PathVariable("id") String id,  @PathVariable("task_id") String taskId, @RequestParam String taskStatus){
        this.employeeManagementService.changeTaskStatus(new UserId(id), new TaskId(taskId), TaskStatus.valueOf(taskStatus));
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getAllTasksForEmployee(@PathVariable("id") String id){
        return this.employeeManagementService.getTasksForEmployee(new UserId(id));
    }

    @PostMapping("/{id}/timesheet/create")
    public void createNewTimesheet(@PathVariable("id") String id, @RequestParam float hoursWorked, @RequestParam long year, @RequestParam int month, @RequestParam int day, @RequestParam String taskId) {
        this.employeeManagementService.createNewTimesheet(new UserId(id), hoursWorked, new Date(year, month, day), new TaskId(taskId));
    }

}
