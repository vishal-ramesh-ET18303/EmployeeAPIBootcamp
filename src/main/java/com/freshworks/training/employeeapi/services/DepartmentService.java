package com.freshworks.training.employeeapi.services;

import com.freshworks.training.employeeapi.dao.DepartmentRepository;
import com.freshworks.training.employeeapi.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
			return Optional.of(department);
		} else {
			return Optional.empty();
		}
	}

	public Optional getDepartment(int deptId){
		return departmentRepository.findById(deptId);
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
			return Optional.of(optionalDepartment.get().getEmployees());
		} else {
			return Optional.empty();
		}
	}

	public Optional getAllEmployeesInDept(String deptName){
		Optional<Department> optionalDepartment = departmentRepository.findByDeptName(deptName);
		if (optionalDepartment.isPresent()){
			return Optional.of(optionalDepartment.get().getEmployees());
		} else {
			return Optional.empty();
		}
	}

}
