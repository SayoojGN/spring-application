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

    @Override
    public String updateEmployee(EmployeeId employeeId, Employee newEmployee, boolean updateName, boolean updateAddress, boolean updateAge) {
        Employee selected = employeeRepository.getReferenceById(employeeId);
        employeeRepository.delete(selected);
        Employee tempEmployee = new Employee();
        tempEmployee.setEmployeeId(employeeId);
        if(updateName){
            tempEmployee.setName(newEmployee.getName());
        }
        else {
            tempEmployee.setName(selected.getName());
        }
        if(updateAddress){
            tempEmployee.setAddress(newEmployee.getAddress());
        }
        else {
            tempEmployee.setAddress(selected.getAddress());
        }
        if(updateAge){
            tempEmployee.setAge(newEmployee.getAge());
        }
        else{
            tempEmployee.setAge(selected.getAge());
        }
        addEmployees(tempEmployee);
        return "Employee updated";
    }

}
