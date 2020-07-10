package com.example.employeemanagement.domain.repository;

import com.example.employeemanagement.domain.model.Timesheet;
import com.example.employeemanagement.domain.model.TimesheetId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetRepository extends JpaRepository<Timesheet, TimesheetId> {
}
