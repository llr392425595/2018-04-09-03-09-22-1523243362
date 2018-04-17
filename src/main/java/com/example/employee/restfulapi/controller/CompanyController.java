package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

  @Autowired
  private CompanyRepository companyRepository;

  @GetMapping("")
  public ResponseEntity<List> getAllCompanies() throws Exception {
    List<Company> companies = companyRepository.findAll();
    return new ResponseEntity<>(companies, HttpStatus.OK);
  }

  @GetMapping(value = "{/id}")
  public ResponseEntity getCompanyById(@PathVariable("id") Long id) throws Exception {
    Company company = companyRepository.findOne(id);
    return new ResponseEntity<>(company, HttpStatus.OK);
  }

  @PostMapping(value = "")
  public ResponseEntity addCompany(@RequestBody Company company) throws Exception {
    companyRepository.save(company);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping(value = "{/id}")
  public ResponseEntity updateCompany(@PathVariable("id") Long id, @RequestBody Company newCompany) {
    Company company = companyRepository.findOne(id);
    company.setCompanyName(newCompany.getCompanyName());
    company.setEmployeesNumber(newCompany.getEmployeesNumber());
    companyRepository.save(company);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/{id}/employees")
  public ResponseEntity getEmployeeByCompanyId(@PathVariable Long id) throws Exception {
    Company company = companyRepository.findOne(id);
    Set<Employee> employees = company.getEmployees();
    return new ResponseEntity<>(employees, HttpStatus.OK);
  }
}
