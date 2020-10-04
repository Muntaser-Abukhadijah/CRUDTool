package com.erabia.crud.services.services.dao.jdbc.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.erabia.crud.services.bean.Student;
import com.erabia.crud.services.services.dao.StudentDAO;

public class StudentDAOJDBCImpl implements StudentDAO {

	private static StudentDAO uniqueInstance;
	private String url;
	private String userName;
	private String password;
	Properties properties;
	private String driverName;
	private Connection connection;
	private PreparedStatement preparedStatment;
	private ResultSet resultSet;
	String deleteQuery = "DELETE FROM studentinfo WHERE StudentId= ?";
	String insertQuery = "INSERT INTO studentinfo (StudentId, first_Name, last_Name, grade1, grade2, grade3) VALUES (?, ?, ?, ?, ?,?)";
	String selectQuery = "SELECT * FROM studentinfo WHERE StudentId=?";

	private StudentDAOJDBCImpl() {

	}

	public static StudentDAO getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new StudentDAOJDBCImpl();
			return uniqueInstance;
		}
		return uniqueInstance;
	}

	private void openConnection() throws ClassNotFoundException, SQLException, IOException {
		properties = new Properties();
		FileReader reader = null;
		try {
			reader = new FileReader("C:/Users/Abukhadijah/eclipse-workspace/CRUDServices/resources/config.properties");
			properties.load(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		url = properties.getProperty("dataBaseURL");
		userName = properties.getProperty("userName");
		password = properties.getProperty("password");
		driverName = properties.getProperty("driverName");
		Class.forName(driverName);
		connection = DriverManager.getConnection(url, userName, password);
	}

	private void closeConnection() {
		try {
			preparedStatment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteStudent(String studentId) throws IOException, ClassNotFoundException, SQLException {
		openConnection();
		preparedStatment = connection.prepareStatement(deleteQuery);
		preparedStatment.setInt(1, Integer.parseInt(studentId));
		int counter = preparedStatment.executeUpdate();
		closeConnection();
	}

	@Override
	public void addStudent(Student student) throws IOException, ClassNotFoundException, SQLException {
		openConnection();

		preparedStatment = connection.prepareStatement(insertQuery);
		preparedStatment.setInt(1, Integer.parseInt(student.getStudentId()));
		preparedStatment.setString(2, student.getFirstName());
		preparedStatment.setString(3, student.getLastName());
		double[] grades = student.getGrades();
		preparedStatment.setDouble(4, grades[0]);
		preparedStatment.setDouble(5, grades[1]);
		preparedStatment.setDouble(6, grades[2]);
		int counter = preparedStatment.executeUpdate();

		closeConnection();
	}

	@Override
	public void updateStudent(Student student) throws IOException, ClassNotFoundException, SQLException {
		deleteStudent(student.getStudentId());
		addStudent(student);
	}

	@Override
	public Student getStudent(String studentId) throws IOException, ClassNotFoundException, SQLException {
		openConnection();
		preparedStatment = connection.prepareStatement(selectQuery);
		preparedStatment.setInt(1, Integer.parseInt(studentId));
		resultSet = preparedStatment.executeQuery();
		resultSet.next();

		int id = resultSet.getInt("StudentId");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		double[] grade = new double[3];
		grade[0] = resultSet.getDouble("grade1");
		grade[1] = resultSet.getDouble("grade2");
		grade[2] = resultSet.getDouble("grade3");
		closeConnection();
		return new Student(String.valueOf(id), firstName, lastName, grade);
	}
}
