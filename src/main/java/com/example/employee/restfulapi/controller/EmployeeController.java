package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
  @Autowired
  private EmployeeRepository employeeRepository;


  //  查询所有员工
  @GetMapping(value = "")
  public ResponseEntity<List> getAllEmployees() {
    List<Employee> employeesList = employeeRepository.findAll();
    return new ResponseEntity<>(employeesList, HttpStatus.OK);
  }

  //  新增一个员工
  @PostMapping(value = "")
  public ResponseEntity<Map<String, String>> addEmployee(
      @RequestParam("name") String name,
      @RequestParam("age") Integer age,
      @RequestParam("gender") String gender,
      @RequestParam("salary") Integer salary,
      @RequestParam("companyId") Long companyId) {
    Employee employee = new Employee();
    employee.setName(name);
    employee.setAge(age);
    employee.setGender(gender);
    employee.setSalary(salary);
    employee.setCompanyId(companyId);
    employee = employeeRepository.save(employee);

    Map<String, String> body = new HashMap<>();

    body.put("uri", "/api/employees/" + employee.getId());
    return new ResponseEntity<>(body, HttpStatus.CREATED);
  }
}
