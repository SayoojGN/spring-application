package com.example.demo.service.impl;

import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeId;
import com.example.demo.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String SECRET_KEY
            = "my_super_secret_key_ho_ho_ho";

    private static final String SALT = "ssshhhhhhhhhhh!!!!";

    // This method use to encrypt to string
    @Override
    public String encrypt(String strToEncrypt)
    {
        try {

            // Create default byte array
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec
                    = new IvParameterSpec(iv);

            // Create SecretKeyFactory object
            SecretKeyFactory factory
                    = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");

            // Create KeySpec object and assign with
            // constructor
            KeySpec spec = new PBEKeySpec(
                    SECRET_KEY.toCharArray(), SALT.getBytes(),
                    65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(
                    tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(
                    "AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,
                    ivspec);
            // Return encrypted string
            return Base64.getEncoder().encodeToString(
                    cipher.doFinal(strToEncrypt.getBytes(
                            StandardCharsets.UTF_8)));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: "
                    + e.toString());
        }
        return null;
    }

    @Override
    public String decrypt(String strToDecrypt)
    {
        try {

            // Default byte array
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0 };
            // Create IvParameterSpec object and assign with
            // constructor
            IvParameterSpec ivspec
                    = new IvParameterSpec(iv);

            // Create SecretKeyFactory Object
            SecretKeyFactory factory
                    = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");

            // Create KeySpec object and assign with
            // constructor
            KeySpec spec = new PBEKeySpec(
                    SECRET_KEY.toCharArray(), SALT.getBytes(),
                    65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(
                    tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(
                    "AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey,
                    ivspec);
            // Return decrypted string
            return new String(cipher.doFinal(
                    Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: "
                    + e.toString());
        }
        return null;
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Cacheable(value = "employeeInfo")
    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    @Override
    @CachePut(value = "employeeInfo")
    public Employee getEmployeesById(EmployeeId employeeId) {
        return employeeRepository.findById(employeeId).get();
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
    public void updateEmployee(EmployeeId employeeId, Employee newEmployee, boolean updateEmiratesIdNo, boolean updateName, boolean updateAddress, boolean updateAge) {
        Employee selected = employeeRepository.getReferenceById(employeeId);
        employeeRepository.delete(selected);
        Employee tempEmployee = new Employee();
        tempEmployee.setEmployeeId(employeeId);
        if(updateEmiratesIdNo){
            tempEmployee.setEmiratesIdNo(newEmployee.getEmiratesIdNo());
        }
        else {
            tempEmployee.setEmiratesIdNo(selected.getEmiratesIdNo());
        }
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
    }

}
