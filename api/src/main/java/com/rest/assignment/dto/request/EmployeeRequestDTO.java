package com.rest.assignment.dto.request;

import com.rest.assignment.validation_groups.CreateEmployee;
import com.rest.assignment.validation_groups.UpdateEmployee;
import com.rest.assignment.validation_groups.UpdateEmployeeName;
import com.rest.assignment.validation_groups.UpdateEmployeeSalary;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class EmployeeRequestDTO {
    private UUID id;

    @Email(message = "Invalid email", groups = {CreateEmployee.class})
    @NotBlank(message = "Email is required", groups = {CreateEmployee.class})
    private String email;

    @NotBlank(message = "Password is required", groups = {CreateEmployee.class})
    @Size(min = 8, message = "Password must be at least 8 characters long", groups = {CreateEmployee.class})
    private String password;

    @NotBlank(message = "Name is required", groups = {CreateEmployee.class, UpdateEmployee.class, UpdateEmployeeName.class})
    private String name;

    @NotBlank(message = "Department is required", groups = {CreateEmployee.class, UpdateEmployee.class})
    private String department;

    @NotBlank(message = "Department is required", groups = {CreateEmployee.class, UpdateEmployeeSalary.class})
    @Min(value = 1000,message = "Minimum salary should be 1000", groups = {CreateEmployee.class, UpdateEmployee.class, UpdateEmployeeSalary.class})
    private int salary;


}
