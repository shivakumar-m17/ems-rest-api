package com.example.ems.api.service;

import com.example.ems.api.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);
    Page<EmployeeDto> getAllEmployees(Pageable pageable);
    EmployeeDto getEmployeeById(Long id);
    //EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    void deleteEmployee(Long id);
}
