package com.revature.daotests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.dao.ReimbursementDao;
import com.revature.dao.ReimbursementDaoImpl;
import com.revature.model.Reimbursement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReimbursementDaoTest {

	private static ReimbursementDao rdao = ReimbursementDaoImpl.getRdao();
	//Current: 4/5 tests passed. Failed tests are marked.
	
	
//	@Test
//	@Order(1)
//	void createReimbursement() 
//	{
//		Reimbursement r = new Reimbursement(0, 3, 105, "Gas", "2020-10-10", "PENDING");
//		
//		Reimbursement result = rdao.insertReimbursement(r);
//		
//		Assertions.assertNotEquals(0, result.getId());
//		System.out.println(result);
//	}
//	
//	//This test is currently failing - database ordering could be an issue
//	@Test
//	@Order(2)
//	void getReimbursementsById()
//	{
//		List<Reimbursement> test = rdao.getAllReimbursements();
//		List<Reimbursement> result = rdao.getTargetEmpReimbursement(test.get(test.size()-1).getId());
//		Reimbursement rTest = result.get(0);
//		Assertions.assertEquals(75, rTest.getAmount());
//		
//	}
//	
//	@Test
//	@Order(3)
//	void getAllReimbursements()
//	{
//		List<Reimbursement> test = rdao.getAllReimbursements();
//		Assertions.assertNotEquals(0, test.size());
//	}
//	
//	@Test
//	@Order(4)
//	void updateReimbursement()
//	{
//		List<Reimbursement> temp = rdao.getAllReimbursements();
//		Reimbursement target = temp.get(temp.size()-1);
//		target.setAmount(80);
//		boolean result = rdao.updateReimbursement(target, target.getId());
//		
//		Assertions.assertEquals(true,result);
//	}
//	
//	@Test
//	@Order(5)
//	void deleteReimbursement()
//	{
//		List<Reimbursement> temp = rdao.getAllReimbursements();
//		Reimbursement target = temp.get(temp.size()-1);
//		int test = target.getId();
//		boolean result = rdao.deleteReimbursement(test);
//		
//		Assertions.assertEquals(true, result);
//	}
	
	@Test
	@Order(6)
	void getPendingReimbursements() 
	{
		List<Reimbursement> test = rdao.getPendingReimbursements();
		Assertions.assertNotEquals(0, test.size());
	}
	
	@Test
	@Order(7)
	void getResolvedReimbursements() 
	{
		List<Reimbursement> test = rdao.getResolvedReimbursements();
		Assertions.assertNotEquals(0, test.size());
	}
}
