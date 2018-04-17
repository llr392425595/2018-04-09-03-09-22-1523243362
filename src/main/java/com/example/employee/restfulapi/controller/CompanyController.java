package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.repository.CompanyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

  @Autowired
  private CompanyRepository companyRepository;

  @GetMapping("")
  public ResponseEntity<List> getAllCompanies() {
    List<Company> companies = companyRepository.findAll();
    return new ResponseEntity<>(companies, HttpStatus.OK);
  }



}
