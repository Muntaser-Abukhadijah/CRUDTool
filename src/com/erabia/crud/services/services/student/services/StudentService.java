package com.erabia.crud.services.services.student.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.erabia.crud.services.bean.Student;

public interface StudentService {
	
	public abstract double getAvarageById(String studentId) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException;

	public abstract void addStudent(Student Student) throws FileNotFoundException,IOException, ClassNotFoundException, SQLException;

	public abstract void deleteStudent(String studentId) throws FileNotFoundException,IOException, ClassNotFoundException, SQLException;

	public abstract void updateStudent(Student student) throws FileNotFoundException,IOException, ClassNotFoundException, SQLException;

	public abstract Student getStudentById(String studentId) throws FileNotFoundException,IOException, ClassNotFoundException, SQLException;
	
}
