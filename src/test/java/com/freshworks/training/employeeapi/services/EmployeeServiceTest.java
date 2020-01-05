package com.freshworks.training.employeeapi.services;

import com.freshworks.training.employeeapi.dao.DepartmentRepository;
import com.freshworks.training.employeeapi.dao.EmployeeRepository;
import com.freshworks.training.employeeapi.models.Department;
import com.freshworks.training.employeeapi.models.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@Before
	public void setupMocks(){
		MockitoAnnotations.initMocks(this.getClass());
	}

	@Test
	@DisplayName("Test creating an employee")
	public void addEmployeeTest(){
		Mockito.when(departmentRepository.findByDeptName(Mockito.anyString())).thenReturn(Optional.of(new Department()));
		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Department()));
		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(new Employee());
		Optional optional = employeeService.createEmployee("John Doe", "Dept1");
		assertTrue(optional.isPresent());
		optional = employeeService.createEmployee("John Doe", 1);
		assertTrue(optional.isPresent());

		Mockito.when(departmentRepository.findByDeptName(Mockito.anyString())).thenReturn(Optional.empty());
		optional = employeeService.createEmployee("John Doe", "Dept1");
		assertFalse(optional.isPresent());
		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		optional = employeeService.createEmployee("John Doe", 1);
		assertFalse(optional.isPresent());
	}

	@Test
	@DisplayName("Test Finding an employee")
	public void getEmployeeTest(){
		Employee mockEmployee = new Employee();
		Department mockDepartment = new Department();
		mockDepartment.setEmployees(new HashSet<>());
		mockEmployee.setDepartment(mockDepartment);
		Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockEmployee));
		Optional optional = employeeService.getEmployee(1);
		assertTrue(optional.isPresent());

		Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		optional = employeeService.getEmployee(1);
		assertFalse(optional.isPresent());
	}

	@Test
	@DisplayName("Test Update Employee")
	public void updateEmployeeTest(){
		Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Employee()));
		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Department()));
		Mockito.when(departmentRepository.findByDeptName(Mockito.anyString())).thenReturn(Optional.of(new Department()));
		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(new Employee());

		Optional optional = employeeService.updateEmployeeDept(1, 1);
		assertTrue(optional.isPresent());
		optional = employeeService.updateEmployeeDept(1, "");
		assertTrue(optional.isPresent());

		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Mockito.when(departmentRepository.findByDeptName(Mockito.anyString())).thenReturn(Optional.empty());

		optional = employeeService.updateEmployeeDept(1, 1);
		assertFalse(optional.isPresent());
		optional = employeeService.updateEmployeeDept(1, "");
		assertFalse(optional.isPresent());

		Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		optional = employeeService.updateEmployeeDept(1, 1);
		assertFalse(optional.isPresent());
	}

	@Test
	@DisplayName("Test Delete Employee")
	public void deleteEmployeeTest(){
		Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Employee()));
		Mockito.doNothing().when(employeeRepository).delete(Mockito.any(Employee.class));
		boolean deleted = employeeService.deleteEmployee(1);
		assertTrue(deleted);

		Mockito.when(employeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		deleted = employeeService.deleteEmployee(1);
		assertFalse(deleted);
	}

}
