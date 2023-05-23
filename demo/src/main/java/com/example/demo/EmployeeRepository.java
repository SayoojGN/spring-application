package com.example.demo;

import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {
}
