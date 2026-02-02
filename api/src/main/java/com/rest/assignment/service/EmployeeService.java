package com.rest.assignment.service;

import com.rest.assignment.dto.request.EmployeeRequestDTO;
import com.rest.assignment.dto.response.EmployeeResponseDTO;
import com.rest.assignment.entities.Employee;
import com.rest.assignment.mapper.EntityMapper;
import com.rest.assignment.repository.EmployeeRepo;
import com.rest.assignment.validation_groups.UpdateEmployee;
import com.rest.assignment.validation_groups.UpdateEmployeeName;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private EntityMapper entityMapper;

    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        List<Employee> data = employeeRepo.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(entityMapper.toEmployeeDTOList(data));
    }

    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(UUID employeeId) {
        Optional<Employee> data = employeeRepo.findById(employeeId);

        if (data.isPresent())
            return ResponseEntity.ok().body(entityMapper.toEmployeeDTO(data.orElse(null)));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<EmployeeResponseDTO> createEmployee(EmployeeRequestDTO employee) {
        Employee newEmployee = employeeRepo.save(entityMapper.dtoToEmployee(employee));
        return ResponseEntity.status(HttpStatus.CREATED).body(entityMapper.toEmployeeDTO(newEmployee));
    }


    public ResponseEntity<EmployeeResponseDTO> updateEmployee(UUID employeeId, EmployeeRequestDTO employee) {
        Optional<Employee> oldEmployee = employeeRepo.findById(employeeId);

        if (oldEmployee.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        Employee updatedEmployee = employeeRepo.findById(employeeId).map(existingEmployee -> {
            existingEmployee.setName(employee.getName());
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setDepartment(employee.getDepartment());
            return employeeRepo.save(existingEmployee);
        }).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(entityMapper.toEmployeeDTO(updatedEmployee));
    }

    public ResponseEntity<EmployeeResponseDTO> updateEmployeeName(UUID employeeId, EmployeeRequestDTO employee) {
        Optional<Employee> oldEmployee = employeeRepo.findById(employeeId);
        if (oldEmployee.isEmpty()) ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Employee updatedEmployee = employeeRepo.findById(employeeId).map(existingEmployee -> {
            existingEmployee.setName(employee.getName());
            return employeeRepo.save(existingEmployee);
        }).orElse(null);


        return ResponseEntity.status(HttpStatus.OK).body(entityMapper.toEmployeeDTO(updatedEmployee));
    }

    public ResponseEntity<EmployeeResponseDTO> updateEmployeeSalary(UUID employeeId, int salary) {
        if (salary <= 0) throw new IllegalArgumentException("Salary must be greater than 0");

        Optional<Employee> oldEmployee = employeeRepo.findById(employeeId);

        if (oldEmployee.isEmpty()) ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);


        Employee updatedEmployee = employeeRepo.findById(employeeId).map(existingEmployee -> {
            existingEmployee.setSalary(salary);
            return employeeRepo.save(existingEmployee);
        }).orElse(null);

        return ResponseEntity.status(HttpStatus.OK).body(entityMapper.toEmployeeDTO(updatedEmployee));
    }

    public ResponseEntity<EmployeeResponseDTO> deleteEmployeeById(UUID employeeId) {

        Optional<Employee> existingEmployee = employeeRepo.findById(employeeId);

        if (existingEmployee.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        employeeRepo.deleteById(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(entityMapper.toEmployeeDTO(null));
    }

}
