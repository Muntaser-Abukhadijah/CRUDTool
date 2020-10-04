package com.erabia.crud.services.bean;

public class Student {

	private String studentId;
	private String firstName;
	private String lastName;
	private double[] grades;

	public Student(String studentId, String firstName, String lastName, double... grades) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.grades = grades;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public double[] getGrades() {
		return grades;
	}

	public void setGrades(double[] grades) {
		this.grades = grades;
	}
}
