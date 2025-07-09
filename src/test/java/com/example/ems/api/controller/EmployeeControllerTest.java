package com.example.ems.api.controller;

import com.example.ems.api.dto.EmployeeDto;
import com.example.ems.api.service.EmployeeService;
import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.greghaskins.spectrum.dsl.specification.Specification.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

@RunWith(Spectrum.class)
public class EmployeeControllerTest {

    {
        describe("EmployeeController", () -> {

            EmployeeService employeeService = Mockito.mock(EmployeeService.class);
            EmployeeController controller = new EmployeeController(employeeService);

            EmployeeDto sampleDto = new EmployeeDto(
                    1L,
                    "Shiva Kumar",
                    "shiva@gmail.com",
                    "IT",
                    7732070700L,
                    LocalDate.of(2022, 7, 28)
            );

            describe("createEmployee()", () -> {
                it("should return a CREATED response with message and employee", () -> {
                    Mockito.when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(sampleDto);

                    var response = controller.createEmployee(sampleDto);

                    assertThat(response.getStatusCodeValue()).isEqualTo(201);
                    Map<String, Object> body = response.getBody();
                    assertThat(body).isNotNull();
                    assertThat(body.get("message")).isEqualTo("Employee created successfully");
                    assertThat(((EmployeeDto) body.get("employee")).getEmail()).isEqualTo("shiva@gmail.com");
                });
            });

            describe("getAllEmployees()", () -> {
                it("should return a page of employees", () -> {
                    Pageable pageable = PageRequest.of(0, 10);
                    List<EmployeeDto> employeeList = List.of(sampleDto);
                    Page<EmployeeDto> page = new PageImpl<>(employeeList, pageable, 1);

                    Mockito.when(employeeService.getAllEmployees(any(Pageable.class))).thenReturn(page);

                    Page<EmployeeDto> result = controller.getAllEmployees(pageable);

                    assertThat(result.getTotalElements()).isEqualTo(1);
                    assertThat(result.getContent().get(0).getDepartment()).isEqualTo("IT");
                });
            });

            describe("getEmployeeById()", () -> {
                it("should return employee by id", () -> {
                    Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(sampleDto);

                    EmployeeDto result = controller.getEmployeeById(1L);

                    assertThat(result.getName()).isEqualTo("Shiva Kumar");
                    assertThat(result.getContactDetails()).isEqualTo(7732070700L);
                });
            });

            describe("updateEmployee()", () -> {
                it("should return OK with updated employee", () -> {
                    Mockito.when(employeeService.updateEmployee(eq(1L), any(EmployeeDto.class))).thenReturn(sampleDto);

                    var response = controller.updateEmployee(1L, sampleDto);

                    assertThat(response.getStatusCodeValue()).isEqualTo(200);
                    Map<String, Object> body = response.getBody();
                    assertThat(body.get("message")).isEqualTo("Employee updated successfully");
                    assertThat(((EmployeeDto) body.get("employee")).getName()).isEqualTo("Shiva Kumar");
                });
            });

            describe("deleteEmployee()", () -> {
                it("should return OK after deleting employee", () -> {
                    Mockito.doNothing().when(employeeService).deleteEmployee(1L);

                    var response = controller.deleteEmployee(1L);

                    assertThat(response.getStatusCodeValue()).isEqualTo(200);
                    Map<String, Object> body = response.getBody();
                    assertThat(body.get("message")).isEqualTo("Employee deleted successfully");
                    assertThat(body.get("id")).isEqualTo(1L);
                });
            });

            describe("deleteAllEmployees()", () -> {
                it("should delete all employees", () -> {
                    Mockito.doNothing().when(employeeService).deleteAllEmployees();

                    var response = controller.deleteAllEmployees();

                    assertThat(response.getStatusCodeValue()).isEqualTo(200);
                    assertThat(response.getBody().get("message")).isEqualTo("All employees deleted successfully");
                });
            });
        });
    }
}
