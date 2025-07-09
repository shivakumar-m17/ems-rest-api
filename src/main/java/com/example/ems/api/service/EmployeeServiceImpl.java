package com.example.ems.api.service;

import com.example.ems.api.dto.EmployeeDto;
import com.example.ems.api.exception.ResourceNotFoundException;
import com.example.ems.api.model.Employee;
import com.example.ems.api.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper = new ModelMapper();

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto dto) {
        Employee entity = mapper.map(dto, Employee.class);
        Employee savedEntity = employeeRepository.save(entity);

        logger.info("[EMS] EMPLOYEE_CREATE: id={} | name={} | status=SUCCESS",
                savedEntity.getId(), savedEntity.getName());

        return mapper.map(savedEntity, EmployeeDto.class);
    }

    @Override
    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employee -> mapper.map(employee, EmployeeDto.class));
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Employee not found"));
        return mapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("[EMS] EMPLOYEE_UPDATE_FAILED: id={} | reason=NOT_FOUND", id);
                    return new ResourceNotFoundException("Employee not found");
                });

        // Check if the email exists and belongs to another employee
        employeeRepository.findByEmail(dto.getEmail())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new IllegalArgumentException("Email already used by another employee.");
                    }
                });
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setContactDetails(dto.getContactDetails());
        employee.setJoiningDate(dto.getJoiningDate());

        Employee updated = employeeRepository.save(employee);

        logger.info("[EMS] EMPLOYEE_UPDATE: id={} | status=SUCCESS", id);

        return mapper.map(updated, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            logger.warn("[EMS] EMPLOYEE_DELETE_FAILED: id={} | reason=NOT_FOUND", id);
            throw new ResourceNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
        logger.info("[EMS] EMPLOYEE_DELETE: id={} | status=SUCCESS", id);
    }

    @Override
    public void deleteAllEmployees() {
        long count = employeeRepository.count();
        if (count == 0) {
            logger.warn("[EMS] EMPLOYEE_DELETE_ALL_SKIPPED: reason=NO_EMPLOYEES");
            throw new ResourceNotFoundException("No employees found to delete");
        }
        employeeRepository.deleteAll();
        logger.info("[EMS] EMPLOYEE_DELETE_ALL: count={} | status=SUCCESS", count);
    }

}
