package com.erabia.crud.services.services.dao.csv.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import com.erabia.crud.services.bean.Student;
import com.erabia.crud.services.services.csv.services.CSVServices;
import com.erabia.crud.services.services.csv.services.impl.CSVServicesImpl;
import com.erabia.crud.services.services.dao.StudentDAO;

public class StudentDAOCSVImpl implements StudentDAO {

	private static StudentDAO uniqueInstance;

	Properties properties;
	private String url;
	private String seperator;
	private boolean isHead;

	private StudentDAOCSVImpl() {

	}

	{
		FileReader reader = null;
		try {
			reader = new FileReader("resources/config.properites");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			properties.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		url = properties.getProperty("CSVFileURL");
		seperator = properties.getProperty("CSVFileSeperator");
		isHead = Boolean.parseBoolean(properties.getProperty("IsHead"));
	}


	public static StudentDAO getInstance() throws IOException {
		if (uniqueInstance == null) {
			uniqueInstance = new StudentDAOCSVImpl();
			return uniqueInstance;
		}
		return uniqueInstance;
	}

	@Override
	public void deleteStudent(String StudentId) throws IOException {
		CSVServices csvFileService = CSVServicesImpl.getInstance();
		csvFileService.delete(url, seperator, StudentId);
	}

	@Override
	public void addStudent(Student student) throws IOException {
		String[] studentInfo = studentToArray(student);
		CSVServices csvFileService = CSVServicesImpl.getInstance();
		csvFileService.append(url, seperator, studentInfo);
	}

	@Override
	public void updateStudent(Student student) throws IOException {
		CSVServices csvFileService = CSVServicesImpl.getInstance();
		csvFileService.delete(url, seperator, student.getStudentId());
		String[] studentInfo = studentToArray(student);
		csvFileService.append(url, seperator, studentInfo);
	}

	@Override
	public Student getStudent(String studentId) throws IOException {
		CSVServices csvFileService = CSVServicesImpl.getInstance();
		List<String[]> studentInfo = csvFileService.search(url, seperator, false, studentId, 1);
		return arrayToStudent(studentInfo.get(0));
	}

	private String[] studentToArray(Student student) {
		List<String> list = new ArrayList<>();
		list.add(student.getStudentId());
		list.add(student.getFirstName());
		list.add(student.getLastName());
		double[] grade = student.getGrades();
		for (int i = 0; i < grade.length; i++) {
			list.add(String.valueOf(grade[i]));
		}
		return (String[]) list.toArray();
	}

	private Student arrayToStudent(String[] studentInfo) {
		String studentId = studentInfo[0];
		String firstName = studentInfo[1];
		String lastName = studentInfo[2];
		double[] grades = new double[studentInfo.length - 3];
		for (int i = 0; i < grades.length; i++) {
			grades[i] = Double.parseDouble(studentInfo[i + 3]);
		}
		Student student = new Student(studentId, firstName, lastName, grades);
		return student;
	}
}
