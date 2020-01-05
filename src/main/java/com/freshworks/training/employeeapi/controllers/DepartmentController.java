package com.freshworks.training.employeeapi.controllers;

import com.freshworks.training.employeeapi.models.Department;
import com.freshworks.training.employeeapi.services.DepartmentService;
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
@RequestMapping(path = "/api/dept")
public class DepartmentController {

	final private String DEPT_ID = "deptId";
	final private String DEPT_NAME = "deptName";

	@Autowired
	DepartmentService departmentService;

	private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@PostMapping(path = "/create")
	public @ResponseBody
	Department createDept(@RequestBody Map<String, String> requestParams){
		logger.info("Received request to create department "+requestParams.get(DEPT_NAME));
		Optional<Department> optionalDepartment = departmentService.createDepartment(requestParams.get(DEPT_NAME));
		return optionalDepartment.get();
	}

	@GetMapping(path = "/find")
	public @ResponseBody
	ResponseEntity findDept(@RequestParam int deptId){
		logger.info("Received request to find department deptId: "+deptId);
		Optional<Department> optionalDepartment = departmentService.getDepartment(deptId);
		return optionalDepartment.map(department -> new ResponseEntity(department, HttpStatus.OK)).orElseGet(() -> new ResponseEntity("Department Not found", HttpStatus.NOT_FOUND));
	}

	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity updateDept(@RequestBody Map<String, Object> requestParams){
		logger.info("Received request to update department deptId: "+requestParams.get(DEPT_ID));
		Optional<Department> optionalDepartment = departmentService.updateDepartment((Integer) requestParams.get(DEPT_ID), (String) requestParams.get(DEPT_NAME));
		return optionalDepartment.map(department -> new ResponseEntity(department, HttpStatus.OK)).orElseGet(() -> new ResponseEntity("Department not found", HttpStatus.NOT_FOUND));
	}

	@DeleteMapping(path = "/delete/{deptId}")
	public @ResponseBody
	ResponseEntity deleteDept(@PathVariable int deptId){
		logger.info("Received request to delete department deptId: "+deptId);
		boolean deleted = departmentService.deleteDepartment(deptId);
		if (deleted){
			return new ResponseEntity("Deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity("Department not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/getEmployees")
	public @ResponseBody ResponseEntity getAllEmployees(@RequestParam int deptId){
		logger.info("Received request to get all employeesint with deptId: "+deptId);
		Optional<Set> optionalEmployees = departmentService.getAllEmployeesInDept(deptId);
		return optionalEmployees.map(set -> new ResponseEntity(set, HttpStatus.OK)).orElseGet(() -> new ResponseEntity("Department not found", HttpStatus.NOT_FOUND));
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable getAllDepartments(){
		logger.info("Received request to get all departments.");
		return departmentService.getAllDepartments();
	}

}
