package com.example.employeerestapi.service;

import com.example.employeerestapi.model.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {

    CompanyDTO addCompany(CompanyDTO companyDTO);
    CompanyDTO updateCompany(CompanyDTO companyDTO);
    CompanyDTO deleteCompany(Long id);
    List<CompanyDTO> findAllCompanies();
    CompanyDTO findCompanyById(Long id);
    CompanyDTO findCompanyByName(String name);

}
