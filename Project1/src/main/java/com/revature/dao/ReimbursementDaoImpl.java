package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Reimbursement;

public class ReimbursementDaoImpl implements ReimbursementDao {
	private static ReimbursementDao rdao;
	public ReimbursementDaoImpl() {
		super();
	}
	public static ReimbursementDao getRdao() {
		if(rdao == null) {
			rdao = new ReimbursementDaoImpl();
		}
		return rdao;
	}
	
	private String jdbcUrl = "jdbc:postgresql://deldb.crauj7j3czhr.us-east-2.rds.amazonaws.com:5432/del_regdb";
	private String jdbcUsername = "delujan";
	private String jdbcPassword = "Pa$$w0rd";

//	private static final String INSERT_REIMBURSEMENTS_PDGB = "INSERT INTO reimbursements (emp_id, r_amount, r_type,r_date_requested, r_status) VALUES"
//			+ "(?,?,?,?,?);";
	private static final String SELECT_REIMBURSEMENTS_BY_ID = "SELECT * FROM reimbursements WHERE emp_id=?";
	private static final String SELECT_ALL_REIMBURSEMENTS = "SELECT * FROM reimbursements";
	private static final String UPDATE_REIMBURSEMENT = "UPDATE reimbursements SET r_status=? WHERE r_id=?";
	private static final String DELETE_REIMBURSEMENT = "DELETE FROM reimbursements WHERE r_id=?";

