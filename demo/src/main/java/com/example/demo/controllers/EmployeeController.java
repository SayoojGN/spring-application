package com.example.demo.controllers;

import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeId;
import com.example.demo.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.EmployeeDTO;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequestMapping("/")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl service;

    @GetMapping
    public List<Employee> getEmployee() {
        return service.getEmployees();
    }

    @PostMapping("/add")
    public String addEmployee(@RequestParam("empId") Long empId, @RequestParam("companyCode") Long companyCode, @RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("address") String address) {

        EmployeeId temp2 = new EmployeeId(empId, companyCode);
        Employee temp = Employee.builder()
                .employeeId(temp2)
                .address(address)
                .age(age)
                .name(name).build();

        service.addEmployees(temp);
        return "Record added successfully";
    }

    @PostMapping("/add2")
    public String addEmployee2(@RequestBody EmployeeDTO employeeDTO){
        EmployeeId temp2 = new EmployeeId(employeeDTO.getEmpId(), employeeDTO.getCompanyCode());
        Employee temp = Employee.builder()
                .employeeId(temp2)
                .address(employeeDTO.getAddress())
                .age(employeeDTO.getAge())
                .name(employeeDTO.getName()).build();

        service.addEmployees(temp);
        return "Record added successfully";
    }

    @PostMapping("/present")
    public String addIfNotPresent(@RequestParam("empId") Long empId, @RequestParam("companyCode") Long companyCode, @RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("address") String address){
        EmployeeId temp2 = new EmployeeId(empId, companyCode);
        if(service.searchEmployeeById(temp2))
        {
            return "Employee already present";
        }
        else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("empId", empId);
            map.add("companyCode", companyCode);
            map.add("name", name);
            map.add("age", age);
            map.add("address", address);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map , httpHeaders);
            RestTemplate restTemplate = new RestTemplate();

            String result = restTemplate.postForObject("http://localhost:8080/add",
                    requestEntity,
                    String.class);

            System.out.println(result);
            return "Employee added";
        }
    }

    @PostMapping("/update")
    public String updateIfPresent(@RequestParam("empId") Long empId,@RequestParam("companyCode") Long companyCode,@RequestParam("address") String address, @RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("updateName") Boolean updateName, @RequestParam("updateAge") Boolean updateAge, @RequestParam("updateAddress") Boolean updateAddress) {
        EmployeeId temp2 = new EmployeeId(empId, companyCode);
        Employee temp = Employee.builder()
                .employeeId(temp2)
                .address(address)
                .age(age)
                .name(name).build();
        if(service.searchEmployeeById(temp2))
        {
            return service.updateEmployee(temp2, temp, updateName, updateAddress, updateAge);
        }
        else {
            return "Employee not found";
        }
    }

    @PostMapping("/present1")
    public String addIfNotPresent(@RequestBody EmployeeDTO employeeDTO){
        EmployeeId temp2 = new EmployeeId(employeeDTO.getEmpId(), employeeDTO.getCompanyCode());
        if(service.searchEmployeeById(temp2))
        {
            return "Employee already present";
        }
        else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<EmployeeDTO> requestEntity = new HttpEntity<>(employeeDTO, httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject("http://localhost:8080/add2",
                    requestEntity,
                    String.class);

            System.out.println(result);
            return "Employee added";
        }
    }

    @DeleteMapping
    public String deleteEmployees(@RequestBody EmployeeDTO e) {
        EmployeeId temp = new EmployeeId();
        temp.setEmpId(e.getEmpId());
        temp.setCompanyCode(e.getCompanyCode());
        service.deleteEmployeesById(temp);
        return "Deleted Successfully";
    }
    @DeleteMapping("/{empId}/{companyCode}")
    public String deleteEmployees(@PathVariable("empId") Long empId, @PathVariable("companyCode") Long companyCode){
        EmployeeId temp = new EmployeeId(empId, companyCode);
        service.deleteEmployeesById(temp);
        return "Delete Successfully";
    }

}

