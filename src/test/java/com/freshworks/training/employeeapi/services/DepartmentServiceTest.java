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

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentServiceTest {

	@Mock
	private DepartmentRepository departmentRepository;

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private DepartmentService departmentService;

	@Before
	public void setUpMocks(){
		MockitoAnnotations.initMocks(this.getClass());
	}

	@Test
	@DisplayName("Test Department Creation")
	public void addDepartmentTest(){
		Mockito.when(departmentRepository.findByDeptName(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(departmentRepository.save(Mockito.any(Department.class))).thenReturn(new Department());
		Optional optionalDepartment = departmentService.createDepartment("Dept1");
		assertTrue(optionalDepartment.isPresent());

		Mockito.when(departmentRepository.findByDeptName(Mockito.anyString())).thenReturn(Optional.of(new Department()));
		assertTrue(optionalDepartment.isPresent());
	}

	@Test
	@DisplayName("Test Get Department")
	public void getDepartmentTest(){
		Department mockDepartment = new Department("Mock dept", new Employee());
		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockDepartment));
		Optional optionalDepartment = departmentService.getDepartment(1);
		assertTrue(optionalDepartment.isPresent());

		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		optionalDepartment = departmentService.getDepartment(1);
		assertFalse(optionalDepartment.isPresent());
	}

	@Test
	@DisplayName("Test Update Department")
	public void updateDepartmentTest(){
		Department mockDepartment = new Department("Mock dept", new Employee());
		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockDepartment));
		Mockito.when(departmentRepository.save(Mockito.any(Department.class))).thenReturn(mockDepartment);
		Optional optionalDepartment = departmentService.updateDepartment(1, "Dept2");
		assertTrue(optionalDepartment.isPresent());

		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		optionalDepartment = departmentService.updateDepartment(1, "Dept2");
		assertFalse(optionalDepartment.isPresent());
	}

	@Test
	@DisplayName("Test Delete Department")
	public void deleteDepartmentTest(){
		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Department()));
		Mockito.doNothing().when(departmentRepository).delete(Mockito.any(Department.class));
		boolean deleted = departmentService.deleteDepartment(1);
		assertTrue(deleted);

		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		deleted = departmentService.deleteDepartment(1);
		assertFalse(deleted);
	}

	@Test
	@DisplayName("Test Get All Employees")
	public void getAllEmployeesTest(){
		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Department("Dept1", new Employee())));
		Optional optionalEmployees = departmentService.getAllEmployeesInDept(1);
		assertTrue(optionalEmployees.isPresent());

		Mockito.when(departmentRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		optionalEmployees = departmentService.getAllEmployeesInDept(1);
		assertFalse(optionalEmployees.isPresent());
	}

}
