package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

  @Autowired
  private EmployeeRepository employeeRepository;


  //  查询所有员工
  @GetMapping(value = "")
  public ResponseEntity<List> getAllEmployees() throws Exception {
    List<Employee> employeesList = employeeRepository.findAll();
    return new ResponseEntity<>(employeesList, HttpStatus.OK);
  }

  //  获取某个具体employee
  @GetMapping(value = "/{id}")
  public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) throws Exception {
    Employee employee = employeeRepository.findOne(id);
    return new ResponseEntity<>(employee, HttpStatus.OK);
  }

  //  新增一个员工
  @PostMapping(value = "")
  public ResponseEntity<Map<String, String>> addEmployee(
      @RequestParam("name") String name,
      @RequestParam("age") Integer age,
      @RequestParam("gender") String gender,
      @RequestParam("salary") Integer salary,
      @RequestParam("companyId") Long companyId) throws Exception {
    Employee employee = new Employee(name, age, gender, salary, companyId);
    employee = employeeRepository.save(employee);

    Map<String, String> body = new HashMap<>();
    body.put("uri", "/api/employees/" + employee.getId());
    return new ResponseEntity<>(body, HttpStatus.CREATED);
  }

  //  删除某个employee
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) throws Exception {
    if (employeeRepository.exists(id)) {
      employeeRepository.delete(id);
      return new ResponseEntity<>(employeeRepository.findOne(id), HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  //  更新某个employee
  @PutMapping(value = "/{id}")
  public ResponseEntity updateEmployee(@PathVariable Long id, @RequestBody Employee newEmployee) throws Exception {
    Employee employee = employeeRepository.findOne(id);
    employee.setAge(newEmployee.getAge());
    employee.setGender(newEmployee.getGender());
    employee.setName(newEmployee.getName());
    employee.setSalary(newEmployee.getSalary());
    employee.setCompanyId(newEmployee.getCompanyId());
    employeeRepository.save(employee);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  //  筛选出所有男性Employee
  @GetMapping(value = "/male")
  public ResponseEntity getEmployeeByGender() throws Exception {
    List<Employee> employeeList = employeeRepository.findByGender("male");
    return new ResponseEntity<>(employeeList, HttpStatus.OK);
  }

  //  分页查询，page等于1，pageSize等于5
  @GetMapping(value = "/page/{page}/pageSize/{pageSize}")
  public ResponseEntity getEmployeePage(@PathVariable int page, @PathVariable int pageSize) throws Exception {
    Pageable pageable = new PageRequest(page-1, pageSize);
    Page employees = employeeRepository.findAll(pageable);
    return new ResponseEntity<>(employees, HttpStatus.OK);
  }

}
