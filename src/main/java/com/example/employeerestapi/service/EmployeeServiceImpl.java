package com.example.employeerestapi.service;

import com.example.employeerestapi.model.Company;
import com.example.employeerestapi.model.Employee;
import com.example.employeerestapi.model.dto.EmployeeDTO;
import com.example.employeerestapi.repository.CompanyRepository;
import com.example.employeerestapi.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            throw new NullPointerException("Employee Is Null");
        }

        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setId(null);

        if (employeeRepository.findEmployeeByFirstNameAndLastName(employee.getFirstName(), employee.getLastName()) != null)
            throw new EntityExistsException("Employee Already Exists");

        if (employee.getCompany() != null) {
            Company company = companyRepository.findCompanyByName(employee.getCompany().getName());
            if (company != null)
                employee.setCompany(company);
            else
                throw new EntityNotFoundException("Cannot Find Company With Name " + employee.getCompany().getName());
        }

        Employee employeeCreated = employeeRepository.save(employee);
        return modelMapper.map(employeeCreated, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            throw new NullPointerException("Employee Is Null");
        }

        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        if (employee.getId() == null)
            throw new NullPointerException("Id Is Null");

        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        if (!employeeOptional.isPresent())
            throw new EntityNotFoundException("Cannot Find Employee With Id " + employee.getId());

        if (employee.getCompany() != null) {
            if (employee.getCompany().getId() != null && employee.getCompany().getName() != null) {
                Optional<Company> companyOptional = companyRepository.findById(employee.getCompany().getId());
                Company company = companyRepository.findCompanyByName(employee.getCompany().getName());

                if (!companyOptional.isPresent())
                    throw new EntityNotFoundException("Cannot Find Company With Id " + employee.getCompany().getId());

                if (company == null)
                    throw new EntityNotFoundException("Cannot Find Company With Name " + employee.getCompany().getName());

                if (!Objects.equals(employee.getCompany().getId(), company.getId()) || !Objects.equals(employee.getCompany().getName(), company.getName()))
                    throw new EntityNotFoundException("Cannot Find Company With Id " + employee.getCompany().getId() + " And Name " + employee.getCompany().getName());

                employee.setCompany(company);
            }
            else if (employee.getCompany().getId() != null) {
                Optional<Company> companyOptional = companyRepository.findById(employee.getCompany().getId());

                if (!companyOptional.isPresent())
                    throw new EntityNotFoundException("Cannot Find Company With Id " + employee.getCompany().getId());

                employee.setCompany(companyOptional.get());
            }
            else if (employee.getCompany().getName() != null) {
                Company company = companyRepository.findCompanyByName(employee.getCompany().getName());

                if (company == null)
                    throw new EntityNotFoundException("Cannot Find Company With Name " + employee.getCompany().getName());

                employee.setCompany(company);
            }
        }

        Employee employeeUpdated = employeeRepository.save(employee);
        return modelMapper.map(employeeUpdated, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO deleteEmployee(Long id) {
        if (id == null)
            throw new NullPointerException("Id Is Null");

        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (!employeeOptional.isPresent())
            throw new EntityNotFoundException("Cannot Find Employee With Id " + id);

        Employee employee = employeeOptional.get();

        if (employee.getCompany() != null) {
            Company company = companyRepository.findCompanyByName(employee.getCompany().getName());
            company.getEmployees().remove(employee);
            companyRepository.save(company);
        }

        employeeRepository.delete(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty())
            throw new NoResultException("Cannot Find Any Employee");

        return employees
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO findEmployeeById(Long id) {
        if (id == null)
            throw new NullPointerException("Id Is Null");

        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (!employeeOptional.isPresent())
            throw new EntityNotFoundException("Cannot Find Employee With Id " + id);

        Employee employee = employeeOptional.get();
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO findEmployeeByFirstNameAndLastName(String firstName, String lastName) {
        if (firstName == null || lastName == null)
            throw new NullPointerException("FirstName And LastName Cannot Be Null");

        Employee employee = employeeRepository.findEmployeeByFirstNameAndLastName(firstName, lastName);

        if (employee == null)
            throw new EntityNotFoundException("Cannot Find Employee With FirstName " + firstName + " And LastName " + lastName);

        return modelMapper.map(employee, EmployeeDTO.class);
    }

}
