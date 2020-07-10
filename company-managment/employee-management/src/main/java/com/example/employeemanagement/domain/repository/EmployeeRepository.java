package com.example.employeemanagement.domain.repository;

import com.example.employeemanagement.domain.model.Employee;
import com.example.employeemanagement.domain.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UserId> {
}
