package com.example.demo.service.impl;

import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeId;
import com.example.demo.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    @Override
    public void addEmployees(Employee employee){
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeesById(EmployeeId e){
        employeeRepository.deleteById(e);
    }

    @Override
    public boolean searchEmployeeById(EmployeeId e) { return employeeRepository.existsById(e); }

}
