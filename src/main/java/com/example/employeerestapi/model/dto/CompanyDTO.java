package com.example.employeerestapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {

    private Long id;
    private String name;
    @JsonIgnoreProperties("company")
    private List<EmployeeDTO> employees;

}
