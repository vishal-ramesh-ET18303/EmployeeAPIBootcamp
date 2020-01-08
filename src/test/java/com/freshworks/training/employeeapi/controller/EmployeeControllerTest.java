package com.freshworks.training.employeeapi.controller;

import com.freshworks.training.employeeapi.dao.DepartmentRepository;
import com.freshworks.training.employeeapi.dao.EmployeeRepository;
import com.freshworks.training.employeeapi.models.Department;
import com.freshworks.training.employeeapi.models.Employee;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

	private String targetUrl;
	private HttpHeaders httpHeaders;
	private Department mockDepartment;
	private Employee mockEmployee;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this.getClass());
		targetUrl = "http://localhost:"+port+"/api/employee";
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		mockDepartment = new Department("Department");
		mockDepartment = departmentRepository.save(mockDepartment);
		mockEmployee = new Employee();
		mockEmployee.setName("Employee");
		mockEmployee.setDepartment(mockDepartment);
		mockEmployee = employeeRepository.save(mockEmployee);
	}

	@Test
	public void testCreate() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("empName", "Employee");
		jsonObject.put("deptId", mockDepartment.getDeptId());

		HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), httpHeaders);
		ResponseEntity responseEntity =  testRestTemplate.postForEntity(targetUrl+"/create", request, String.class);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		jsonObject.put("deptId", mockDepartment.getDeptId()+1);

		request = new HttpEntity<>(jsonObject.toString(), httpHeaders);
		responseEntity =  testRestTemplate.postForEntity(targetUrl+"/create", request, String.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testRead(){

		ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(targetUrl+"/find?empId="+mockEmployee.getEmpId(), String.class);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		responseEntity = testRestTemplate.getForEntity(targetUrl+"/find?empId="+(mockEmployee.getEmpId()+1), String.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdate() throws JSONException{
		Department mockDepartment2 = new Department();
		mockDepartment2.setDeptName("Dept2");
		mockDepartment2 = departmentRepository.save(mockDepartment2);


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("empId", mockEmployee.getEmpId());
		jsonObject.put("deptId", mockDepartment2.getDeptId());

		HttpEntity<String> entity = new HttpEntity(jsonObject.toString(), httpHeaders);
		ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(targetUrl+"/update", entity, String.class);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		jsonObject.put("deptId", mockDepartment2.getDeptId()+1);

		entity = new HttpEntity(jsonObject.toString(), httpHeaders);
		responseEntity = testRestTemplate.postForEntity(targetUrl+"/update", entity, String.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testDelete() {
		Employee mockEmployee3 = new Employee();
		mockEmployee3.setName("Employee3");
		mockEmployee3.setDepartment(mockDepartment);
		mockEmployee3 = employeeRepository.save(mockEmployee3);
		testRestTemplate.delete(targetUrl+"/delete/"+mockEmployee3.getEmpId());
		Assert.assertFalse(employeeRepository.findById(mockEmployee3.getEmpId()).isPresent());
	}

	@Test
	public void testGetAll(){
		ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(targetUrl+"/all", String.class);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
