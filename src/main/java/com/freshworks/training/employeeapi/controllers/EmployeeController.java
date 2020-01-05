package com.freshworks.training.employeeapi.controllers;

import com.freshworks.training.employeeapi.models.Employee;
import com.freshworks.training.employeeapi.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(path = "/api/employee")
public class EmployeeController {

	final private String EMP_NAME = "empName";
	final private String EMP_ID = "empId";
	final private String DEPT_NAME = "deptName";
	final private String DEPT_ID = "deptId";

	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity createEmployee(@RequestBody Map<String, Object> requestParams){
		logger.info("Received request to create Employee.");
		Set<String> keys = requestParams.keySet();
		Optional<Employee> optionalEmployee;
		if (keys.contains(DEPT_ID)){
			optionalEmployee =  employeeService.createEmployee((String)requestParams.get(EMP_NAME), (Integer)requestParams.get(DEPT_ID));

		} else {
			optionalEmployee = employeeService.createEmployee((String)requestParams.get(EMP_NAME), (String)requestParams.get(DEPT_NAME));

		}
		if (optionalEmployee.isPresent()){
			logger.info("Employee created with empId: "+optionalEmployee.get().getEmpId()+" and deptId: "+optionalEmployee.get().getDepartment().getDeptId());
			return new ResponseEntity<Employee>(optionalEmployee.get(), HttpStatus.OK);
		} else {
			logger.info("Employee not created as department was not found.");
			return new ResponseEntity<String>("Department not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/find")
	public @ResponseBody ResponseEntity readEmployee(@RequestParam int empId){
		logger.info("Getting Employee with empId: "+empId);
		Optional<Employee> optionalEmployee = employeeService.getEmployee(empId);
		return optionalEmployee.map(employee -> new ResponseEntity(employee, HttpStatus.OK)).orElseGet(() -> new ResponseEntity("Employee not found", HttpStatus.NOT_FOUND));
	}

	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity updateEmployee(@RequestBody Map<String, Object> requestParam){
		logger.info("Received request to update Employee.");
		Set keys = requestParam.keySet();
		Optional<Employee> optionalEmployee;
		if (keys.contains(DEPT_ID)){
			 optionalEmployee = employeeService.updateEmployeeDept((Integer) requestParam.get(EMP_ID), (Integer) requestParam.get(DEPT_ID));
		} else {
			 optionalEmployee = employeeService.updateEmployeeDept((Integer) requestParam.get(EMP_ID), (String) requestParam.get(DEPT_NAME));
		}
		return optionalEmployee.map(employee -> new ResponseEntity(employee, HttpStatus.OK)).orElseGet(() -> new ResponseEntity("Employee not found", HttpStatus.NOT_FOUND));
	}

	@DeleteMapping(path = "/delete/{empId}")
	public @ResponseBody ResponseEntity deleteEmployee(@PathVariable int empId){
		logger.info("Received request to delete Employee with empId: "+empId);
		boolean deleted = employeeService.deleteEmployee(empId);
		if (deleted){
			logger.info("Employee Deleted");
			return new ResponseEntity("Employee Deleted", HttpStatus.OK);
		} else {
			logger.info("Employee not found");
			return new ResponseEntity("Employee Not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable getAllEmployees(){
		logger.info("Received request to get all Employees.");
		return employeeService.getAllEmployees();
	}

}
