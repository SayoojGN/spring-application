package com.example.demo.service.impl;

import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeId;
import com.example.demo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public void addEmployees(Employee employee){
        employeeRepository.save(employee);
    }

    public void deleteEmployeesById(EmployeeId e){
        employeeRepository.deleteById(e);
    }

    public boolean searchEmployeeById(EmployeeId e) { return employeeRepository.existsById(e); }
}
