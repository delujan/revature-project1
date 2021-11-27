package com.revature.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.revature.model.Reimbursement;
import com.revature.service.ReimbursementService;
import io.javalin.http.Handler;

public class ReimbursementController {
	static Logger logger = LoggerFactory.getLogger(ReimbursementController.class);
	private static ReimbursementService test = ReimbursementService.instance();
	
	//Fetches all reimbursements for all employees and sends in JSON format
	public static Handler fetchAllReimbursements = ctx ->{
		Iterable<Reimbursement> allReimbursements = test.getAllReimbursements();
		logger.info("Fetching all reimbursements");
		ctx.json(allReimbursements);
		ctx.status(200);
	};
	
	//Fetches all pending reimbursements and sends in JSON format
	public static Handler fetchPendingReimbursements = ctx ->{
		Iterable<Reimbursement> allPending = test.getPendingReimbursements();
		logger.info("Fetching all pending reimbursements");
		ctx.json(allPending);
		ctx.status(200);
	};
	
	//Fetches all resolved reimbursements (!pending)  and sends in JSON format
	public static Handler fetchResolvedReimbursements = ctx ->{
		Iterable<Reimbursement> allResolved = test.getResolvedReimbursements();
		logger.info("Fetching all resolved reimbursements");
		ctx.json(allResolved);
		ctx.status(200);
	};
	
	//Fetches all reimbursement requests based on employee id and sends in JSON format
	public static Handler fetchEmployeeReimbursements = ctx ->{
		String id= ctx.pathParam("id");
		Iterable<Reimbursement> allEmployeeRequests = test.getTargetEmpReimbursements(Integer.parseInt(id));
		logger.info("Fetching target employee reimbursements");
		ctx.json(allEmployeeRequests);
		ctx.status(200);
	};

	//Takes in a JSON object, converts to Reimbursement object, then inserts into database
	public static Handler submitNewReimbursement = ctx ->{
		Reimbursement reim = ctx.bodyAsClass(Reimbursement.class);
		logger.info("Submitting new employee reimbursement");
		test.insertReimbursement(reim);
		ctx.status(201);
	};
	
	//Handles reimbursement status updates
	public static Handler updateReimbursement = ctx ->{
		int id = Integer.parseInt(ctx.pathParam("id"));
		Reimbursement reim = ctx.bodyAsClass(Reimbursement.class);
		logger.info("Updating target employee reimbursement");
		test.updateReimbursement(reim, id);
		ctx.status(200);
	};
	
	//This is meant to convert HTML form data into a reimbursement object
	public static Handler submitNewRequest = ctx ->{
		int emp_id = Integer.parseInt(ctx.formParam("emp_id"));
		double amount = Double.parseDouble(ctx.formParam("amount"));
		String type = ctx.formParam("type");
		String date = ctx.formParam("date");
		//String status = ctx.formParam("status");
		Reimbursement temp = new Reimbursement();
		temp.setAmount(amount);
		temp.setType(type);
		temp.setDate(date);
		temp.setStatus("PENDING");
		temp.setEmp_id(emp_id);
		test.insertReimbursement(temp);
		logger.info("Submitting new employee reimbursement");
		ctx.status(201);
		ctx.redirect("employee.html");
	};
	
	//This is meant to convert the HTML form values into an update for the targeted reimbursement
	public static Handler updateRequest = ctx ->{
		Reimbursement temp = new Reimbursement(); 			// new temporary reimbursement object to hold values
		int r_id = Integer.parseInt(ctx.formParam("r_id")); //retrieve reimbursement id from form
		temp = test.getReimbursementById(r_id);				//locate reimbursement in database for other values
		temp.setStatus(ctx.formParam("status"));			//set status to response from html form
		test.updateReimbursement(temp, r_id);				//update the reimbursement
		logger.info("Submitting updated reimbursement");
		ctx.status(201);
		ctx.redirect("manager.html");
	};
}
