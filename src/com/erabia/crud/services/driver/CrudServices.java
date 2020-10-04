package com.erabia.crud.services.driver;

import java.io.FileNotFoundException;

import com.erabia.crud.services.services.csv.services.CSVServices;
import com.erabia.crud.services.services.csv.services.impl.CSVServicesImpl;

public class CrudServices {

	public static void main(String[] args) throws FileNotFoundException {
		String url = "C:/Users/Abukhadijah/Desktop/csvSource.csv";
		CSVServices csvServices = CSVServicesImpl.getInstance();
		String[] val = new String[] { "this", "ZZZZZZZZZZ", "new", "val" };
		csvServices.insert(url, ",", 1, val);
	}

}
