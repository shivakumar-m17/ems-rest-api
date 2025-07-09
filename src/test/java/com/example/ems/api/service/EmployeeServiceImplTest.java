package com.example.ems.api.service;

import com.example.ems.api.dto.EmployeeDto;
import com.example.ems.api.exception.ResourceNotFoundException;
import com.example.ems.api.model.Employee;
import com.example.ems.api.repository.EmployeeRepository;
import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.greghaskins.spectrum.dsl.specification.Specification.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;

@RunWith(Spectrum.class)
public class EmployeeServiceImplTest {

    {
        describe("EmployeeServiceImpl", () -> {

            EmployeeRepository mockRepository = Mockito.mock(EmployeeRepository.class);
            EmployeeServiceImpl service = new EmployeeServiceImpl(mockRepository);

            Employee sampleEntity = new Employee();
            sampleEntity.setId(1L);
            sampleEntity.setName("Shiva Kumar");
            sampleEntity.setEmail("shiva@gmail.com");
            sampleEntity.setDepartment("IT");
            sampleEntity.setJoiningDate(LocalDate.of(2022, 7, 28));

            EmployeeDto sampleDto = new EmployeeDto();
            sampleDto.setName("Shiva Kumar");
            sampleDto.setEmail("shiva@gmail.com");
            sampleDto.setDepartment("IT");
            sampleDto.setContactDetails(7732070700L);
            sampleDto.setJoiningDate(LocalDate.of(2022, 7, 28));

            describe("createEmployee()", () -> {
                it("should save and return the created employee", () -> {
                    Mockito.when(mockRepository.save(any(Employee.class))).thenReturn(sampleEntity);

                    EmployeeDto result = service.createEmployee(sampleDto);

                    assertThat(result.getName()).isEqualTo("Shiva Kumar");
                    assertThat(result.getEmail()).isEqualTo("shiva@gmail.com");
                });
            });

            describe("getAllEmployees()", () -> {
                it("should return a page of employee DTOs", () -> {
                    Pageable pageable = PageRequest.of(0, 10);
                    Page<Employee> page = new PageImpl<>(List.of(sampleEntity), pageable, 1);

                    Mockito.when(mockRepository.findAll(any(Pageable.class))).thenReturn(page);

                    Page<EmployeeDto> result = service.getAllEmployees(pageable);

                    assertThat(result.getTotalElements()).isEqualTo(1);
                    assertThat(result.getContent().get(0).getName()).isEqualTo("Shiva Kumar");
                });
            });

            describe("getEmployeeById()", () -> {
                it("should return mapped employee if found", () -> {
                    Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));

                    EmployeeDto result = service.getEmployeeById(1L);

                    assertThat(result.getEmail()).isEqualTo("shiva@gmail.com");
                });

                it("should throw if employee not found", () -> {
                    Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.empty());

                    assertThatThrownBy(() -> service.getEmployeeById(1L))
                            .isInstanceOf(RuntimeException.class)
                            .hasMessageContaining("Employee not found");
                });
            });

            describe("updateEmployee()", () -> {
                it("should update existing employee", () -> {
                    Mockito.when(mockRepository.findById(1L)).thenReturn(Optional.of(sampleEntity));
                    Mockito.when(mockRepository.save(any(Employee.class))).thenReturn(sampleEntity);

                    EmployeeDto updated = new EmployeeDto();
                    updated.setName("Shiva Kumar Updated");
                    updated.setEmail("shiva_updated@gmail.com");
                    updated.setDepartment("Finance");
                    updated.setContactDetails(7732070700L);
                    updated.setJoiningDate(LocalDate.of(2023, 1, 1));

                    EmployeeDto result = service.updateEmployee(1L, updated);

                    assertThat(result.getName()).isEqualTo("Shiva Kumar Updated");
                    assertThat(result.getEmail()).isEqualTo("shiva_updated@gmail.com");
                });

                it("should throw if updating non-existing employee", () -> {
                    Mockito.when(mockRepository.findById(99L)).thenReturn(Optional.empty());

                    assertThatThrownBy(() -> service.updateEmployee(99L, sampleDto))
                            .isInstanceOf(ResourceNotFoundException.class)
                            .hasMessageContaining("Employee not found");
                });
            });

            describe("deleteEmployee()", () -> {
                it("should delete employee if exists", () -> {
                    Mockito.when(mockRepository.existsById(1L)).thenReturn(true);
                    Mockito.doNothing().when(mockRepository).deleteById(1L);

                    service.deleteEmployee(1L);
                    Mockito.verify(mockRepository).deleteById(1L);
                });

                it("should throw if employee doesn't exist", () -> {
                    Mockito.when(mockRepository.existsById(99L)).thenReturn(false);

                    assertThatThrownBy(() -> service.deleteEmployee(99L))
                            .isInstanceOf(ResourceNotFoundException.class)
                            .hasMessageContaining("Employee not found");
                });
            });

            describe("deleteAllEmployees()", () -> {
                it("should delete all employees if count > 0", () -> {
                    Mockito.when(mockRepository.count()).thenReturn(5L);
                    Mockito.doNothing().when(mockRepository).deleteAll();

                    service.deleteAllEmployees();

                    Mockito.verify(mockRepository).deleteAll();
                });

                it("should throw if no employees found", () -> {
                    Mockito.when(mockRepository.count()).thenReturn(0L);

                    assertThatThrownBy(() -> service.deleteAllEmployees())
                            .isInstanceOf(ResourceNotFoundException.class)
                            .hasMessageContaining("No employees found");
                });
            });


        });
    }
}
