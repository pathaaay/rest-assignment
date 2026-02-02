package com.rest.assignment.mapper;


import com.rest.assignment.dto.request.EmployeeRequestDTO;
import com.rest.assignment.dto.response.EmployeeResponseDTO;
import com.rest.assignment.entities.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    public EmployeeResponseDTO toEmployeeDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setDepartment(employee.getDepartment());
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());
        return employeeDTO;
    }

    public Employee dtoToEmployee(EmployeeRequestDTO employeeDTO) {
        if (employeeDTO == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPassword(employeeDTO.getPassword());
        return employee;
    }

    public List<EmployeeResponseDTO> toEmployeeDTOList(List<Employee> libraries) {
        return libraries.stream()
                .map(this::toEmployeeDTO)
                .collect(Collectors.toList());
    }
}
