package com.rest.assignment.controller;


import com.rest.assignment.ApiResponse;
import com.rest.assignment.dto.request.EmployeeRequestDTO;
import com.rest.assignment.dto.response.EmployeeResponseDTO;
import com.rest.assignment.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping()
    public ApiResponse<List<EmployeeResponseDTO>> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("/{employeeId}")
    public ApiResponse<EmployeeResponseDTO> getEmployeeById(@PathVariable UUID employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping()
    public ApiResponse<EmployeeResponseDTO> createEmployee(@RequestBody @Valid EmployeeRequestDTO employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping()
    public ApiResponse<EmployeeResponseDTO> updateEmployee(@RequestBody EmployeeRequestDTO employee){
        return employeeService.updateEmployee(employee);
    }

    @PatchMapping("/{employeeId}/update-name")
    public ApiResponse<EmployeeResponseDTO> updateEmployeeName(@PathVariable UUID employeeId,@RequestBody String name){
        return employeeService.updateEmployeeName(employeeId,name);
    }

    @PatchMapping("/{employeeId}/update-salary")
    public ApiResponse<EmployeeResponseDTO> updateEmployeeSalary(@PathVariable UUID employeeId,@RequestBody int salary){
        return employeeService.updateEmployeeSalary(employeeId,salary);
    }

    @DeleteMapping("/{employeeId}")
    public ApiResponse<EmployeeResponseDTO> deleteEmployeeName(@PathVariable UUID employeeId,@RequestBody String name){
        return employeeService.deleteEmployeeName(employeeId);
    }

}
