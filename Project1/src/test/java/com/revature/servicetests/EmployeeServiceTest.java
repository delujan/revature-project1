package com.revature.servicetests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import com.revature.model.Employee;
import com.revature.service.EmployeeService;


public class EmployeeServiceTest {

	private static EmployeeService eserv = EmployeeService.instance();
	//Database order affects test - but 5/6 pass with correct input
	//failed test is marked for review
	
	@Test
	@Order(1)
	void insertEmployee() {
		Employee e = new Employee(0, "Harry Potter", "hp@hogwarts.com", "snitch", "employee");

		Employee result = eserv.insertEmployee(e);

		Assertions.assertNotEquals(0, result.getId());

	}

	@Test
	@Order(2)
	void getEmployeeById() {
		List<Employee> test = eserv.selectAllEmployees();
		Employee target = test.get(1);
		Employee result = eserv.selectEmployee(target.getId());
		
		Assertions.assertEquals(18, result.getId());
	}

	@Test
	@Order(3)
	void getEmployeeByEmail() {
		List<Employee> test = eserv.selectAllEmployees();
		Employee target = test.get(test.size()-1);
		Employee result = eserv.getEmployeeByEmail(target.getEmail());

		Assertions.assertEquals("jre@gmail.com", result.getEmail());

	}

	@Test
	@Order(4)
	void getAllEmployees() {
		List<Employee> target = eserv.selectAllEmployees();
		Assertions.assertNotEquals(0, target.size());
	}
	
//	currently failing
	@Test
	@Order(5)
	void updateEmployee() {
		List<Employee> test = eserv.selectAllEmployees();
		Employee update = test.get(test.size() - 1);
		update.setEmail("test@yahoo.com");
		Employee result = eserv.updateEmployee(update);

		Assertions.assertEquals("test@yahoo.com", result.getEmail());
	}
	
	@Test
	@Order(6)
	void deleteEmployee() {
		List<Employee> test = eserv.selectAllEmployees();
		Employee deleteTarget = test.get(test.size() - 1);

		boolean result = eserv.deleteEmployee(deleteTarget);
		Assertions.assertEquals(true, result);
	}

}
