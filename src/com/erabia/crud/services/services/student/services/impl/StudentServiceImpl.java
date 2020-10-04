package com.erabia.crud.services.services.student.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.erabia.crud.services.bean.Student;
import com.erabia.crud.services.services.dao.StudentDAO;
import com.erabia.crud.services.services.dao.csv.impl.StudentDAOCSVImpl;
import com.erabia.crud.services.services.dao.jdbc.impl.StudentDAOJDBCImpl;
import com.erabia.crud.services.services.student.services.StudentService;

public class StudentServiceImpl implements StudentService {

	private static StudentService uniqueInstance;

	private StudentServiceImpl() {
	}

	public static StudentService getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new StudentServiceImpl();
		}
		return uniqueInstance;
	}

	@Override
	public double getAvarageById(String studentId)
			throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		StudentDAO studentDAO = StudentDAOJDBCImpl.getInstance();
		Student student = studentDAO.getStudent(studentId);
		double[] grades = student.getGrades();
		double avg = 0;
		for (int i = 0; i < grades.length; i++) {
			avg += grades[i];
		}
		return avg / grades.length;
	}

	@Override
	public void addStudent(Student student) throws ClassNotFoundException, IOException, SQLException {
		StudentDAO studentDAO = StudentDAOJDBCImpl.getInstance();
		studentDAO.addStudent(student);
	}

	@Override
	public void deleteStudent(String studentId) throws ClassNotFoundException, IOException, SQLException {
		StudentDAO studentDAO = StudentDAOJDBCImpl.getInstance();
		studentDAO.deleteStudent(studentId);
	}

	@Override
	public void updateStudent(Student student) throws ClassNotFoundException, IOException, SQLException {
		deleteStudent(student.getStudentId());
		addStudent(student);
	}

	@Override
	public Student getStudentById(String studentId) throws ClassNotFoundException, IOException, SQLException {
		StudentDAO studentDAO = StudentDAOJDBCImpl.getInstance();
		Student student = studentDAO.getStudent(studentId);
		return student;
	}

}
