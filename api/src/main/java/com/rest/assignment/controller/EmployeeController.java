package com.rest.assignment.controller;


import com.rest.assignment.dto.request.EmployeeRequestDTO;
import com.rest.assignment.dto.response.EmployeeResponseDTO;
import com.rest.assignment.service.EmployeeService;
import com.rest.assignment.validation_groups.CreateEmployee;
import com.rest.assignment.validation_groups.UpdateEmployee;
import com.rest.assignment.validation_groups.UpdateEmployeeName;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping()
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable UUID employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping()
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Validated(CreateEmployee.class) @RequestBody EmployeeRequestDTO employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable UUID employeeId, @Validated(UpdateEmployee.class) @RequestBody EmployeeRequestDTO employee){
        return employeeService.updateEmployee(employeeId,employee);
    }

    @PatchMapping("/{employeeId}/update-name")
    public ResponseEntity<EmployeeResponseDTO> updateEmployeeName(@PathVariable UUID employeeId,@Validated(UpdateEmployeeName.class) @RequestBody EmployeeRequestDTO name){
        return employeeService.updateEmployeeName(employeeId,name);
    }

    @PatchMapping("/{employeeId}/update-salary")
    public ResponseEntity<EmployeeResponseDTO> updateEmployeeSalary(@PathVariable UUID employeeId,@RequestBody int salary){
        return employeeService.updateEmployeeSalary(employeeId,salary);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> deleteEmployeeById(@PathVariable UUID employeeId){
        return employeeService.deleteEmployeeById(employeeId);
    }
}
