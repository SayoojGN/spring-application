package com.example.demo.service;

import com.example.demo.entities.EmployeeId;

import java.util.concurrent.CompletableFuture;

public interface EmployeeService2 {

    CompletableFuture<String> getNameOfEmpId(EmployeeId employeeId);


    CompletableFuture<String> getAddressOfEmpId(EmployeeId employeeId);


    CompletableFuture<Integer> getAgeOfEmpId(EmployeeId employeeId);
}
