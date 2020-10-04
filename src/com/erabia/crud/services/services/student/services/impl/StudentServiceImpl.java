package com.erabia.crud.services.services.student.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.erabia.crud.services.bean.Student;
import com.erabia.crud.services.services.dao.StudentDAO;
import com.erabia.crud.services.services.dao.csv.impl.StudentDAOCSVImpl;
import com.erabia.crud.services.services.student.services.StudentService;

public class StudentServiceImpl implements StudentService {

	private static StudentService uniqueInstance;

	private StudentServiceImpl() {
	}

	@Override
	public double getAvarageById(String path, String seperator, String studentId) throws IOException {
		StudentDAO studentDAO = StudentDAOCSVImpl.getInstance();
		Student student = studentDAO.getStudent(studentId);   //getStudent(studentId);
		double avarage = 0;
		double[] grades = student.getGrades();
		for (int i = 0; i < grades.length; i++) {
			avarage += grades[i];
		}
		return avarage / grades.length;
	}

	@Override
	public void addStudent(String path, String seperator, Student student) throws FileNotFoundException {
		StudentDAO studentDAO = new StudentDAOCSVImpl();
		studentDAO.addStudent(path, seperator, student);
	}

	@Override
	public void deleteStudent(String path, String seperator, String studentId) throws FileNotFoundException {
		StudentDAO studentDAO = new StudentDAOCSVImpl();
		studentDAO.deleteStudent(path, seperator, studentId);
	}

	@Override
	public void updateStudent(String path, String seperator, String studentId, Student student) throws FileNotFoundException {
		StudentDAO studentDAO = new StudentDAOCSVImpl();
		studentDAO.updateStudent(path, seperator, studentId, student);
	}

	@Override
	public void getStudentById(String path, String seperator, String studentId) throws FileNotFoundException {
		StudentDAO studentDAO = new StudentDAOCSVImpl();
		studentDAO.getStudent(path, seperator, studentId);	
	}

	public static StudentService getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new StudentServiceImpl();
		}
		return uniqueInstance;
	}

}
