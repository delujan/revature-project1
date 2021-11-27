package com.revature.daotests;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.model.Employee;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeDaoTest {

	private static EmployeeDao edao = EmployeeDaoImpl.getEdao();
	//Database order affects test - but 5/6 pass with correct input
	//failed test is marked for review
	

	@Test
	@Order(1)
	void insertEmployee() {
		Employee e = new Employee(
				0, "Harry Potter",
				"hp@hogwarts.com",
				"snitch","employee");
	
	Employee result = edao.insertEmployee(e);
	
	Assertions.assertNotEquals(0, result.getId());

	}
	
	@Test
	@Order(2)
	void getEmployeeById()
	{
		List<Employee> test = edao.getAllEmployees();
		Employee target = test.get(1);
		Employee result = edao.getEmployee(target.getId());	
		Assertions.assertEquals(2, result.getId());
	}
	@Test
	@Order(3)
	void getEmployeeByEmail() 
	{
		List<Employee> test = edao.getAllEmployees();
		Employee target = test.get(1);
		Employee result = edao.getEmployeeByEmail(target.getEmail());
		
		Assertions.assertEquals("rush@mma.com", result.getEmail());
		
	}
	
	@Test
	@Order(4)
	void getAllEmployees()
	{
		List<Employee> target = edao.getAllEmployees();
		Assertions.assertNotEquals(0, target.size());
	}
	
	
	//Test currently failing
	@Test
	@Order(5)
	void updateEmployee()
	{
		List<Employee> test = edao.getAllEmployees();
		Employee update = test.get(test.size()-1);
		update.setEmail("test@yahoo.com");
		Employee result = edao.updateEmployee(update);
		
		Assertions.assertEquals("test@yahoo.com", result.getEmail());
	}
	
	@Test
	@Order(6)
	void deleteEmployee()
	{
		List<Employee> test = edao.getAllEmployees();
		Employee deleteTarget = test.get(test.size()-1);
		
		boolean result = edao.deleteEmployee(deleteTarget);
		Assertions.assertEquals(true,result);
	}
	
	
	
}
