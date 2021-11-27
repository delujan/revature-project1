package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.revature.model.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	private static EmployeeDao edao;

	public EmployeeDaoImpl() {
		super();
	}

	public static EmployeeDao getEdao() {
		if (edao == null) {
			edao = new EmployeeDaoImpl();
		}
		return edao;
	}

	private String jdbcUrl = "jdbc:postgresql://deldb.crauj7j3czhr.us-east-2.rds.amazonaws.com:5432/del_regdb";
	private String jdbcUsername = "delujan";
	private String jdbcPassword = "Pa$$w0rd";

	private static final String INSERT_EMPLOYEES_PGDB = "INSERT INTO employees (emp_name,emp_email,emp_password,emp_type) VALUES (?,?,?,?)";
	private static final String SELECT_EMP_BY_ID = "SELECT * FROM employees WHERE emp_id =?";
	private static final String SELECT_ALL_EMP = "SELECT * FROM employees";
	private static final String DELETE_EMP = "DELETE FROM  employees WHERE emp_id =?;";
	private static final String UPDATE_EMP = "UPDATE employees SET emp_name =?, emp_email=?, emp_password=?, admin_access=? WHERE emp_id=?";

	protected Connection getConnection() {
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
	public List<Employee> getAllEmployees() {
		List<Employee> emps = new ArrayList<>();
		// Establish connection
		try (Connection connect = getConnection();
				// Create a prepared statement using the connection object
				PreparedStatement preparedStmnt = connect.prepareStatement(SELECT_ALL_EMP);) {
			// Execute the query
			ResultSet rs = preparedStmnt.executeQuery();

			// Process the ResultSet object
			while (rs.next()) {
				int id = rs.getInt("emp_id");
				String name = rs.getString("emp_name");
				String email = rs.getString("emp_email");
				String password = rs.getString("emp_password");
				String type = rs.getString("emp_type");
				emps.add(new Employee(id, name, email, password, type));
			}
			return emps;
		} catch (SQLException e) {
			e.getMessage();
			return null;
		}
	}

	@Override
	public Employee getEmployee(int emp_id) {
		Employee employee = new Employee();
		// Establish connection
		try {
			// Establish connection
			Connection connect = getConnection();
			// Create a prepared statement using the connection object
			PreparedStatement preparedStmnt = connect.prepareStatement(SELECT_EMP_BY_ID);
			preparedStmnt.setInt(1, emp_id);
			// execute the query
			ResultSet rs = preparedStmnt.executeQuery();
			// process the ResultSet object
			if (rs.next()) 
			{
				// initialize employee with values from database result
				employee.setId(emp_id);
				employee.setName(rs.getString("emp_name"));
				employee.setEmail(rs.getString("emp_email"));
				employee.setPassword(rs.getString("emp_password"));
				employee.setType(rs.getString("emp_type"));
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return employee;
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		Employee employee = new Employee();
		String SELECT_EMP_BY_EMAIL = "SELECT * FROM employees WHERE emp_email=?";

		try (Connection connect = getConnection();
				PreparedStatement preparedStmnt = connect.prepareStatement(SELECT_EMP_BY_EMAIL);) {
			preparedStmnt.setString(1, email);
			ResultSet rs = preparedStmnt.executeQuery();

			while (rs.next()) {
				employee.setId(rs.getInt("emp_id"));
				employee.setName(rs.getString("emp_name"));
				employee.setEmail(rs.getString("emp_email"));
				employee.setPassword(rs.getString("emp_password"));
				employee.setType(rs.getString("emp_type"));
			}
			return employee;
		} catch (SQLException e) {
			e.getMessage();
			return null;
		}

	}

	@Override
	public Employee updateEmployee(Employee employee) { // currently not updating database
		try (Connection connect = getConnection()) {
			PreparedStatement preparedStmnt = connect.prepareStatement(UPDATE_EMP);

			preparedStmnt.setString(1, employee.getName());
			preparedStmnt.setString(2, employee.getEmail());
			preparedStmnt.setString(3, employee.getPassword());
			preparedStmnt.setString(4, employee.getType());
			preparedStmnt.setInt(5, employee.getId());

			preparedStmnt.executeUpdate();

			return employee;
		} catch (SQLException e) {
			e.getMessage();
		}
		return null;
	}

	@Override
	public boolean deleteEmployee(Employee employee) {
		try (Connection connect = getConnection()) {
			PreparedStatement preparedStmnt = connect.prepareStatement(DELETE_EMP);
			preparedStmnt.setInt(1, employee.getId());

			if (preparedStmnt.executeUpdate() > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.getMessage();
			return false;
		}
	}

	@Override
	public Employee insertEmployee(Employee employee) {
		try {
			Connection connect = getConnection();
			connect.setAutoCommit(false);
			PreparedStatement preparedStmnt = connect.prepareStatement(INSERT_EMPLOYEES_PGDB,
					Statement.RETURN_GENERATED_KEYS);

			preparedStmnt.setString(1, employee.getName());
			preparedStmnt.setString(2, employee.getEmail());
			preparedStmnt.setString(3, employee.getPassword());
			preparedStmnt.setString(4, employee.getType());
			preparedStmnt.execute();

			ResultSet rs = preparedStmnt.getGeneratedKeys();
			if (rs.next()) {
				employee.setId(rs.getInt(1));
				connect.commit();
			} else {
				connect.rollback();
			}
			connect.close();
		} catch (SQLException e) {
			e.getMessage();
		}
		return employee;

	}
}
