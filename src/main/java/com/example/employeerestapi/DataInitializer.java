package com.example.employeerestapi;

import com.example.employeerestapi.model.dto.CompanyDTO;
import com.example.employeerestapi.model.dto.EmployeeDTO;
import com.example.employeerestapi.service.CompanyServiceImpl;
import com.example.employeerestapi.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EmployeeServiceImpl employeeService;
    private final CompanyServiceImpl companyService;

    @Autowired
    public DataInitializer(EmployeeServiceImpl employeeService, CompanyServiceImpl companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        CompanyDTO company1 = CompanyDTO
                .builder()
                .name("Company1")
                .build();

        CompanyDTO company2 = CompanyDTO
                .builder()
                .name("Company2")
                .build();

        EmployeeDTO employee1 = EmployeeDTO
                .builder()
                .firstName("FirstName1")
                .lastName("LastName1")
                .dateOfBirth(LocalDate.of(1990, 4, 25))
                .salary(new BigDecimal(10000.0))
                .company(company1)
                .build();

        EmployeeDTO employee2 = EmployeeDTO
                .builder()
                .firstName("FirstName2")
                .lastName("LastName2")
                .dateOfBirth(LocalDate.of(1885, 5, 20))
                .salary(new BigDecimal(15000.0))
                .company(company2)
                .build();

        EmployeeDTO employee3 = EmployeeDTO
                .builder()
                .firstName("FirstName3")
                .lastName("LastName3")
                .dateOfBirth(LocalDate.of(1995, 6, 15))
                .salary(new BigDecimal(5000.0))
                .company(company2)
                .build();

        companyService.addCompany(company1);
        companyService.addCompany(company2);

        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
    }

}
