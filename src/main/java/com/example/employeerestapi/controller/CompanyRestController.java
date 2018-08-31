package com.example.employeerestapi.controller;

import com.example.employeerestapi.model.dto.CompanyDTO;
import com.example.employeerestapi.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("company")
public class CompanyRestController {

    private final CompanyServiceImpl companyService;

    @Autowired
    public CompanyRestController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    @ResponseBody
    public List<CompanyDTO> getAllCompanies() {
        return companyService.findAllCompanies();
    }

    @GetMapping("id/{id}")
    @ResponseBody
    public CompanyDTO getCompanyById(@PathVariable Long id) {
        return companyService.findCompanyById(id);
    }

    @GetMapping("name/{name}")
    @ResponseBody
    public CompanyDTO getCompanyByName(@PathVariable String name) {
        return companyService.findCompanyByName(name);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO addCompany(@RequestBody CompanyDTO companyDTO) {
        return companyService.addCompany(companyDTO);
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CompanyDTO updateCompany(@RequestBody CompanyDTO companyDTO) {
        return companyService.updateCompany(companyDTO);
    }

    @DeleteMapping("id/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CompanyDTO deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompany(id);
    }

}
