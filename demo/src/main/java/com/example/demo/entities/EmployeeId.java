package com.example.demo.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class EmployeeId {
    private Long empId;
    private Long companyCode;
}
