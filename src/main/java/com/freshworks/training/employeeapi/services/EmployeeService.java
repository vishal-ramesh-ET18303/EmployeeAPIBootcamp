package com.freshworks.training.employeeapi.services;

import com.freshworks.training.employeeapi.dao.DepartmentRepository;
import com.freshworks.training.employeeapi.dao.EmployeeRepository;
import com.freshworks.training.employeeapi.models.Department;
import com.freshworks.training.employeeapi.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	public Optional<Employee> createEmployee(String name, String deptName){
		Optional<Department> optionalDepartment = departmentRepository.findByDeptName(deptName);
		if (optionalDepartment.isPresent()){
			Employee e = new Employee(name);
			e.setDepartment(optionalDepartment.get());
			e = employeeRepository.save(e);
			return Optional.of(e);
		} else {
			return Optional.empty();
		}
	}

	public Optional createEmployee(String name, int deptId){
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalDepartment.isPresent()){
			Employee e = new Employee(name);
			e.setDepartment(optionalDepartment.get());
			e = employeeRepository.save(e);
			return Optional.of(e);
		} else {
			return Optional.empty();
		}
	}

	public boolean deleteEmployee(int empId){
		Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
		if (optionalEmployee.isPresent()){
			employeeRepository.delete(optionalEmployee.get());
			return true;
		} else {
			return false;
		}
	}

	public Optional updateEmployeeDept(int empId, int deptId){
		Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalEmployee.isPresent() && optionalDepartment.isPresent()){
			Employee e = optionalEmployee.get();
			e.setDepartment(optionalDepartment.get());
			employeeRepository.save(e);
			return Optional.of(e);
		} else {
			return Optional.empty();
		}
	}

	public Optional updateEmployeeDept(int empId, String deptName){
		Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
		Optional<Department> optionalDepartment = departmentRepository.findByDeptName(deptName);
		if (optionalEmployee.isPresent() && optionalDepartment.isPresent()){
			Employee e = optionalEmployee.get();
			e.setDepartment(optionalDepartment.get());
			employeeRepository.save(e);
			return Optional.of(e);
		} else {
			return Optional.empty();
		}
	}

	public Optional getEmployee(int empId){
		return employeeRepository.findById(empId);
	}

}
