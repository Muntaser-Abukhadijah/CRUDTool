package com.erabia.crud.services.services.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.erabia.crud.services.bean.Student;

public interface StudentDAO {

	public void deleteStudent(String StudentId) throws IOException, ClassNotFoundException, SQLException;
	public void addStudent(Student student) throws IOException, ClassNotFoundException, SQLException;
	public void updateStudent(Student student) throws IOException,ClassNotFoundException, SQLException;
	public Student getStudent(String studentId) throws IOException,ClassNotFoundException, SQLException;

}
