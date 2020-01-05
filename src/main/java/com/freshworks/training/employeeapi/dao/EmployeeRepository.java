package com.freshworks.training.employeeapi.dao;

import com.freshworks.training.employeeapi.models.Department;
import com.freshworks.training.employeeapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	public Iterable<Employee> findAllByDepartment(Department department);

}
