package com.example.demo.controllers;

import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeId;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.models.EmployeeDTO;
import com.example.demo.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequestMapping("/")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl service;

    @GetMapping("/")
    public List<Employee> getEmployee() {
        return service.getEmployees();
    }

    @GetMapping("/get")
    public Employee getEmployeesById(@RequestParam("empId") Long empId, @RequestParam("companyCode") Long companyCode){
        EmployeeId temp2 = new EmployeeId(empId, companyCode);
        Employee temp = service.getEmployeesById(temp2);
        temp.setEmiratesIdNo(service.decrypt(temp.getEmiratesIdNo()));
        return temp;
    }

    @GetMapping("/{empId}/{companyCode}")
    public Employee getEmployeesById1(@PathVariable("empId") Long empId, @PathVariable("companyCode") Long companyCode){
        EmployeeId temp2 = new EmployeeId(empId, companyCode);
        Employee temp = service.getEmployeesById(temp2);
        temp.setEmiratesIdNo(service.decrypt(temp.getEmiratesIdNo()));
        return temp;
    }

    @PostMapping("/add")
    public void addEmployee(@RequestParam("empId") Long empId, @RequestParam("companyCode") Long companyCode, @RequestParam("emiratesIdNo") String emiratesIdNo, @RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("address") String address) {
        String encryptedEmiratesIdNo = service.encrypt(emiratesIdNo);
        EmployeeId temp2 = new EmployeeId(empId, companyCode);
        Employee temp = Employee.builder()
                .employeeId(temp2)
                .emiratesIdNo(encryptedEmiratesIdNo)
                .address(address)
                .age(age)
                .name(name).build();

        service.addEmployees(temp);
        throw new ApiRequestException("Employee added");
    }

    @PostMapping("/add2")
    public void addEmployee2(@RequestBody EmployeeDTO employeeDTO){
        String encryptedEmiratesIdNo = service.encrypt(employeeDTO.getEmiratesIdNo());
        EmployeeId temp2 = new EmployeeId(employeeDTO.getEmpId(), employeeDTO.getCompanyCode());
        Employee temp = Employee.builder()
                .employeeId(temp2)
                .emiratesIdNo(encryptedEmiratesIdNo)
                .address(employeeDTO.getAddress())
                .age(employeeDTO.getAge())
                .name(employeeDTO.getName()).build();

        service.addEmployees(temp);
        throw new ApiRequestException("Employee added");
    }

    @PostMapping("/present")
    public void addIfNotPresent(@RequestParam("empId") Long empId, @RequestParam("companyCode") Long companyCode, @RequestParam("emiratesIdNo") String emiratesIdNo, @RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("address") String address){
        EmployeeId temp2 = new EmployeeId(empId, companyCode);
        if(service.searchEmployeeById(temp2))
        {
            throw new ApiRequestException("Employee already exists");
        }
        else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("empId", empId);
            map.add("companyCode", companyCode);
            map.add("emiratesIdNo", service.encrypt(emiratesIdNo));
            map.add("name", name);
            map.add("age", age);
            map.add("address", address);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map , httpHeaders);
            RestTemplate restTemplate = new RestTemplate();

            String result = restTemplate.postForObject("http://localhost:8080/swagger-ui/index.html/add",
                    requestEntity,
                    String.class);

            System.out.println(result);
            throw new ApiRequestException("Employee added");
        }
    }

    @PutMapping("/update")
    public void updateIfPresent(@RequestParam("empId") Long empId, @RequestParam("companyCode") Long companyCode, @RequestParam("emiratesIdNo") String emiratesIdNo, @RequestParam("address") String address, @RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam("updateEmiratesIdNo") Boolean updateEmiratesIdNo, @RequestParam("updateName") Boolean updateName, @RequestParam("updateAge") Boolean updateAge, @RequestParam("updateAddress") Boolean updateAddress) {
        EmployeeId temp2 = new EmployeeId(empId, companyCode);
        Employee temp = Employee.builder()
                .employeeId(temp2)
                .emiratesIdNo(service.encrypt(emiratesIdNo))
                .address(address)
                .age(age)
                .name(name).build();
        if(service.searchEmployeeById(temp2)) {
            service.updateEmployee(temp2, temp, updateEmiratesIdNo, updateName, updateAddress, updateAge);
            throw new ApiRequestException("Employee updated");
        } else {
            throw new ApiRequestException("Employee not found");
        }
    }

    @PostMapping("/present1")
    public void addIfNotPresent(@RequestBody EmployeeDTO employeeDTO){
        EmployeeId temp2 = new EmployeeId(employeeDTO.getEmpId(), employeeDTO.getCompanyCode());
        if(service.searchEmployeeById(temp2))
        {
            throw new ApiRequestException("Employee already exists");
        }
        else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<EmployeeDTO> requestEntity = new HttpEntity<>(employeeDTO, httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject("http://localhost:8080/swagger-ui/index.html/add2",
                    requestEntity,
                    String.class);
            throw new ApiRequestException("Employee added");
        }
    }

    @DeleteMapping("/delete")
    public void deleteEmployees(@RequestBody EmployeeDTO e) {
        EmployeeId temp = new EmployeeId();
        temp.setEmpId(e.getEmpId());
        temp.setCompanyCode(e.getCompanyCode());
        service.deleteEmployeesById(temp);
        throw new ApiRequestException("Employee deleted");
    }

    @DeleteMapping("/delete2")
    public void deleteEmployees(@RequestParam("empId") Long empId, @RequestParam("companyCode") Long companyCode){
        EmployeeId temp = new EmployeeId(empId, companyCode);
        service.deleteEmployeesById(temp);
        throw new ApiRequestException("Employee deleted");
    }

    @DeleteMapping("/{empId}/{companyCode}")
    public void deleteEmployees2(@PathVariable("empId") Long empId, @PathVariable("companyCode") Long companyCode) {
        EmployeeId temp = new EmployeeId(empId, companyCode);
        service.deleteEmployeesById(temp);
        throw new ApiRequestException("Employee deleted");
    }

}

