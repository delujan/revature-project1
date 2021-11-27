package com.revature.dao;

import java.util.List;
import com.revature.model.Employee;

//Data Access Object Interface
public interface EmployeeDao {
	
	List<Employee> getAllEmployees();
	Employee getEmployee(int emp_id);
	Employee getEmployeeByEmail(String email);
	Employee insertEmployee(Employee employee);
	Employee updateEmployee(Employee employee);
	boolean deleteEmployee(Employee employee);
}
