package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @GetMapping("/{id}")
  public ResponseEntity<?> getCompanyById(@PathVariable("id") Long id) throws Exception {
    Company company = companyRepository.findOne(id);
    return new ResponseEntity<>(company, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity addCompany(@RequestBody Company company) throws Exception {
    companyRepository.save(company);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteCompany(@PathVariable("id") Long id) {
    if (companyRepository.exists(id)) {
      companyRepository.delete(id);
      return new ResponseEntity<>(companyRepository.findOne(id), HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
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

  @GetMapping(value = "/page/{page}/pageSize/{pageSize}")
  public ResponseEntity getCompanyByPage(@PathVariable int page, @PathVariable int pageSize) throws Exception {
    Pageable pageable = new PageRequest(page-1, pageSize);
    Page companies = companyRepository.findAll(pageable);
    return new ResponseEntity<>(companies, HttpStatus.OK);
  }
}
