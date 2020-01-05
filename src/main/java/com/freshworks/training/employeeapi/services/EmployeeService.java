package com.freshworks.training.employeeapi.services;

import com.freshworks.training.employeeapi.dao.DepartmentRepository;
import com.freshworks.training.employeeapi.dao.EmployeeRepository;
import com.freshworks.training.employeeapi.models.Department;
import com.freshworks.training.employeeapi.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	private static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	private Optional getCreateResult(String name, Optional<Department> optionalDepartment) {
		if (optionalDepartment.isPresent()){
			logger.info("Department found. Creating Employee.");
			Employee e = new Employee(name);
			Department department = optionalDepartment.get();
			e.setDepartment(department);
			e = employeeRepository.save(e);
			department.setEmpCount(department.getEmpCount()+1);
			return Optional.of(e);
		} else {
			logger.info("Delartment not found.");
			return Optional.empty();
		}
	}

	public Optional<Employee> createEmployee(String name, String deptName){
		logger.info("Trying to find department.");
		Optional<Department> optionalDepartment = departmentRepository.findByDeptName(deptName);
		return getCreateResult(name, optionalDepartment);
	}

	public Optional createEmployee(String name, int deptId){
		logger.info("Trying to find department.");
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		return getCreateResult(name, optionalDepartment);
	}

	public boolean deleteEmployee(int empId){
		logger.info("Trying to find employee.");
		Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
		if (optionalEmployee.isPresent()){
			logger.info("Employee Found. Deleting.");
			employeeRepository.delete(optionalEmployee.get());
			return true;
		} else {
			logger.info("Employee not found.");
			return false;
		}
	}

	public Optional updateEmployeeDept(int empId, int deptId){
		logger.info("Trying to find department.");
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		return getUpdateResult(empId, optionalDepartment);
	}

	public Optional updateEmployeeDept(int empId, String deptName){
		logger.info("Trying to find department.");
		Optional<Department> optionalDepartment = departmentRepository.findByDeptName(deptName);
		return getUpdateResult(empId, optionalDepartment);
	}

	private Optional getUpdateResult(int empId, Optional<Department> optionalDepartment) {
		logger.info("Trying to find employee.");
		Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
		if (optionalEmployee.isPresent() && optionalDepartment.isPresent()){
			logger.info("Department and Employee found. Updating Employee.");
			Employee e = optionalEmployee.get();
			e.setDepartment(optionalDepartment.get());
			employeeRepository.save(e);
			return Optional.of(e);
		} else {
			logger.info("Department or Employee not found.");
			return Optional.empty();
		}
	}

	public Optional getEmployee(int empId){
		logger.info("Trying to find employee "+empId);
		Optional<Employee> optionalEmployee =  employeeRepository.findById(empId);
		if (optionalEmployee.isPresent()){
			Employee e = optionalEmployee.get();
			Department d = e.getDepartment();
			d.setEmpCount(d.getEmployees().size());
		}
		return optionalEmployee;
	}

	public Iterable getAllEmployees(){
		logger.info("Getting all employees.");
		Iterable<Employee> employees = employeeRepository.findAll();
		Iterable<Department> departments = departmentRepository.findAll();
		departments.forEach(department -> department.setEmpCount(department.getEmployees().size()));
		return employees;
	}

}
