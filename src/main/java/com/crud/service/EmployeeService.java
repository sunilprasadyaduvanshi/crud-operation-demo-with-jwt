package com.crud.service;

import com.crud.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    Employee createEmployee(Employee employee);
    List<Employee> createEmployees(List<Employee> employees);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Integer id);
    Employee updateEmployee(Integer id, Employee employee);
    void deleteEmployee(Integer id);
}
