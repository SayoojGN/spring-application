package com.example.demo.service;

import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeId;

import java.util.List;

public interface EmployeeService {

    List<Employee> getEmployees();

    void addEmployees(Employee employee);

    void deleteEmployeesById(EmployeeId e);

    boolean searchEmployeeById(EmployeeId e);

    String updateEmployee(EmployeeId employeeId, Employee newEmployee, boolean updateName, boolean updateAddress, boolean updateAge);
}
