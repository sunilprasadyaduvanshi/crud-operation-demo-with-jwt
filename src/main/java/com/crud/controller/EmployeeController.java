package com.crud.controller;

import com.crud.entity.Employee;
import com.crud.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        log.info("Received request to create employee: {}", employee);
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.ok(createdEmployee);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createEmployees(@RequestBody List<Employee> employees) {
        log.info("Received request to create list of employees: {}", employees);
        List<Employee> createdEmployees = employeeService.createEmployees(employees);
        return ResponseEntity.ok(createdEmployees);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("Received request to get all employees");
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        log.info("Received request to get employee by ID: {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
