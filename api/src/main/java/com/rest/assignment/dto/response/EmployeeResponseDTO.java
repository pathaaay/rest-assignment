package com.rest.assignment.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class EmployeeResponseDTO {
    private UUID id;
    private String name;
    private String department;
    private int salary;
    private String email;
    private String profile_picture_path;
}
