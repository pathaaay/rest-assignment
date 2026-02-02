package com.rest.assignment.service;

import com.rest.assignment.ApiResponse;
import com.rest.assignment.dto.request.EmployeeRequestDTO;
import com.rest.assignment.dto.response.EmployeeResponseDTO;
import com.rest.assignment.entities.Employee;
import com.rest.assignment.mapper.EntityMapper;
import com.rest.assignment.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private EntityMapper entityMapper;

    public ApiResponse<List<EmployeeResponseDTO>> getEmployees() {
        List<Employee> data = employeeRepo.findAll();
        return ApiResponse.success(entityMapper.toEmployeeDTOList(data), "Employee Get Successfully");
    }

    public ApiResponse<EmployeeResponseDTO> getEmployeeById(UUID employeeId) {
        Optional<Employee> data = employeeRepo.findById(employeeId);
        return ApiResponse.success(entityMapper.toEmployeeDTO(data.orElse(null)), "Employee Get Successfully");
    }

    public ApiResponse<EmployeeResponseDTO> createEmployee(EmployeeRequestDTO employee) {
        Employee newEmployee = employeeRepo.save(entityMapper.dtoToEmployee(employee));
        return ApiResponse.success(entityMapper.toEmployeeDTO(newEmployee), "Employee Created successfully",201);
    }


    public ApiResponse<EmployeeResponseDTO> updateEmployee(EmployeeRequestDTO employee) {
        Employee updatedEmployee = employeeRepo.findById(employee.getId()).map(existingEmployee -> {
            existingEmployee.setName(employee.getName());
            existingEmployee.setEmail(employee.getEmail());

            return employeeRepo.save(existingEmployee);
        }).orElse(null);
        return ApiResponse.success(entityMapper.toEmployeeDTO(updatedEmployee), "Employee Get Successfully");
    }

    public ApiResponse<EmployeeResponseDTO> updateEmployeeName(UUID employeeId, String name) {
        Employee updatedEmployee = employeeRepo.findById(employeeId).map(existingEmployee -> {
            existingEmployee.setName(name);
            return employeeRepo.save(existingEmployee);
        }).orElse(null);
        return ApiResponse.success(entityMapper.toEmployeeDTO(updatedEmployee), "Employee name updated Successfully");
    }

    public ApiResponse<EmployeeResponseDTO> updateEmployeeSalary(UUID employeeId, int salary) {
        Employee updatedEmployee = employeeRepo.findById(employeeId).map(existingEmployee -> {
            existingEmployee.setSalary(salary);
            return employeeRepo.save(existingEmployee);
        }).orElse(null);
        return ApiResponse.success(entityMapper.toEmployeeDTO(updatedEmployee), "Employee salary updated Successfully");
    }

    public ApiResponse<EmployeeResponseDTO> deleteEmployeeName(UUID employeeId) {
        employeeRepo.deleteById(employeeId);
        return ApiResponse.success(entityMapper.toEmployeeDTO(null), "Employee Deleted Successfully");
    }

}
