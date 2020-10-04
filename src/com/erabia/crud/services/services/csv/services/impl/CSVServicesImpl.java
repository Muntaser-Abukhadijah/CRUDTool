package com.erabia.crud.services.services.csv.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.erabia.crud.services.services.csv.services.CSVServices;
import com.erabia.crud.services.services.csv.services.comparable.types.ComparableKey;
import com.erabia.crud.services.services.dao.StudentDAO;

public class CSVServicesImpl implements CSVServices {
	
	private static CSVServices uniqueInstance;
	
	private CSVServicesImpl() {
		
	}
	
	public static CSVServices getInstance() {
		if(uniqueInstance==null) {
			uniqueInstance =new CSVServicesImpl();
			return uniqueInstance;
		}
		return uniqueInstance;
	}

	private void checkurl(String url) {
		if (url == null)
			throw new IllegalArgumentException("Null is not a valid value as a url");
	}

	private void checkSeperator(String seperator) {
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException(seperator + " is not a valid value as a seperator");
	}

	private void checkKey(String[] keys) {
		if (keys == null)
			throw new IllegalArgumentException("Null is not a valid value as keys");
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == null)
				throw new IllegalArgumentException("Null is not a valid value as one of the key fields");
		}
	}

	private void checkKey(String key) {
		if (key == null)
			throw new IllegalArgumentException("Null is not a valid value as a key");
	}

	private void checkKey(List<String[]> keys) {
		if (keys == null)
			throw new IllegalArgumentException("Null is not a valid value as keys");
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i) == null)
				throw new IllegalArgumentException("Null is not a valid value as keys");
			for (int j = 0; j < keys.get(i).length; j++) {
				if (keys.get(i)[j] == null)
					throw new IllegalArgumentException("Null is not a valid value as one of the key fields");
			}
		}
	}

	private void checkColumn(int... column) {
		if (column == null)
			throw new IllegalArgumentException("null is not a valid value as column");
	}

	@Override
	public Map<Integer, String[]> getData(String url, String seperator, boolean isHeader) throws FileNotFoundException {
		checkurl(url);
		checkSeperator(seperator);

		String csvFile = url;
		String newLine = "";
		int rowNumber = 1;
		Map<Integer, String[]> data = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((newLine = br.readLine()) != null) {
				String record[] = newLine.split(seperator);
				if (isHeader) {
					isHeader = false;
					continue;
				}
				data.put(rowNumber++, record);
			}
		} catch (IOException e) {
			throw new FileNotFoundException("Specified url does not exist");
		}
		return data;
	}

	@Override
	public void append(String url, String seperator, String[] keys) throws FileNotFoundException {

		checkurl(url);
		checkSeperator(seperator);
		checkKey(keys);

		try (FileWriter csvWriter = new FileWriter(new File(url), true)) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < keys.length; i++) {
				sb.append(keys[i]);
				if (i + 1 != keys.length)
					sb.append(seperator);
				else
					sb.append('\n');
			}
			csvWriter.write(sb.toString());
		} catch (IOException e) {
			throw new FileNotFoundException("Specified url does not exist");
		}
	}

	@Override
	public void append(String url, String seperator, List<String[]> keys) throws FileNotFoundException {
		checkurl(url);
		checkSeperator(seperator);
		checkKey(keys);

		for (int i = 0; i < keys.size(); i++) {
			append(url, seperator, keys.get(i));
		}
	}

	@Override
	public void insert(String url, String seperator, int index, String[] key) throws FileNotFoundException {

		checkurl(url);
		checkSeperator(seperator);
		checkKey(key);

		List<String[]> list = new ArrayList<>();
		list.add(key);
		insert(url, seperator, index, list);

	}

	@Override
	public void insert(String url, String seperator, int index, List<String[]> keys) throws FileNotFoundException {

		checkurl(url);
		checkSeperator(seperator);
		checkKey(keys);

		Map<Integer, String[]> data = getData(url, seperator, false);

		if (index - 1 > data.size() || index <= 0) {
			throw new IllegalArgumentException("Index out of bounday");
		} else if (index == data.size() + 1) {
			for (int i = 0; i < keys.size(); i++) {
				append(url, seperator, keys.get(i));
			}
		} else {
			clearFile(url);
			for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
				if (entry.getKey() == index) {
					for (int i = 0; i < keys.size(); i++) {
						append(url, seperator, keys.get(i));
					}
				}
				append(url, seperator, entry.getValue());
			}
		}
	}

	@Override
	public void replace(String url, String seperator, String oldKey, String newKey) throws FileNotFoundException {
		checkurl(url);
		checkSeperator(seperator);
		checkKey(oldKey);
		checkKey(newKey);

		List<String> oldList = new ArrayList<>();
		List<String> newList = new ArrayList<>();
		oldList.add(oldKey);
		newList.add(newKey);

		replace(url, seperator, oldList, newList);
	}

	@Override
	public void replace(String url, String seperator, List<String> oldKeys, List<String> newKeys)
			throws FileNotFoundException {

		checkurl(url);
		checkSeperator(seperator);

		if (oldKeys == null)
			throw new IllegalArgumentException("Null is not a valied value as keys");
		if (newKeys == null)
			throw new IllegalArgumentException("Null is not a valied value for keys");

		if (oldKeys.size() != newKeys.size())
			throw new IllegalArgumentException("The size of the two lists are not equal");

		for (int i = 0; i < oldKeys.size(); i++) {
			if (oldKeys.get(i) == null)
				throw new IllegalArgumentException("Null is not a valied value as field in keys");
		}

		for (int i = 0; i < newKeys.size(); i++) {
			if (newKeys.get(i) == null)
				throw new IllegalArgumentException("Null is not a valied value as field in keys");
		}

		Map<String, String> orderedOldKeys = new TreeMap<>();
		for (int i = 0; i < oldKeys.size(); i++) {
			orderedOldKeys.put(oldKeys.get(i), newKeys.get(i));
		}

		Map<Integer, String[]> data = getData(url, seperator, false);
		for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
			String[] row = entry.getValue();
			for (int i = 0; i < row.length; i++) {
				if (orderedOldKeys.containsKey(row[i])) {
					row[i] = orderedOldKeys.get(row[i]);
				}
			}
		}
		printFile(url, seperator, data);
	}

	@Override
	public List<String[]> search(String url, String seperator, boolean isHeader, String key, int... column)
			throws FileNotFoundException {

		checkurl(url);
		checkSeperator(seperator);
		checkKey(key);
		checkColumn(column);

		List<String> list = new ArrayList<>();
		list.add(key);
		return search(url, seperator, isHeader, list, column);
	}

	@Override
	public List<String[]> search(String url, String seperator, boolean isHeader, List<String> keys, int... column)
			throws FileNotFoundException {

		checkurl(url);
		checkSeperator(seperator);
		checkColumn(column);
		if (keys == null)
			throw new IllegalArgumentException("Null is not a valied value for keys");

		Set<String> setOfKeys = new HashSet<>();

		for (int i = 0; i < keys.size(); i++) {
			setOfKeys.add(keys.get(i));
		}

		List<String[]> results = new ArrayList<>();
		Set<Integer> rows = new HashSet<>();

		Map<Integer, String[]> data = getData(url, seperator, false);

		if (column.length == 0) {
			int mx = Integer.MIN_VALUE;
			for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
				mx = Math.max(mx, entry.getValue().length);
			}
			column = new int[mx];
			for (int i = 0; i < column.length; i++) {
				column[i] = i + 1;
			}
		}

		for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
			String[] row = entry.getValue();
			for (int i = 0; i < column.length; i++) {
				if (column[i] - 1 < row.length && column[i] > 0 && setOfKeys.contains(row[column[i] - 1])) {
					rows.add(entry.getKey());
				}
			}
		}

		for (Integer rowNumber : rows) {
			results.add(data.get(rowNumber));
		}
		return results;
	}

	// Not tested yet (JUnit test)
	@Override
	public List<String[]> search(String url, String seperator, boolean isHeader,

			Map<Integer, List<ComparableKey>> keys) throws FileNotFoundException {

		if (url == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");
		if (keys == null)
			throw new IllegalArgumentException("Null is not a valied value for keys");

		List<String[]> result = new ArrayList<>();
		Map<Integer, String[]> data = getData(url, seperator, isHeader);
		Set<Integer> rowsId = new HashSet<>();

		for (Map.Entry<Integer, String[]> entry1 : data.entrySet()) {
			for (Map.Entry<Integer, List<ComparableKey>> entry2 : keys.entrySet()) {

				String[] valuesInCSVFile = entry1.getValue();
				ArrayList<ComparableKey> valuesToLookFor = (ArrayList) entry2.getValue();

				for (int i = 0; i < valuesToLookFor.size(); i++) {
					if (valuesToLookFor.get(i).compare(valuesInCSVFile[entry2.getKey()],
							valuesToLookFor.get(i).getOperator())) {
						rowsId.add(entry1.getKey());
						break;
					}
				}
			}
		}
		for (Integer rowId : rowsId)
			result.add(data.get(rowId));
		return result;
	}

	@Override
	public void delete(String url, String seperator, String key, int... col) throws FileNotFoundException {
		List<String> list = new ArrayList<>();
		list.add(key);
		delete(url, seperator, list, col);
	}

	@Override
	public void delete(String url, String seperator, List<String> key, int... col) throws FileNotFoundException {

		if (url == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");
		if (key == null)
			throw new IllegalArgumentException("Null is not a valied value for keys");
		if (col == null)
			throw new IllegalArgumentException("Null is not a valied value for columns");

		Map<Integer, String[]> data = getData(url, seperator, false);
		if (col.length > 0) {
			for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
				String[] row = entry.getValue();
				for (int i = 0; i < col.length; i++) {
					if (col[i] >= row.length) {
						throw new IllegalArgumentException("Column number not found!");
					}
					if (key.contains(row[col[i]])) {
						row[col[i]] = "";
					}
				}
			}
		} else {
			for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
				String[] row = entry.getValue();
				for (int i = 0; i < row.length; i++) {
					if (key.contains(row[i])) {
						row[i] = "";
					}
				}
			}
		}
		printFile(url, seperator, data);
	}

	private void printFile(String url, String seperator, Map<Integer, String[]> data) throws FileNotFoundException {
		clearFile(url);
		for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
			append(url, seperator, entry.getValue());
		}
	}

	@Override
	public void deleteColumnByIndex(String url, String seperator, int... col) throws FileNotFoundException {
		if (url == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");
		if (col == null)
			throw new IllegalArgumentException("Null is not a valied value for columns");

		Map<Integer, String[]> data = getData(url, seperator, false);

		Set<Integer> st = new HashSet<>();
		for (int i = 0; i < col.length; i++) {
			st.add(col[i]);
		}

		for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
			String[] row = entry.getValue();
			String[] temp = new String[row.length - col.length];

			int removed = 0;
			for (int i = 0; i < row.length; i++) {
				if (st.contains(i)) {
					removed++;
					continue;
				}
				temp[i - removed] = row[i];
			}
			data.put(entry.getKey(), temp);
		}
		printFile(url, seperator, data);
	}

	@Override
	public void mergeCSVFiles(String firstFileurl, String firstFileSeperator, String secondFileurl,
			String secondFileseperator, String resultFileurl, String resultFileSeperator) throws FileNotFoundException {

		if (firstFileurl == null || secondFileurl == null || resultFileurl == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (firstFileSeperator == null || firstFileSeperator != "," || secondFileseperator == null
				|| secondFileseperator != "," || resultFileSeperator == null || resultFileSeperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");

		Map<Integer, String[]> firstFileData = getData(firstFileurl, firstFileSeperator, false);
		Map<Integer, String[]> secondFileData = getData(secondFileurl, secondFileseperator, false);
		Map<Integer, String[]> result = new HashMap<Integer, String[]>();
		int sizeOfFirstFile = firstFileData.size();
		for (Map.Entry<Integer, String[]> entry : firstFileData.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<Integer, String[]> entry : secondFileData.entrySet()) {
			result.put(entry.getKey() + sizeOfFirstFile, entry.getValue());
		}
		printFile(resultFileurl, resultFileSeperator, result);
	}

	@Override
	public boolean compareFiles(String firstFileurl, String firstFileSeperator, String secondFileurl,
			String secondFileseperator) throws FileNotFoundException {

		if (firstFileurl == null || secondFileurl == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (firstFileSeperator == null || firstFileSeperator != "," || secondFileseperator == null
				|| secondFileseperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");

		Map<Integer, String[]> firstFileData = getData(firstFileurl, firstFileSeperator, false);
		Map<Integer, String[]> secondFileData = getData(secondFileurl, secondFileseperator, false);

		if (firstFileData.size() != secondFileData.size())
			return false;

		Iterator<Map.Entry<Integer, String[]>> iterator1 = firstFileData.entrySet().iterator();
		Iterator<Map.Entry<Integer, String[]>> iterator2 = firstFileData.entrySet().iterator();

		while (iterator1.hasNext() && iterator2.hasNext()) {
			Map.Entry<Integer, String[]> entry1 = iterator1.next();
			Map.Entry<Integer, String[]> entry2 = iterator2.next();
			if (entry1.getKey() == entry2.getKey()) {
				String[] list1 = entry1.getValue();
				String[] list2 = entry2.getValue();
				if (list1.length != list2.length) {
					return false;
				}
				for (int i = 0; i < list1.length; i++) {
					if (!(list1[i].equals(list2[i]))) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public void setCell(String url, String seperator, int row, int column, String key) throws FileNotFoundException {
		if (url == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");
		if (key == null)
			throw new IllegalArgumentException("Null is not a valied value for key");

		Map<Integer, String[]> data = getData(url, seperator, false);
		if (row < 0 || column < 0 || row >= data.size() || column >= data.get(row).length) {
			throw new IllegalArgumentException("row or/and column out of csv file boundary");
		}
		data.get(row)[column] = key;
		printFile(url, seperator, data);
	}

	@Override
	public void swapRows(String url, String seperator, int firstRow, int secondRow) throws FileNotFoundException {

		if (url == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");

		Map<Integer, String[]> data = getData(url, seperator, false);
		if (firstRow < 0 || secondRow < 0 || firstRow >= data.size() || secondRow >= data.size()) {
			throw new IllegalArgumentException("row number is out of csv file boundary");
		}
		String[] temp1 = data.get(firstRow);
		String[] temp2 = data.get(secondRow);
		data.put(secondRow, temp1);
		data.put(firstRow, temp2);
		printFile(url, seperator, data);
	}

	@Override
	public void swapColumns(String url, String seperator, int firstColumn, int secondColumn)
			throws FileNotFoundException {
		if (url == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");

		Map<Integer, String[]> data = getData(url, seperator, false);
		if (firstColumn < 0 || secondColumn < 0 || firstColumn >= data.size() || secondColumn >= data.size()) {
			throw new IllegalArgumentException("row or/and column out of csv file boundary");
		}

		for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
			String[] temp = entry.getValue();
			String val = temp[firstColumn];
			temp[firstColumn] = temp[secondColumn];
			temp[secondColumn] = val;
			data.put(entry.getKey(), temp);
		}
		printFile(url, seperator, data);
	}

	@Override
	public void orderByColumnId(String url, String seperator, int col) {
		// TODO Auto-generated method stub

	}

	@Override
	public void orderByColumnIdReverse(String url, String seperator, int col) {
		// TODO Auto-generated method stub

	}

	@Override
	public String mostFrequentValue(String url, String seperator, int... col) throws FileNotFoundException {

		if (url == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");
		if (col == null)
			throw new IllegalArgumentException("Null is not a valied value for column");

		Map<Integer, String[]> data = getData(url, seperator, false);
		Map<String, Integer> freq = new HashMap<>();
		for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
			String[] row = entry.getValue();
			for (int i = 0; i < row.length; i++) {
				if (freq.containsKey(row[i])) {
					freq.put(row[i], freq.get(row[i]) + 1);
				} else {
					freq.put(row[i], 1);
				}
			}
		}
		int mx = Integer.MIN_VALUE;
		String ans = "";
		for (Map.Entry<String, Integer> entry : freq.entrySet()) {
			if (entry.getValue() > mx) {
				mx = entry.getValue();
				ans = entry.getKey();
			}
		}
		return ans;
	}

	@Override
	public String leastFrequentValue(String url, String seperator, int... col) throws FileNotFoundException {

		if (url == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");
		if (col == null)
			throw new IllegalArgumentException("Null is not a valied value for column");

		Map<Integer, String[]> data = getData(url, seperator, false);
		Map<String, Integer> freq = new HashMap<>();
		for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
			String[] row = entry.getValue();
			for (int i = 0; i < row.length; i++) {
				if (freq.containsKey(row[i])) {
					freq.put(row[i], freq.get(row[i]) + 1);
				} else {
					freq.put(row[i], 1);
				}
			}
		}
		int mi = Integer.MAX_VALUE;
		String ans = "";
		for (Map.Entry<String, Integer> entry : freq.entrySet()) {
			if (entry.getValue() < mi) {
				mi = entry.getValue();
				ans = entry.getKey();
			}
		}
		return ans;

	}

	@Override
	public int count(String url, String seperator, String key, int... col) throws FileNotFoundException {
		if (url == null)
			throw new IllegalArgumentException("Null is not a valied value for url");
		if (seperator == null || seperator != ",")
			throw new IllegalArgumentException("Null is not a valied value for seperator");
		if (col == null)
			throw new IllegalArgumentException("Null is not a valied value for column");
		if (key == null)
			throw new IllegalArgumentException("Null is not a valied value for key");

		int ans = 0;
		Map<Integer, String[]> data = getData(url, seperator, false);
		for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
			String[] row = entry.getValue();
			for (int i = 0; i < row.length; i++) {
				if (row[i].equals(key)) {
					ans++;
				}
			}

		}
		return ans;
	}

	public void clearFile(String url) {
		if (url == null)
			throw new IllegalArgumentException();

		try (FileWriter csvWriter = new FileWriter(url)) {
			csvWriter.append("");
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printData(Map<Integer, String[]> data) {
		for (Map.Entry<Integer, String[]> entry : data.entrySet()) {
			String[] list = entry.getValue();
			for (int i = 0; i < list.length; i++) {
				System.out.print(list[i] + "  ");
			}
			System.out.println();
		}
	}

	public void printData(List<String[]> data) {
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.get(i).length; j++) {
				System.out.print(data.get(i)[j] + "  ");
			}
			System.out.println();
		}
	}

	public int size(String url, String seperator) throws FileNotFoundException {

		Map<Integer, String[]> data = this.getData(url, seperator, false);
		return data.size();
	}

	@Override
	public String[] getRowByIndex(String url, String seperator, boolean isHeader, int index)
			throws FileNotFoundException {
		checkurl(url);
		checkSeperator(seperator);
		Map<Integer, String[]> data = getData(url, seperator, isHeader);
		return data.get(index);
	}
}
