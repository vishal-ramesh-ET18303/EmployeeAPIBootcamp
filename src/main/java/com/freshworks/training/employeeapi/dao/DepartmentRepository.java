package com.freshworks.training.employeeapi.dao;

import com.freshworks.training.employeeapi.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	public Optional<Department> findByDeptName(String deptName);

}
