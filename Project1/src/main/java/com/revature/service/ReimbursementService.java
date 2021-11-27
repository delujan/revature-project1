package com.revature.service;

import java.util.List;

import com.revature.dao.ReimbursementDao;
import com.revature.dao.ReimbursementDaoImpl;
import com.revature.model.Reimbursement;

public class ReimbursementService {
	private ReimbursementDao reimDao;
	private static ReimbursementService reimbService = null;
	
	public ReimbursementService() {
		reimDao = new ReimbursementDaoImpl();
	}
	public static ReimbursementService instance(){
		if(reimbService == null) {
			reimbService = new ReimbursementService();
		}
		return reimbService;
	}
	public Reimbursement insertReimbursement(Reimbursement entry) {
		return reimDao.insertReimbursement(entry);
	}
	public boolean updateReimbursement(Reimbursement entry, int id) {
		return reimDao.updateReimbursement(entry, id);
	}
	public boolean deleteReimbursement(int id) {
		return reimDao.deleteReimbursement(id);
	}
	public List<Reimbursement> getAllReimbursements(){
		return reimDao.getAllReimbursements();
	}
	public List<Reimbursement> getTargetEmpReimbursements(int emp_id){
		return reimDao.getTargetEmpReimbursement(emp_id);
	}
	public List<Reimbursement> getPendingReimbursements() {
		return reimDao.getPendingReimbursements();
	}
	public List<Reimbursement> getResolvedReimbursements() {
		return reimDao.getResolvedReimbursements();
	}
	public Reimbursement getReimbursementById(int id) {
		return reimDao.getReimbursementById(id);
	}
}
