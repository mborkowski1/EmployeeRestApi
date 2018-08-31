package com.example.employeerestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "company_id_generator")
    @SequenceGenerator(name = "company_id_generator", sequenceName = "company_seq")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("company")
    private List<Employee> employees;

}
