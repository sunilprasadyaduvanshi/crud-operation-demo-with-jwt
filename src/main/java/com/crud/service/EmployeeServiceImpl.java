package com.crud.service;

import com.crud.entity.Employee;
import com.crud.repo.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        log.info("Creating employee: {}", employee);
        if (employee.getPassword() != null && !employee.getPassword().isBlank()) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> createEmployees(List<Employee> employees) {
        log.info("Creating employees: {}", employees);
        employees.forEach(employee -> {
            if (employee.getPassword() != null && !employee.getPassword().isBlank()) {
                employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            }
        });
        return employeeRepository.saveAll(employees);
    }

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Retrieving all employees");
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        log.info("Retrieving employee by ID: {}", id);
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        log.info("Updating employee with ID: {}", id);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        log.info("Deleting employee with ID: {}", id);
        employeeRepository.deleteById(id);
    }
}
