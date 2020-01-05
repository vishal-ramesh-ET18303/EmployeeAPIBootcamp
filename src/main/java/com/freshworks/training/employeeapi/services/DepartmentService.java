package com.freshworks.training.employeeapi.services;

import com.freshworks.training.employeeapi.dao.DepartmentRepository;
import com.freshworks.training.employeeapi.models.Department;
import com.freshworks.training.employeeapi.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	public Department createDepartment(String name){
		Optional<Department> optionalDepartment = departmentRepository.findByDeptName(name);
		if (optionalDepartment.isPresent()){
			return optionalDepartment.get();
		} else {
			Department department = new Department();
			department.setDeptName(name);
			department.setEmpCount(0);
			department = departmentRepository.save(department);
			return department;
		}
	}

	public Optional updateDepartment(int deptId, String name){
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalDepartment.isPresent()){
			Department department = optionalDepartment.get();
			department.setDeptName(name);
			department = departmentRepository.save(department);
			department.setEmpCount(department.getEmployees().size());
			return Optional.of(department);
		} else {
			return Optional.empty();
		}
	}

	public Optional getDepartment(int deptId){
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalDepartment.isPresent()){
			Department d = optionalDepartment.get();
			d.setEmpCount(d.getEmployees().size());
			optionalDepartment = Optional.of(d);
		}
		return optionalDepartment;
	}

	public boolean deleteDepartment(int deptId){
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalDepartment.isPresent()){
			departmentRepository.delete(optionalDepartment.get());
			return true;
		} else {
			return false;
		}
	}

	public Optional getAllEmployeesInDept(int deptId){
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalDepartment.isPresent()){
			Department d = optionalDepartment.get();
			d.setEmpCount(d.getEmployees().size());
			return Optional.of(d.getEmployees());
		} else {
			return Optional.empty();
		}
	}

	public Iterable getAllDepartments(){
		List<Department> departments = departmentRepository.findAll();
		departments.forEach(department -> department.setEmpCount(department.getEmployees().size()));
		return departments;
	}

}
