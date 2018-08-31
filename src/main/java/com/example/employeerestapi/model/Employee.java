package com.example.employeerestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "employee_id_generator")
    @SequenceGenerator(name = "employee_id_generator", sequenceName = "employee_seq")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "data_of_birth")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private BigDecimal salary;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("employees")
    private Company company;

}
