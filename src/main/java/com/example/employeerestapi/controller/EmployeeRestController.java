package com.example.employeerestapi.controller;

import com.example.employeerestapi.model.dto.EmployeeDTO;
import com.example.employeerestapi.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeRestController {

    private final EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeRestController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ResponseBody
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("id/{id}")
    @ResponseBody
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("firstName/{firstName}/lastName/{lastName}")
    @ResponseBody
    public EmployeeDTO getEmployeeByName(@PathVariable String firstName, @PathVariable String lastName) {
        return employeeService.findEmployeeByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.addEmployee(employeeDTO);
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(employeeDTO);
    }

    @DeleteMapping("id/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

}
