package com.example.employeerestapi.service;

import com.example.employeerestapi.model.Company;
import com.example.employeerestapi.model.Employee;
import com.example.employeerestapi.model.dto.CompanyDTO;
import com.example.employeerestapi.repository.CompanyRepository;
import com.example.employeerestapi.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CompanyDTO addCompany(CompanyDTO companyDTO) {
        if (companyDTO == null) {
            throw new NullPointerException("Company Is Null");
        }

        Company company = modelMapper.map(companyDTO, Company.class);
        company.setId(null);

        if (companyRepository.findCompanyByName(company.getName()) != null)
            throw new EntityExistsException("Company Already Exists");

        company.setEmployees(null);
        Company companyCreated = companyRepository.save(company);
        return modelMapper.map(companyCreated, CompanyDTO.class);
    }

    @Override
    public CompanyDTO updateCompany(CompanyDTO companyDTO) {
        if (companyDTO == null) {
            throw new NullPointerException("Company Is Null");
        }

        Company company = modelMapper.map(companyDTO, Company.class);

        if (company.getId() == null)
            throw new NullPointerException("Id Is Null");

        Optional<Company> companyOptional = companyRepository.findById(company.getId());

        if (!companyOptional.isPresent())
            throw new EntityNotFoundException("Cannot Find Company With Id " + company.getId());

        if (companyOptional.get().getEmployees().isEmpty())
            company.setEmployees(null);
        else {
            if (company.getEmployees() == null || company.getEmployees().isEmpty()) {
                List<Employee> employees = new ArrayList<>();

                for (Employee employee : companyOptional.get().getEmployees()) {
                    Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
                    employeeOptional.ifPresent(employees::add);
                }

                employees.forEach(employee -> employee.setCompany(company));
                employees.forEach(employeeRepository::save);
            }
            else if (!company.getEmployees().isEmpty()) {
                List<Employee> employees = new ArrayList<>();

                for (Employee employee : company.getEmployees()) {
                    Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
                    if (employeeOptional.isPresent())
                        employees.add(employee);
                    else
                        throw new EntityNotFoundException("Cannot Find Employee With Id " + employee.getId());
                }

                employees.forEach(employee -> employee.setCompany(company));
                employees.forEach(employeeRepository::save);
            }
        }

        Company companyUpdated = companyRepository.save(company);
        return modelMapper.map(companyUpdated, CompanyDTO.class);
    }

    @Override
    public CompanyDTO deleteCompany(Long id) {
        if (id == null)
            throw new NullPointerException("Id Is Null");

        Optional<Company> companyOptional = companyRepository.findById(id);

        if (!companyOptional.isPresent())
            throw new EntityNotFoundException("Cannot Find Company With Id " + id);

        Company company = companyOptional.get();

        if (!company.getEmployees().isEmpty()) {
            List<Employee> employees = company.getEmployees();
            employees.forEach(employee -> employee.setCompany(null));
            employeeRepository.saveAll(employees);
        }

        companyRepository.delete(company);
        return modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    public List<CompanyDTO> findAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        if (companies.isEmpty())
            throw new NoResultException("Cannot Find Any Company");

        return companies
                .stream()
                .map(company -> modelMapper.map(company, CompanyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO findCompanyById(Long id) {
        if (id == null)
            throw new NullPointerException("Id Is Null");

        Optional<Company> companyOptional = companyRepository.findById(id);

        if (!companyOptional.isPresent())
            throw new EntityNotFoundException("Cannot Find Company With Id " + id);

        Company company = companyOptional.get();

        return modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    public CompanyDTO findCompanyByName(String name) {
        if (name == null)
            throw new NullPointerException("Name Is Null");

        Company company = companyRepository.findCompanyByName(name);

        if (company == null)
            throw new EntityNotFoundException("Cannot Find Company With Name " + name);

        return modelMapper.map(company, CompanyDTO.class);
    }

}
