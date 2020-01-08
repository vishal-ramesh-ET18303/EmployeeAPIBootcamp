package com.freshworks.training.employeeapi.controller;

import com.freshworks.training.employeeapi.dao.DepartmentRepository;
import com.freshworks.training.employeeapi.models.Department;
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
public class DepartmentControllerTest {

	@LocalServerPort
	private int port;
	private String targetUrl;
	private HttpHeaders httpHeaders;
	private Department mockDepartment;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this.getClass());
		targetUrl = "http://localhost:"+port+"/api/dept";
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		mockDepartment = new Department("Department");
		mockDepartment = departmentRepository.save(mockDepartment);
	}

	@Test
	public void testCreateDept() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deptName", "Department2");

		HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), httpHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(targetUrl+"/create", entity, String.class);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testReadDept(){
		ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(targetUrl+"/find?deptId="+mockDepartment.getDeptId(), String.class);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		responseEntity = testRestTemplate.getForEntity(targetUrl+"/find?deptId="+(mockDepartment.getDeptId()+3), String.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdateDept() throws JSONException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deptId", mockDepartment.getDeptId());
		jsonObject.put("deptName", "Department3");

		HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), httpHeaders);
		ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(targetUrl+"/update", entity, String.class);
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void testDeleteDept(){
		Department department = new Department();
		department.setDeptName("Department4");
		department = departmentRepository.save(department);

		testRestTemplate.delete(targetUrl+"/delete/"+department.getDeptId());
		Assert.assertFalse(departmentRepository.findById(department.getDeptId()).isPresent());
	}

}
