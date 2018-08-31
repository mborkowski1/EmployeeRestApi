package com.example.employeerestapi.repository;

import com.example.employeerestapi.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findCompanyByName(String name);

}
