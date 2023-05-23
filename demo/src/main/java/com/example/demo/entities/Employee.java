package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table
public class Employee {
    @EmbeddedId
    public EmployeeId employeeId;
    private String emiratesIdNo;
    private String name;
    private Integer age;
    private String address;
}
