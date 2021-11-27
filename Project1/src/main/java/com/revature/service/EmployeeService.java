package com.revature.service;

import java.util.List;

import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.model.Employee;

public class EmployeeService {
	private EmployeeDao empDao;
	
	private static EmployeeService empService = null;
	
	public EmployeeService() {
		empDao = new EmployeeDaoImpl();
	}
	
	public static EmployeeService instance() {
		if(empService == null) {
			empService = new EmployeeService();
		}
		return empService;
	}
	
	public Employee insertEmployee(Employee emp) {
		return empDao.insertEmployee(emp);
	}
	
	public Employee updateEmployee(Employee emp) {
		return empDao.updateEmployee(emp);
	}
	
	public Employee selectEmployee(int id) {
		return empDao.getEmployee(id);
	}
	
	public Employee getEmployeeByEmail(String email) {
		return empDao.getEmployeeByEmail(email);
	}
	
	public List<Employee> selectAllEmployees(){
		return empDao.getAllEmployees();
	}
	
	public boolean deleteEmployee(Employee emp) {
		return empDao.deleteEmployee(emp);
	}
}
