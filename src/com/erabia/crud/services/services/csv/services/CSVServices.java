package com.erabia.crud.services.services.csv.services;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.erabia.crud.services.services.csv.services.comparable.types.ComparableKey;

public interface CSVServices {

	void printData(Map<Integer, String[]> data);

	void printData(List<String[]> data);

	int size(String url, String seperator) throws FileNotFoundException;

	void clearFile(String url);

	Map<Integer, String[]> getData(String url, String seperator, boolean isHeader) throws FileNotFoundException; // Read

	String[] getRowByIndex(String url, String seperator, boolean isHeader, int index) throws FileNotFoundException;

	void append(String url, String seperator, String[] key) throws FileNotFoundException; // Add one records

	void append(String url, String seperator, List<String[]> keys) throws FileNotFoundException; // Add group of records

	void insert(String url, String seperator, int index, String[] key) throws FileNotFoundException; // Insert element

	void insert(String url, String seperator, int index, List<String[]> keys) throws FileNotFoundException; // Insert
																											// group of
																											// records

	void replace(String url, String seperator, String oldKey, String newKey) throws FileNotFoundException; // replace
																											// one field

	void replace(String url, String seperator, List<String> oldKeys, List<String> newKeys) throws FileNotFoundException; // replace
																															// group
																															// of
																															// fields

	List<String[]> search(String url, String seperator, boolean isHeader, String key, int... col)
			throws FileNotFoundException; // search on key, in list of columns.

	List<String[]> search(String url, String seperator, boolean isHeader, List<String> key, int... col)
			throws FileNotFoundException; // search on list of keys, in list of columns.

	List<String[]> search(String url, String seperator, boolean isHeader, Map<Integer, List<ComparableKey>> key)
			throws FileNotFoundException; // Search based on local operations

	void delete(String url, String seperator, String key, int... col) throws FileNotFoundException; // Delete Key in
																									// specific columns

	void delete(String url, String seperator, List<String> key, int... col) throws FileNotFoundException; // Delete List
																											// of key
																											// from
																											// specific
																											// columns

	void deleteColumnByIndex(String url, String seperator, int... col) throws FileNotFoundException; // Delete column/s

	void mergeCSVFiles(String firstFileurl, String firstFileSeperator, String secondFileurl, String secondFileSeperator,
			String resultFileurl, String resultFileSeperator) throws FileNotFoundException; // merge two files

	boolean compareFiles(String firstFileurl, String firstFileSeperator, String secondFileurl,
			String secondFileseperator) throws FileNotFoundException; // compare two files

	void setCell(String url, String seperator, int row, int column, String Key) throws FileNotFoundException; // Set
																												// value
																												// to
																												// specific
																												// cell

	void swapRows(String url, String seperator, int firstRow, int secondRow) throws FileNotFoundException; // Swap two
																											// rows

	void swapColumns(String url, String seperator, int firstColumn, int secondColumn) throws FileNotFoundException; // Swap
																													// two
																													// rows

	void orderByColumnId(String url, String seperator, int col); // Sort CSV file

	void orderByColumnIdReverse(String url, String seperator, int col); // Sort CSV file in reverse order

	String mostFrequentValue(String url, String seperator, int... col) throws FileNotFoundException; // Find the value
																										// that has the
																										// most number
																										// of
																										// occurrences.

	String leastFrequentValue(String url, String seperator, int... col) throws FileNotFoundException; // Find the value
																										// that has the
																										// least number
																										// of
																										// occurrence.

	int count(String url, String seperator, String key, int... col) throws FileNotFoundException; // Frequency of
																									// element

}
