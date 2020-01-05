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

	private Optional getCreateResult(String name, Optional<Department> optionalDepartment) {
		if (optionalDepartment.isPresent()){
			Employee e = new Employee(name);
			Department department = optionalDepartment.get();
			e.setDepartment(department);
			e = employeeRepository.save(e);
			department.setEmpCount(department.getEmpCount()+1);
			return Optional.of(e);
		} else {
			return Optional.empty();
		}
	}

	public Optional<Employee> createEmployee(String name, String deptName){
		Optional<Department> optionalDepartment = departmentRepository.findByDeptName(deptName);
		return getCreateResult(name, optionalDepartment);
	}

	public Optional createEmployee(String name, int deptId){
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		return getCreateResult(name, optionalDepartment);
	}

	public boolean deleteEmployee(int empId){
		Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
		if (optionalEmployee.isPresent()){
			Department department = optionalEmployee.get().getDepartment();
			employeeRepository.delete(optionalEmployee.get());
			department.setEmpCount(department.getEmpCount()-1);
			departmentRepository.save(department);
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
		Optional<Employee> optionalEmployee =  employeeRepository.findById(empId);
		if (optionalEmployee.isPresent()){
			Employee e = optionalEmployee.get();
			Department d = e.getDepartment();
			d.setEmpCount(d.getEmployees().size());
			e.setDepartment(d);
			optionalEmployee = Optional.of(e);
		}
		return optionalEmployee;
	}

	public Iterable getAllEmployees(){
		Iterable<Employee> employees = employeeRepository.findAll();
		Iterable<Department> departments = departmentRepository.findAll();
		departments.forEach(department -> department.setEmpCount(department.getEmployees().size()));
		return employees;
	}

}
