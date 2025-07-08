package com.example.ems.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDto {

    @NotBlank(message = "Employee name must not be blank")
    private String employeeName;

    @Email
    private String email;

    private String department;

    private LocalDate joiningDate;
}
