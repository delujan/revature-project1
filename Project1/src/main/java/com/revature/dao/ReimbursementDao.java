package com.revature.dao;

import java.util.List;
import com.revature.model.Reimbursement;

public interface ReimbursementDao {
	List<Reimbursement> getAllReimbursements();
	Reimbursement getReimbursementById(int id);
	List<Reimbursement> getTargetEmpReimbursement(int emp_id);
	Reimbursement insertReimbursement(Reimbursement entry);
	boolean updateReimbursement(Reimbursement entry, int id);
	boolean deleteReimbursement(int id);
	List<Reimbursement> getPendingReimbursements();
	List<Reimbursement> getResolvedReimbursements();
}
