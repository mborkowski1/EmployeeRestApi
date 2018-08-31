package com.example.employeerestapi.repository;


import com.example.employeerestapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findEmployeeByFirstNameAndLastName(String firstName, String lastName);

}
