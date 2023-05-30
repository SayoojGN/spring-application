package com.example.demo.service;

import com.example.demo.EmployeeRepository;
import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeServiceImpl2 implements EmployeeService2{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Async
    public CompletableFuture<String> getNameOfEmpId(EmployeeId employeeId) {
        Thread t = Thread.currentThread();
        System.out.println(t.getId());
        Employee temp = employeeRepository.findById(employeeId).get();
        System.out.println("EmployeeName--> " + temp.getName());
        return null;
    }

    @Override
    @Async
    public CompletableFuture<String> getAddressOfEmpId(EmployeeId employeeId) {
        Thread t = Thread.currentThread();
        System.out.println(t.getId());
        Employee temp = employeeRepository.findById(employeeId).get();
        System.out.println("EmployeeAddress--> " + temp.getAddress());
        return null;
    }

    @Override
    @Async
    public CompletableFuture<Integer> getAgeOfEmpId(EmployeeId employeeId) {
        Thread t = Thread.currentThread();
        System.out.println(t.getId());
        Employee temp = employeeRepository.findById(employeeId).get();
        System.out.println("EmployeeAge--> " + temp.getAge());
        return null;
    }

}
