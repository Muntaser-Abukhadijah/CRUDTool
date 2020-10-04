package com.erabia.crud.services.services.student.services;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.erabia.crud.services.bean.Student;

public interface StudentService {
	
	double getAvarageById(String path, String seperator, String studentId) throws FileNotFoundException, IOException;

	void addStudent(String path, String seperator, Student Student) throws FileNotFoundException;

	void deleteStudent(String path, String seperator, String studentId) throws FileNotFoundException;

	void updateStudent(String path, String seperator, String studentId, Student student) throws FileNotFoundException;

	void getStudentById(String path, String seperator, String studentId) throws FileNotFoundException;
	
}
