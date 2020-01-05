package com.freshworks.training.employeeapi.services;

import com.freshworks.training.employeeapi.dao.DepartmentRepository;
import com.freshworks.training.employeeapi.models.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	private Logger logger = LoggerFactory.getLogger(DepartmentService.class);

	public Department createDepartment(String name){
		logger.info("Trying to find department "+name);
		Optional<Department> optionalDepartment = departmentRepository.findByDeptName(name);
		if (optionalDepartment.isPresent()){
			logger.info("Department found.");
			return optionalDepartment.get();
		} else {
			logger.info("Department not found. Creating.");
			Department department = new Department();
			department.setDeptName(name);
			department.setEmpCount(0);
			department = departmentRepository.save(department);
			return department;
		}
	}

	public Optional updateDepartment(int deptId, String name){
		logger.info("Trying to find department "+name);
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalDepartment.isPresent()){
			logger.info("Department found. Updating.");
			Department department = optionalDepartment.get();
			department.setDeptName(name);
			department = departmentRepository.save(department);
			department.setEmpCount(department.getEmployees().size());
			return Optional.of(department);
		} else {
			logger.info("Department not found.");
			return Optional.empty();
		}
	}

	public Optional getDepartment(int deptId){
		logger.info("Trying to find department deptId: "+deptId);
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalDepartment.isPresent()){
			logger.info("Department found.");
			Department d = optionalDepartment.get();
			d.setEmpCount(d.getEmployees().size());
			optionalDepartment = Optional.of(d);
		} else {
			logger.info("Department not found.");
		}
		return optionalDepartment;
	}

	public boolean deleteDepartment(int deptId){
		logger.info("Trying to find department deptId: "+deptId);
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalDepartment.isPresent()){
			logger.info("Department found. Deleting.");
			departmentRepository.delete(optionalDepartment.get());
			return true;
		} else {
			logger.info("Department not found.");
			return false;
		}
	}

	public Optional getAllEmployeesInDept(int deptId){
		logger.info("Getting all employees in department deptId: "+deptId);
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		if (optionalDepartment.isPresent()){
			Department d = optionalDepartment.get();
			d.setEmpCount(d.getEmployees().size());
			return Optional.of(d.getEmployees());
		} else {
			logger.info("Department not found.");
			return Optional.empty();
		}
	}

	public Iterable getAllDepartments(){
		logger.info("Getting all departments.");
		List<Department> departments = departmentRepository.findAll();
		departments.forEach(department -> department.setEmpCount(department.getEmployees().size()));
		return departments;
	}

}
