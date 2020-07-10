package com.example.employeemanagement.application;


import com.example.employeemanagement.domain.model.*;
import com.example.employeemanagement.domain.repository.EmployeeRepository;
import com.example.employeemanagement.domain.repository.TimesheetRepository;
import com.example.employeemanagement.integration.UserCreatedEvent;
import com.example.employeemanagement.port.ProjectCatalogClient;
import com.example.employeemanagement.port.UserCatalogClient;
import com.example.sharedkernel.domain.demographics.Address;
import com.example.sharedkernel.domain.demographics.FullName;
import com.example.sharedkernel.domain.time.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final TimesheetRepository timesheetRepository;
    private final ProjectCatalogClient projectCatalogClient;
    private final UserCatalogClient userCatalogClient;

    private final ObjectMapper objectMapper;

    public EmployeeManagementService(EmployeeRepository employeeRepository, ObjectMapper objectMapper, TimesheetRepository timesheetRepository, ProjectCatalogClient projectCatalogClient, UserCatalogClient userCatalogClient){
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
        this.timesheetRepository = timesheetRepository;
        this.projectCatalogClient = projectCatalogClient;
        this.userCatalogClient = userCatalogClient;

    }

    public List<Employee> findAllEmployees(){
        return this.employeeRepository.findAll();
    }
    public Optional<Employee> findEmployeeById(UserId userId){
        return this.employeeRepository.findById(userId);
    }


    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onUserCreatedEvent(UserCreatedEvent event) {
        System.out.println("catching event  " + event);
        Employee newEmployee = new Employee(event.getUserId(), event.getRoleName());
        employeeRepository.save(newEmployee);
    }

    public void setEmployeeInformation(UserId userId, Address address, FullName fullName){
        Optional<Employee> emp = findById(userId);
        if(emp.isPresent()){
            Employee employee = emp.get();
            employee.setAddress(address);

            employee.setFullName(fullName);
            employeeRepository.save(employee);
        }
        else {
            throw new IllegalArgumentException("No user present with id in database");
        }
    }

    public void assignProjectToEmployee(UserId userId, ProjectId projectId){
        Optional<Employee> emp = findById(userId);
        if(emp.isPresent()){
            Employee employee = emp.get();
            employee.setProject(projectId);
            employeeRepository.save(employee);
        }
        else {
            throw new IllegalArgumentException("No user present with id in database");
        }
    }

    public List<Task> getTasksForEmployee(UserId userId){
        Optional<Employee> emp = findById(userId);

        if(emp.isPresent()) {

            return this.projectCatalogClient.getAllActiveTasksFromProject(emp.get().getProjectId());
        }
        else {
            throw new IllegalArgumentException("No user present with id in database");
        }
    }

    public void createNewTimesheet(UserId userId, float hoursWorked, Date date, TaskId taskId){

        Optional<Employee> emp = findById(userId);

        if(emp.isPresent()) {
            Project proj = this.projectCatalogClient.findById(emp.get().getProjectId());

            Timesheet timesheet = new Timesheet(new TimesheetId(), emp.get(), hoursWorked, date, taskId);
            timesheetRepository.save(timesheet);
        }
        else {
            throw new IllegalArgumentException("No user present with id in database");
        }
    }

    public void checkTasksValidity(List<Task> tasks, TaskId taskId){
        boolean foundTask = false;
        for (Task t: tasks
        ) {
            if(t.getId().getId().equals(taskId.getId())){
                foundTask = true;
            }
        }
        if(!foundTask) {
            throw new IllegalArgumentException("Employee cannot be assigned to tasks not in project");
        }
    }

    public void changeTaskStatus(UserId userId, TaskId taskId, TaskStatus taskStatus){
        Optional<Employee> employeeOptional = this.findEmployeeById(userId);
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            if(employee.getProjectId() != null){
                List<Task> tasks =  getTasksForEmployee(userId);

                checkTasksValidity(tasks, taskId);

                if(taskStatus == TaskStatus.Inactive) {
                    if (employee.isHasTasksPermissions()) {
                        projectCatalogClient.changeTaskState(employee.getProjectId(), taskId, taskStatus);
                    }
                }
                else {
                    projectCatalogClient.changeTaskState(employee.getProjectId(), taskId, taskStatus);
                }
            }
            else {
                throw new IllegalArgumentException("Employee has no tasks");
            }
        }
    }


    public Optional<Employee> findById(UserId userId) {
        return employeeRepository.findById(userId);
    }

}
