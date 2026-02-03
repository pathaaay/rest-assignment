package com.rest.assignment.service;

import com.rest.assignment.entities.Employee;
import com.rest.assignment.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final JwtService jwtService;

    @Autowired
    private EmployeeRepo employeeRepo;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String login(String email, String password) {
        Employee employee = employeeRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (!password.equals(employee.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(employee.getId(), employee.getEmail());
    }


}
