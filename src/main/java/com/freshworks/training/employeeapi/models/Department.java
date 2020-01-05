package com.freshworks.training.employeeapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ValueGenerationType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer deptId;

	@NotNull
	private String deptName;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Employee> employees;

	@Transient
	private int empCount;

	public Department(String deptName, Employee... employees) {
		this.deptName = deptName;
		this.employees = Stream.of(employees).collect(Collectors.toSet());
		this.employees.forEach(e -> e.setDepartment(this));
		empCount = 0;
	}
}
