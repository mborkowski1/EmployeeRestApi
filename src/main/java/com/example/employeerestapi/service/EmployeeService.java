package com.example.employeerestapi.service;

import com.example.employeerestapi.model.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO deleteEmployee(Long id);
    List<EmployeeDTO> findAllEmployees();
    EmployeeDTO findEmployeeById(Long id);
    EmployeeDTO findEmployeeByFirstNameAndLastName(String firstName, String lastName);

}
