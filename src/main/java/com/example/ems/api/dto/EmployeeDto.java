package com.example.ems.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class EmployeeDto {

    private Long id;

    @NotBlank(message = "Employee name must not be blank")
    private String name;

    private String email;

    private String department;

    private Long contactDetails;

    private LocalDate joiningDate;

    public EmployeeDto(Long id, String name, String email, String department, Long contactDetails, LocalDate joiningDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.contactDetails = contactDetails;
        this.joiningDate = joiningDate;
    }

    public EmployeeDto() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(Long contactDetails) {
        this.contactDetails = contactDetails;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }
}