	private Connection getConnection() {
		Connection connect = null;
		try {
			Class.forName("org.postgresql.Driver");
			connect = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
		return connect;
	}

	@Override
	public List<Reimbursement> getAllReimbursements() {
		List<Reimbursement> reim = new ArrayList<>();
		// Establish connection
		try (Connection connect = getConnection();
				// Create a prepared statement using the connection object
				PreparedStatement preparedStmnt = connect.prepareStatement(SELECT_ALL_REIMBURSEMENTS);) {
			// Execute the query
			ResultSet rs = preparedStmnt.executeQuery();

			// Process the ResultSet object
			while (rs.next()) {
				int r_id = rs.getInt("r_id");
				int emp_id = rs.getInt("emp_id");
				double amount = rs.getDouble("r_amount");
				String type = rs.getString("r_type");
				String date = rs.getString("r_date_requested");
				String status = rs.getString("r_status");
				reim.add(new Reimbursement(r_id, emp_id, amount, type, date, status));
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return reim;
	}
	@Override
	public List<Reimbursement> getPendingReimbursements() {
		List<Reimbursement> reim = new ArrayList<>();
		String query = "SELECT * FROM reimbursements WHERE r_status='PENDING'";
		try(Connection connect = getConnection();
			PreparedStatement preparedStmnt = connect.prepareStatement(query);){
			ResultSet rs = preparedStmnt.executeQuery();
			
			while(rs.next()) {
				int r_id = rs.getInt("r_id");
				int emp_id = rs.getInt("emp_id");
				double amount = rs.getDouble("r_amount");
				String type = rs.getString("r_type");
				String date = rs.getString("r_date_requested");
				String status = rs.getString("r_status");
				reim.add(new Reimbursement(r_id, emp_id, amount, type, date, status));
			}
				}catch (SQLException e) {
					e.getMessage();
				}
		return reim;
	}
	@Override
	public List<Reimbursement> getResolvedReimbursements() {
		List<Reimbursement> reim = new ArrayList<>();
		String query = "SELECT * FROM reimbursements WHERE r_status!='PENDING'";
		try(Connection connect = getConnection();
			PreparedStatement preparedStmnt = connect.prepareStatement(query);){
			ResultSet rs = preparedStmnt.executeQuery();
			
			while(rs.next()) {
				int r_id = rs.getInt("r_id");
				int emp_id = rs.getInt("emp_id");
				double amount = rs.getDouble("r_amount");
				String type = rs.getString("r_type");
				String date = rs.getString("r_date_requested");
				String status = rs.getString("r_status");
				reim.add(new Reimbursement(r_id, emp_id, amount, type, date, status));
			}
				}catch (SQLException e) {
					e.getMessage();
				}
		return reim;
	}

	@Override
	public List<Reimbursement> getTargetEmpReimbursement(int emp_id) {
		List<Reimbursement> reim = new ArrayList<>();
		// Establish connection
		try (Connection connect = getConnection();
			// Create a prepared statement using the connection object
			PreparedStatement preparedStmnt = connect.prepareStatement(SELECT_REIMBURSEMENTS_BY_ID);) {
			preparedStmnt.setInt(1, emp_id);
			// execute the query
			ResultSet rs = preparedStmnt.executeQuery();

			// process the ResultSet object
			while (rs.next()) {
				int r_id = rs.getInt("r_id");
				double amount = rs.getDouble("r_amount");
				String type = rs.getString("r_type");
				String date = rs.getString("r_date_requested");
				String status = rs.getString("r_status");
				reim.add(new Reimbursement(r_id, emp_id, amount, type, date, status));
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return reim;
	}

	@Override
	public Reimbursement insertReimbursement(Reimbursement entry) {
		String query = "INSERT INTO reimbursements (emp_id,r_amount,r_type,r_date_requested,r_status) VALUES (?,?,?,?,?)";			
		try {
			Connection connect = getConnection();
			connect.setAutoCommit(false);
			PreparedStatement preparedStmnt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmnt.setInt(1, entry.getEmp_id());
			preparedStmnt.setDouble(2, entry.getAmount());
			preparedStmnt.setString(3, entry.getType());
			preparedStmnt.setString(4, entry.getDate());
			preparedStmnt.setString(5, entry.getStatus());

			preparedStmnt.executeUpdate();

			ResultSet rs = preparedStmnt.getGeneratedKeys();
			if(rs.next()) {
				entry.setId(rs.getInt(1));
				connect.commit();
			}else {
				connect.rollback();
			}
			connect.close();
		} catch (SQLException e) {
			e.getMessage();
		}
		return entry;
	}

	@Override
	public boolean updateReimbursement(Reimbursement entry, int id) {
		boolean success = false;
		try {
			try (Connection connect = getConnection();
				PreparedStatement preparedStmnt = connect.prepareStatement(UPDATE_REIMBURSEMENT)) {
				preparedStmnt.setString(1, entry.getStatus());
				preparedStmnt.setInt(2, id);
				preparedStmnt.executeUpdate();

			}
			success = true;
		} catch (Exception e) {
			e.getMessage();
		}
		return success;
	}

	@Override
	public boolean deleteReimbursement(int id) {
		boolean success = false;
	try {
		try(Connection connect = getConnection();
				PreparedStatement preparedStmnt = connect.prepareStatement(DELETE_REIMBURSEMENT);){
			preparedStmnt.setInt(1, id);
			preparedStmnt.executeUpdate();
			}
			success = true;
		}catch (Exception e) {
			e.getMessage();
		}
		return success;
	}
	@Override
	public Reimbursement getReimbursementById(int id) {
		Reimbursement target = new Reimbursement();
		String query = "SELECT * FROM reimbursements WHERE r_id=?";
		try {
			Connection connect = getConnection();
			PreparedStatement preparedStmnt = connect.prepareStatement(query);
			preparedStmnt.setInt(1, id);
			
			ResultSet rs = preparedStmnt.executeQuery();
			while(rs.next()) {
				target.setId(rs.getInt("r_id"));
				target.setEmp_id(rs.getInt("emp_id"));
				target.setAmount(rs.getDouble("r_amount"));
				target.setType(rs.getString("r_type"));
				target.setDate(rs.getString("r_date_requested"));
				target.setStatus(rs.getString("r_status"));
			}
		}catch(SQLException e) {
			e.getMessage();
		}
		return target;
	}
}
