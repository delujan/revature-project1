package com.revature.servicetests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.model.Reimbursement;
import com.revature.service.ReimbursementService;

@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
public class ReimbursementServiceTest {
	private static ReimbursementService rserv = ReimbursementService.instance();
	//All tests are currently passing
	

	@Test
	@Order(1)
	void createReimbursement()
	{
        Reimbursement temp = new Reimbursement(0, 1, 100, "Gas", "2020-10-10", "PENDING");
		
		Reimbursement result = rserv.insertReimbursement(temp);
		Assertions.assertNotEquals(0, result.getId());
	}
	@Test
	@Order(2)
	void getReimbursementById()
	{
		List<Reimbursement> test = rserv.getAllReimbursements();
		Reimbursement target = test.get(test.size()-1);
		double rtest = target.getAmount();
		Assertions.assertEquals(100, rtest);
	}
	@Test
	@Order(3)
	void getAllReimbursements()
	{
		List<Reimbursement> temp = rserv.getAllReimbursements();
		Assertions.assertNotEquals(0, temp.size());
	}
	@Test
	@Order(4)
	void updateReimbursement()
	{
		List<Reimbursement> temp = rserv.getAllReimbursements();
		Reimbursement target = temp.get(temp.size()-1);
		target.setAmount(80);
		boolean result = rserv.updateReimbursement(target, target.getId());
		Assertions.assertEquals(true, result);
	}
	@Test
	@Order(5)
	void deleteReimbursement()
	{
		List<Reimbursement> temp = rserv.getAllReimbursements();
		Reimbursement target = temp.get(temp.size()-1);
		int test = target.getId();
		boolean result = rserv.deleteReimbursement(test);
		
		Assertions.assertEquals(true, result);
	}
	@Test
	@Order(6)
	void getPendingReimbursements() {
		List<Reimbursement> temp = rserv.getPendingReimbursements();
		Assertions.assertNotEquals(0, temp.size());
	}
	@Test
	@Order(7)
	void getResolvedReimbursements() {
		List<Reimbursement> temp = rserv.getResolvedReimbursements();
		Assertions.assertNotEquals(0, temp.size());
	}
}
