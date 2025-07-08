package com.example.ems.api.service;

import com.example.ems.api.dto.EmployeeDto;
import com.example.ems.api.exception.ResourceNotFoundException;
import com.example.ems.api.model.Employee;
import com.example.ems.api.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper = new ModelMapper();

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto dto) {
        //Employee entity = mapper.map(dto, Employee.class);
        Employee entity = new Employee();
        BeanUtils.copyProperties(dto, entity);
        Employee responseEntity = employeeRepository.save(entity);
        EmployeeDto responseDto = new EmployeeDto();
        BeanUtils.copyProperties(responseEntity, responseDto);

        return responseDto;
        //return mapper.map(employeeRepository.save(entity), EmployeeDto.class);
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
/*

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setJoiningDate(dto.getJoiningDate());

        return mapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }
*/

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

}
