package com.rest.assignment.controller;


import com.rest.assignment.dto.request.EmployeeRequestDTO;
import com.rest.assignment.dto.response.EmployeeResponseDTO;
import com.rest.assignment.service.EmployeeService;
import com.rest.assignment.validation_groups.CreateEmployee;
import com.rest.assignment.validation_groups.UpdateEmployee;
import com.rest.assignment.validation_groups.UpdateEmployeeName;
import com.rest.assignment.validation_groups.UpdateEmployeeSalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable UUID employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping()
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Validated(CreateEmployee.class) @RequestBody EmployeeRequestDTO employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable UUID employeeId, @Validated(UpdateEmployee.class) @RequestBody EmployeeRequestDTO employee) {
        return employeeService.updateEmployee(employeeId, employee);
    }

    @PatchMapping("/{employeeId}/update-name")
    public ResponseEntity<EmployeeResponseDTO> updateEmployeeName(@PathVariable UUID employeeId, @Validated(UpdateEmployeeName.class) @RequestBody EmployeeRequestDTO name) {
        return employeeService.updateEmployeeName(employeeId, name);
    }

    @PatchMapping("/{employeeId}/update-salary")
    public ResponseEntity<EmployeeResponseDTO> updateEmployeeSalary(@PathVariable UUID employeeId, @Validated(UpdateEmployeeSalary.class) @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return employeeService.updateEmployeeSalary(employeeId, employeeRequestDTO);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> deleteEmployeeById(@PathVariable UUID employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }

    @PostMapping("/{employeeId}/profile-picture")
    public ResponseEntity<EmployeeResponseDTO> uploadProfilePic(@PathVariable UUID employeeId, @RequestParam("file") MultipartFile file) {

        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = employeeId + "-" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return employeeService.updateEmployeePhoto(employeeId, fileName);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{employeeId}/profile-picture")
    public ResponseEntity<String> getEmployeeProfilePicture(@PathVariable UUID employeeId){
        return employeeService.getEmployeeProfilePicture(employeeId);
    }
}
