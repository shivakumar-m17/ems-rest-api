package com.example.ems.api.controller;

import com.example.ems.api.dto.EmployeeDto;
import com.example.ems.api.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody EmployeeDto dto) {
        EmployeeDto created = employeeService.createEmployee(dto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee created successfully");
        response.put("employee", created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
        return employeeService.getAllEmployees(pageable);
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto dto) {
        EmployeeDto updated = employeeService.updateEmployee(id, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee updated successfully");
        response.put("employee", updated);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee deleted successfully");
        response.put("id", id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteAllEmployees() {
        employeeService.deleteAllEmployees();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "All employees deleted successfully");

        return ResponseEntity.ok(response);
    }
}
