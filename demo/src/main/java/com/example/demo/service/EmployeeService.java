package com.example.demo.service;

import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeId;

import java.util.List;

public interface EmployeeService {

    // This method use to encrypt to string
    String encrypt(String strToEncrypt);

    String decrypt(String strToDecrypt);

    List<Employee> getEmployees();

    void addEmployees(Employee employee);

    void deleteEmployeesById(EmployeeId e);

    boolean searchEmployeeById(EmployeeId e);

    void updateEmployee(EmployeeId employeeId, Employee newEmployee, boolean updateEmiratesIdNo, boolean updateName, boolean updateAddress, boolean updateAge);
}
