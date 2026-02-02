package com.rest.assignment.service;

import com.rest.assignment.dto.request.EmployeeRequestDTO;
import com.rest.assignment.dto.response.EmployeeResponseDTO;
import com.rest.assignment.entities.Employee;
import com.rest.assignment.repository.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final ModelMapper modelMapper;

    @Autowired
    private EmployeeRepo employeeRepo;

    public EmployeeService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EmployeeResponseDTO convertToDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return modelMapper.map(employee, EmployeeResponseDTO.class);
    }

    public Employee convertToEntity(EmployeeRequestDTO employeeRequestDTO) {

        return modelMapper.map(employeeRequestDTO, Employee.class);
    }

    public List<EmployeeResponseDTO> convertListToDto(List<Employee> employees) {
        return employees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        List<Employee> data = employeeRepo.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(convertListToDto(data));
    }

    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(UUID employeeId) {
        Optional<Employee> data = employeeRepo.findById(employeeId);

        if (data.isPresent())
            return ResponseEntity.ok().body(convertToDto(data.orElse(null)));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<EmployeeResponseDTO> createEmployee(EmployeeRequestDTO employee) {
        Employee newEmployee = employeeRepo.save(convertToEntity(employee));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(newEmployee));
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
        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(updatedEmployee));
    }

    public ResponseEntity<EmployeeResponseDTO> updateEmployeeName(UUID employeeId, EmployeeRequestDTO employee) {
        Optional<Employee> oldEmployee = employeeRepo.findById(employeeId);
        if (oldEmployee.isEmpty()) ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Employee updatedEmployee = employeeRepo.findById(employeeId).map(existingEmployee -> {
            existingEmployee.setName(employee.getName());
            return employeeRepo.save(existingEmployee);
        }).orElse(null);


        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(updatedEmployee));
    }

    public ResponseEntity<EmployeeResponseDTO> updateEmployeeSalary(UUID employeeId, EmployeeRequestDTO employee) {

        Optional<Employee> oldEmployee = employeeRepo.findById(employeeId);

        if (oldEmployee.isEmpty()) ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);


        Employee updatedEmployee = employeeRepo.findById(employeeId).map(existingEmployee -> {
            existingEmployee.setSalary(employee.getSalary());
            return employeeRepo.save(existingEmployee);
        }).orElse(null);

        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(updatedEmployee));
    }

    public ResponseEntity<EmployeeResponseDTO> deleteEmployeeById(UUID employeeId) {

        Optional<Employee> existingEmployee = employeeRepo.findById(employeeId);

        if (existingEmployee.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        employeeRepo.deleteById(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(null));
    }

    public ResponseEntity<EmployeeResponseDTO> updateEmployeePhoto(UUID employeeId, String filePath) {
        System.out.println("filePath : " + filePath);
        Employee updatedEmployee = employeeRepo.findById(employeeId).map(existingEmployee -> {
            existingEmployee.setProfile_picture_path(filePath);
            return employeeRepo.save(existingEmployee);
        }).orElse(null);

        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(updatedEmployee));
    }

    public ResponseEntity<String> getEmployeeProfilePicture(UUID employeeId) {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        if (employee.isPresent()) {
            EmployeeResponseDTO dto = convertToDto(employee.orElse(null));
            if (dto.getProfile_picture_path().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No profile uploaded yet");
            } else return ResponseEntity.ok().body(dto.getProfile_picture_path());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee profile not found");
        }
    }
}
