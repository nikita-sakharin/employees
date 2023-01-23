package com.github.nikita.sakharin.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeHierarchy getEmployeeHierarchy() {
        return new EmployeeHierarchy(employeeRepository.findAll());
    }
}
