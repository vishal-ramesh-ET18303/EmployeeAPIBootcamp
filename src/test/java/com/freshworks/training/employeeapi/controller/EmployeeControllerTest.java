package com.freshworks.training.employeeapi.controller;

import com.freshworks.training.employeeapi.dao.DepartmentRepository;
import com.freshworks.training.employeeapi.models.Department;
import com.freshworks.training.employeeapi.models.Employee;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

	@MockBean
	RestTemplate restTemplate;
	private String createTargetUrl;
	private String updateTargetUrl;
	private String readTargetUrl;
	private String deleteTargetUrl;
	private HttpHeaders httpHeaders;

	@LocalServerPort
	int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this.getClass());
		createTargetUrl = "http://localhost:"+port+"/api/employee/create";
		updateTargetUrl = "http://localhost:"+port+"/api/employee/update";
		readTargetUrl = "httpL//localhost:"+port+"/api/employee/find";
		deleteTargetUrl = "http://localhost:"+port+"/api/employee/delete/";
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		mockRestExchange();
	}

	private void mockRestExchange(){
		ResponseEntity<String> entity = new ResponseEntity<String>("", HttpStatus.OK);

		Mockito.when(restTemplate.exchange(
				Mockito.anyString(),
				Mockito.any(HttpMethod.class),
				Mockito.any(),
				Mockito.<Class<String >>any()
		)).thenReturn(entity);
	}

	private void mockCreateUpdateExchange(){
		ResponseEntity<Employee> entity = new ResponseEntity<>(new Employee(), HttpStatus.OK);
		Mockito.when(restTemplate.exchange(
				Mockito.matches("./employee/create"),
				HttpMethod.POST,
				Mockito.any(),
				Mockito.<Class<Employee>>any()
		)).thenReturn(entity);
		Mockito.when(restTemplate.exchange(
				Mockito.matches("./employee/update"),
				HttpMethod.POST,
				Mockito.any(),
				Mockito.<Class<Employee>>any()
		)).thenReturn(entity);
	}

	/*private void mockReadExchange(){
		ResponseEntity<Employee> entity = new ResponseEntity(new Employee(), HttpStatus.OK);
		Mockito.when(restTemplate.exchange(
				Mockito.matches("./employee/find"),
				HttpMethod.GET,
				Mockito.any(),
				Mockito.<Class<Employee>>any()
		)).thenReturn(entity);
	}

	private void mockDeleteExchange(){
		Mockito.when(restTemplate.exchange(
				Mockito.matches("./employee/delete/.*"),
				HttpMethod.DELETE,
				Mockito.any(),
				Mockito.<Class<String>>any()
		)).thenReturn(new ResponseEntity<>("", HttpStatus.OK));
	}*/

	@Test
	public void testCreate() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("empName", 1);
		jsonObject.put("deptId", 1);

		mockCreateUpdateExchange();

		HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);

		ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(createTargetUrl, httpEntity, String.class);

		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
