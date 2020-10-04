package com.erabia.crud.services.driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.erabia.crud.services.bean.Student;
import com.erabia.crud.services.services.csv.services.CSVServices;
import com.erabia.crud.services.services.csv.services.impl.CSVServicesImpl;
import com.erabia.crud.services.services.dao.StudentDAO;
import com.erabia.crud.services.services.dao.jdbc.impl.StudentDAOJDBCImpl;

public class CrudServices {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		
//		Student student = new Student("99","AnasSSSSS","Abukhadijah",100,99,98);
		StudentDAO studentDAO = StudentDAOJDBCImpl.getInstance();
//		studentDAO.updateStudent(student);
		
		Student student =  studentDAO.getStudent("99");
		System.out.println(student.getFirstName());
	}

}
