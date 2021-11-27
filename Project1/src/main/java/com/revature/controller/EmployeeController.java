package com.revature.controller;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.revature.model.Employee;
import com.revature.service.EmployeeService;

import io.javalin.http.Handler;

public class EmployeeController {
	
//	public static Handler fetchAllEmployees = ctx ->{
//		EmployeeDaoImpl test = EmployeeDaoImpl.instance();
//		Iterable<Employee> allEmployees = test.getAllEmployees();
//		ctx.json(allEmployees);
//	};
	private static EmployeeService empService = new EmployeeService();
	private static Gson gson = new Gson();
	
	
	public static Handler fetchAllEmployees = ctx ->{
		List<Employee> employees = new ArrayList<Employee>();
		EmployeeService empService = EmployeeService.instance();
//		Iterable<Employee> allEmployees = test.selectAllEmployees();
		employees = empService.selectAllEmployees();
		
		String json = gson.toJson(employees);
		ctx.result(json);
		ctx.status(200);
//		ctx.json(allEmployees);
	};
	
	public static Handler fetchEmployeeById = ctx -> {
		String id = ctx.pathParam("emp_id");
		//int id = Integer.parseInt(Objects.requireNonNull(ctx.formParam("id")));
		try {
			Employee employee = empService.selectEmployee(Integer.parseInt(id));
			String json = gson.toJson(employee);
			ctx.result(json);
			ctx.status(200);
		}catch (NumberFormatException e) {
			ctx.status(404);
			e.printStackTrace();
		}
//		EmployeeService test = EmployeeService.instance();
//		Employee employee = test.selectEmployee(id);
//		if(employee == null) {
//			ctx.html("Not Found");
//		}else {
//			ctx.json(employee);
//		}
	};
	
	public static Handler fetchEmployeeByEmail = ctx->{
		String email = ctx.pathParam("email");
		empService = EmployeeService.instance();
		Employee employee = empService.getEmployeeByEmail(email);
		ctx.json(employee);
	};
	
}
