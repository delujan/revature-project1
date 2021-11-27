package com.revature.model;

public class Reimbursement {
	private int id;
	private int emp_id;
	private double amount;
	private String type;
	private String date;
	private String status;
	
	public Reimbursement() {
		super();
	}
	
	
//	public Reimbursement(int emp_id, double amount, String type, String date, String status) {
//		super();
//		this.emp_id = emp_id;
//		this.amount = amount;
//		this.type = type;
//		this.date = date;
//		this.status = status;
//	}


	public Reimbursement(int id, int emp_id, double amount, String type, String date, String status) {
		super();
		this.id = id;
		this.emp_id = emp_id;
		this.amount = amount;
		this.type = type;
		this.date = date;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", emp_id=" + emp_id + ", amount=" + amount + ", type=" + type + ", date="
				+ date + ", status=" + status + "]";
	}
	
}
