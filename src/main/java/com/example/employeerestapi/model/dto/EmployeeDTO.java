package com.example.employeerestapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    private Long id;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private BigDecimal salary;
    @JsonIgnoreProperties("employees")
    private CompanyDTO company;

}
