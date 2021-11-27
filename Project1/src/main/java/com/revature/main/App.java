package com.revature.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.revature.controller.EmployeeController;
import com.revature.controller.ReimbursementController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {

	public static void main(String[] args) {
		
		Logger logger = LoggerFactory.getLogger(App.class);
		
		Javalin app = Javalin.create(config -> {
			config.addStaticFiles("/public", Location.CLASSPATH);
		}).start(7000);
		logger.info("Application has started running on port 7000");
		
		app.get("api/employee/{email}", EmployeeController.fetchEmployeeByEmail);
		app.get("api/pendingreimbursements", ReimbursementController.fetchPendingReimbursements);
		app.get("api/pastreimbursements", ReimbursementController.fetchResolvedReimbursements);
		app.get("api/employeerequests/{id}", ReimbursementController.fetchEmployeeReimbursements);

		
		app.post("request", ReimbursementController.submitNewRequest);
		app.post("update", ReimbursementController.updateRequest);
		
	}

}
