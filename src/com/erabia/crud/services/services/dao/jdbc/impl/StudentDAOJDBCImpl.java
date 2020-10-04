package com.erabia.crud.services.services.dao.jdbc.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
	private Statement statment;
	private ResultSet resultSet;

	private StudentDAOJDBCImpl() {

	}

	private void openConnection() throws ClassNotFoundException, SQLException, IOException {
		FileReader reader = null;
		try {
			reader = new FileReader("resources/config.properites");
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
		statment = connection.createStatement();

	}

	private void closeConnection() {
		try {
			statment.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static StudentDAO getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new StudentDAOJDBCImpl();
			return uniqueInstance;
		}
		return uniqueInstance;
	}

	@Override
	public void deleteStudent(String studentId) throws IOException, ClassNotFoundException, SQLException {
		openConnection();
		String query = "DELETE FROM Student WHERE StudentId= studentId";
		resultSet = statment.executeQuery(query);
		closeConnection();
	}

	@Override
	public void addStudent(Student student) throws IOException, ClassNotFoundException, SQLException {
		openConnection();
		String query = "INSERT INTO Student (StudentId, firstName, lastName, grade1, grade2, grade3) VALUES (student.getStudentId, student.firstName, student.getLastName, student.getGrades[0], student.getGrades[1],student.getGrades[2] ";
		resultSet = statment.executeQuery(query);
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
		String query = "SELECT * FROM Student WHERE StudentId=studentId";
		resultSet = statment.executeQuery(query);
		closeConnection();
		String id = resultSet.getString("StudentId");
		String firstName = resultSet.getString("StudentFirstName");
		String lastName = resultSet.getString("StudentSecondName");
		double[] grade = new double[3];
		grade[0] = resultSet.getDouble(3);
		grade[1] = resultSet.getDouble(4);
		grade[2] = resultSet.getDouble(5);
		return new Student(id,firstName,lastName,grade);
	}
}
