package test.services.csv.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.erabia.crud.services.services.csv.services.CSVServices;
import com.erabia.crud.services.services.csv.services.impl.CSVServicesImpl;

public class CSVServicesTest {

	private String url = "C:/Users/Abukhadijah/Desktop/csvSource.csv";
	private String seperator = ",";
	CSVServices csvServices = CSVServicesImpl.getInstance();

	@BeforeEach
	private void clearCSVFile() {
		csvServices.clearFile(url);
	}

	@Test
	public void getData_NullUrl_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.getData(null, seperator, true));
	}

	@Test
	public void getData_EmptySeperator_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.getData(url, null, true));
	}

	@Test
	public void getData_InvalidPath_ExceptionThrown() {
		Assertions.assertThrows(FileNotFoundException.class, () -> csvServices.getData("zzz", seperator, true));
	}

	@Test
	public void getData_GettingEmptyMap_MapOfData() throws FileNotFoundException {
		csvServices.printData(csvServices.getData(url, seperator, true));
		assertEquals(0, csvServices.getData(url, seperator, true).size());
	}

	@Test
	public void append_AppendNullValue_ExceptionThrown() throws FileNotFoundException {
		String[] val = null;
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.append(url, seperator, val));
	}

	@Test
	public void append_AppendNullUrl_ExceptionThrown() throws FileNotFoundException {
		String[] val = new String[] { "this", "is", "new", "val" };
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.append(null, seperator, val));
	}

	@Test
	public void append_AppendNullField_ExcetionThrown() {
		String[] val = new String[] { "this", null, "new", "val" };
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.append(url, seperator, val));
	}

	@Test
	public void append_AppendValidValue_EqualSize() throws FileNotFoundException {
		String[] val = new String[] { "this", "is", "new", "val" };
		csvServices.append(url, seperator, val);
		csvServices.append(url, seperator, val);
		assertEquals(2, csvServices.size(url, seperator));
	}
	// List<String[]> keys

	@Test
	public void append_AppendListOfValidValues_EqualSize() throws FileNotFoundException {
		String[] val = new String[] { "this", "is", "new", "val" };
		List<String[]> keys = new ArrayList<>();
		keys.add(val);
		keys.add(val);
		keys.add(val);
		csvServices.append(url, seperator, keys);
		csvServices.append(url, seperator, keys);
		assertEquals(6, csvServices.size(url, seperator));
	}

	@Test
	public void append_AppendNullAsList_ExceptionThrown() throws FileNotFoundException {
		List<String[]> keys = null;
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.append(url, seperator, keys));
	}

	@Test
	public void append_AppendNullAsValueInList_ExceptionThrown() throws FileNotFoundException {
		List<String[]> keys = new ArrayList<>();
		String[] val = new String[] { "this", null, "new", "val" };
		keys.add(val);
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.append(url, seperator, keys));
	}

	@Test
	public void insert_InsertNullUrl_ExceptionThrown() throws FileNotFoundException {
		String[] keys = { "This", "Is", "From", "Insertion", "Method" };
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.insert(null, seperator, 1, keys));
	}

	@Test
	public void insert_InsertNullAsListOfKeys_ExceptionThrown() throws FileNotFoundException {
		List<String[]> keys = null;
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.insert(url, seperator, 1, keys));
	}

	@Test
	public void insert_InsertNullAsValueInListOfKeys_ExceptionThrown() throws FileNotFoundException {
		List<String[]> keys = new ArrayList<>();
		String[] val = { "This", "Is", null, "Insertion", "Method" };
		keys.add(val);
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.insert(url, seperator, 1, keys));
	}

	@Test
	public void insert_InsertNullAsSeperator_ExceptionThrown() throws FileNotFoundException {
		String[] keys = { "This", "Is", "From", "Insertion", "Method" };
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.insert(url, null, 1, keys));
	}

	@Test
	public void insert_InsertNullAsKeys_ExceptionThrown() throws FileNotFoundException {
		String[] keys = null;
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.insert(url, seperator, 1, keys));
	}

	@Test
	public void insert_InsertNullAsFieldInKeys_ExceptionThrown() throws FileNotFoundException {
		String[] keys = { "This", "Is", null, "Insertion", "Method" };
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.insert(url, seperator, 1, keys));
	}

	@Test
	public void insert_InsertWithInvalidIndex_ExceptionThrown() throws FileNotFoundException {
		String[] keys = { "This", "Is", "zz", "Insertion", "Method" };
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.insert(url, seperator, 3, keys));
	}

	@Test
	public void insert_InsertWithNegativeIndex_ExceptionThrown() throws FileNotFoundException {
		String[] keys = { "This", "Is", "zz", "Insertion", "Method" };
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.insert(url, seperator, -3, keys));
	}

	@Test
	public void insert_InsertWithValidValue_Equality() throws FileNotFoundException {
		String[] keys = { "This", "Is", "zz", "Insertion", "Method" };
		csvServices.insert(url, seperator, 1, keys);
		assertEquals(1, csvServices.size(url, seperator));
	}

	@Test
	public void insert_InsertWithValidValues_Equality() throws FileNotFoundException {
		String[] keys = { "This", "Is", "zz", "Insertion", "Method" };
		csvServices.insert(url, seperator, 1, keys);
		csvServices.insert(url, seperator, 1, keys);
		csvServices.insert(url, seperator, 1, keys);
		assertEquals(3, csvServices.size(url, seperator));
	}

	@Test
	public void insert_InsertWithValidValuesAsList_Equality() throws FileNotFoundException {
		List<String[]> keys = new ArrayList<>();
		String[] val = { "This", "Is", "", "Insertion", "Method" };
		keys.add(val);
		keys.add(val);
		keys.add(val);
		csvServices.insert(url, seperator, 1, keys);
		csvServices.insert(url, seperator, 1, keys);
		csvServices.insert(url, seperator, 1, keys);
		assertEquals(9, csvServices.size(url, seperator));
	}

	@Test
	public void insert_CheckTheInsertionIndex_Equality() throws FileNotFoundException {
		List<String[]> keys = new ArrayList<>();
		String[] val1 = { "1", "1", "1", "1", "1" };
		String[] val2 = { "2", "2", "2", "2", "2" };
		String[] val3 = { "3", "3", "3", "3", "3" };
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		keys.add(val1);
		keys.add(val2);
		keys.add(val3);
		csvServices.insert(url, seperator, 2, keys);
		String[] res1 = csvServices.getRowByIndex(url, seperator, false, 2);
		String[] res2 = csvServices.getRowByIndex(url, seperator, false, 3);
		String[] res3 = csvServices.getRowByIndex(url, seperator, false, 4);
		assertEquals(val1.length, res1.length);
		assertEquals(val2.length, res2.length);
		assertEquals(val3.length, res3.length);
		for (int i = 0; i < val1.length; i++) {
			assertEquals(val1[i], res1[i]);
		}
		for (int i = 0; i < val2.length; i++) {
			assertEquals(val2[i], res2[i]);
		}
		for (int i = 0; i < val3.length; i++) {
			assertEquals(val3[i], res3[i]);
		}
	}

	@Test
	public void insert_InsertAtTheEnd_Equality() throws FileNotFoundException {
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		String[] keys = { "This", "Is", "zz", "Insertion", "Method" };
		csvServices.insert(url, seperator, 4, keys);
		assertEquals(4, csvServices.size(url, seperator));
	}

	@Test
	public void insert_InsertAfterTheEnd_ExceptionThrown() throws FileNotFoundException {
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		String[] keys = { "This", "Is", "zz", "Insertion", "Method" };
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.insert(url, seperator, 5, keys));
	}

	@Test
	public void insert_InsertAtTheBegining_Equality() throws FileNotFoundException {
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		csvServices.append(url, seperator, new String[] { "zzz", "zzz", "zz" });
		String[] keys = { "This", "Is", "zz", "Insertion", "Method" };
		csvServices.insert(url, seperator, 1, keys);
		String[] res = csvServices.getRowByIndex(url, seperator, false, 1);
		assertEquals(keys.length, res.length);
		for (int i = 0; i < keys.length; i++) {
			assertEquals(keys[i], res[i]);
		}
	}

	@Test
	public void repalce_ReplaceWithNullUrl_ExcpetionThrown() {
		String oldKey = "mun", newKey = "Muntaser";
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.replace(null, seperator, oldKey, newKey));
	}

	@Test
	public void repalce_ReplaceWithNullAsSeperator_ExcpetionThrown() {
		String oldKey = "mun", newKey = "Muntaser";
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.replace(url, null, oldKey, newKey));
	}

	@Test
	public void repalce_ReplaceWithNullAsOldKey_ExcpetionThrown() {
		String oldKey = "mun", newKey = "Muntaser";
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.replace(url, seperator, null, newKey));
	}

	@Test
	public void repalce_ReplaceWithNullAsNewKey_ExcpetionThrown() {
		String oldKey = "mun", newKey = "Muntaser";
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.replace(url, seperator, oldKey, null));
	}

	@Test
	public void repalce_ReplaceWithNullAsOldKeyList_ExcpetionThrown() {
		List<String> newList = new ArrayList<>();
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.replace(url, seperator, null, newList));
	}

	@Test
	public void repalce_ReplaceWithNullAsNewKeyList_ExcpetionThrown() {
		List<String> oldList = new ArrayList<>();
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.replace(url, seperator, oldList, null));
	}

	@Test
	public void repalce_ReplaceWithNullAsFieldInNewKeyList_ExcpetionThrown() {
		List<String> newList = new ArrayList<>();
		List<String> oldList = new ArrayList<>();
		oldList.add("mun");
		newList.add(null);
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.replace(url, seperator, oldList, newList));
	}

	@Test
	public void repalce_ReplaceWithNullAsFieldInOldKeyList_ExcpetionThrown() {
		List<String> oldList = new ArrayList<>();
		List<String> newList = new ArrayList<>();
		oldList.add(null);
		newList.add("muntaser");
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.replace(url, seperator, oldList, newList));
	}

	@Test
	public void repalce_ReplaceWithRightValues_Equality() throws FileNotFoundException {
		String[] key = { "My", "Name", "Is", "Mun" };
		String[] key2 = { "Mun", "Is", "My", "Name" };
		csvServices.append(url, seperator, key);
		csvServices.append(url, seperator, key2);
		key[3] = "Muntaser";
		key2[0] = "Muntaser";
		csvServices.replace(url, seperator, "Mun", "Muntaser");
		String[] val = csvServices.getRowByIndex(url, seperator, false, 1);
		String[] val2 = csvServices.getRowByIndex(url, seperator, false, 2);
		assertEquals(key.length, val.length);
		assertEquals(key2.length, val2.length);
		for (int i = 0; i < key.length; i++) {
			assertEquals(key[i], val[i]);
		}
		for (int i = 0; i < key2.length; i++) {
			assertEquals(key2[i], val2[i]);
		}
	}

	@Test
	public void repalce_ReplaceNotExistingValue_Equality() throws FileNotFoundException {
		String[] key = { "My", "Name", "Is", "Mun" };
		String[] key2 = { "Mun", "Is", "My", "Name" };
		csvServices.append(url, seperator, key);
		csvServices.append(url, seperator, key2);
		csvServices.replace(url, seperator, "Munt", "Muntaser");
		String[] val = csvServices.getRowByIndex(url, seperator, false, 1);
		String[] val2 = csvServices.getRowByIndex(url, seperator, false, 2);
		assertEquals(key.length, val.length);
		assertEquals(key2.length, val2.length);
		assertNotEquals("Muntaser", key[3]);
		assertNotEquals("Muntaser", key2[0]);
	}

	@Test
	public void search_SearchWithNullAsUrl_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.search(null, seperator, true, "test"));
	}

	@Test
	public void search_SearchWithNullAsSeperator_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.search(url, null, true, "test"));
	}

	@Test
	public void search_SearchWithNullAsKey_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.search(url, seperator, true, null));
	}

	@Test
	public void search_SearchWithNullColNumber_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.search(url, seperator, true, "zz", null));
	}

	@Test
	public void search_SearchOnNotExistingKey_SizeEquality() throws FileNotFoundException {
		String[] keys = { "This", "Is", "Line", "To", "Serach", "For", "Word", "In" };
		csvServices.append(url, seperator, keys);
		assertEquals(0, csvServices.search(url, seperator, false, "Muntaser").size());
	}

	@Test
	public void search_SearchOnExistingKey_SizeEquality() throws FileNotFoundException {
		String[] keys = { "This", "Is", "Line", "To", "Serach", "For", "Word", "In" };
		csvServices.append(url, seperator, keys);
		assertEquals(1, csvServices.search(url, seperator, false, "Word").size());
	}

	@Test
	public void search_SearchOnExistingKey_CheckDublicateValues() throws FileNotFoundException {
		String[] keys = { "This", "Is", "Line", "To", "Serach", "For", "Word", "In" };
		csvServices.append(url, seperator, keys);
		csvServices.append(url, seperator, keys);
		assertEquals(2, csvServices.search(url, seperator, false, "Word").size());
	}

	@Test
	public void search_SearchOnNotExistingKeyInSpecificColumn_SizeEquality() throws FileNotFoundException {
		String[] keys = { "This", "Is", "Line", "To", "Serach", "For", "Word", "In" };
		csvServices.append(url, seperator, keys);
		assertEquals(0, csvServices.search(url, seperator, false, "Muntaser", 1).size());
	}

	@Test
	public void search_SearchOnExistingKeyInSpecificColumn_SizeEquality() throws FileNotFoundException {
		String[] keys = { "This", "Is", "Line", "To", "Serach", "For", "Word", "In" };
		csvServices.append(url, seperator, keys);
		assertEquals(1, csvServices.search(url, seperator, false, "This", 1).size());
	}

////////////
	@Test
	public void searchWithList_SearchWithNullAsUrl_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.search(null, seperator, true, new ArrayList<String>()));
	}

	@Test
	public void searchWithList_SearchWithNullAsSeperator_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.search(url, null, true, "test"));
	}

	@Test
	public void searchWithList_SearchWithNullAsKey_ExceptionThrown() {
		List<String> list = null;
		Assertions.assertThrows(IllegalArgumentException.class, () -> csvServices.search(url, seperator, true, list));
	}

	@Test
	public void searchWithList_SearchWithNullAsColumnNumber_ExceptionThrown() {
		List<String> list = new ArrayList<>();
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> csvServices.search(url, seperator, true, list, null));
	}

	@Test
	public void searchWithList_SearchOnNotExistingKey_SizeEquality() throws FileNotFoundException {
		String[] keys = { "This", "Is", "Line", "To", "Serach", "For", "Word", "In" };
		String[] keys2 = { "1", "2", "3", "4", "5" };
		String[] keys3 = { "Hello", "my", "name", "is", "World", "and", "I", "love", "Java", "Language" };
		csvServices.append(url, seperator, keys);
		csvServices.append(url, seperator, keys2);
		csvServices.append(url, seperator, keys3);
		List<String> list = new ArrayList<>();
		list.add("Muntaser");
		list.add("Lineus");
		assertEquals(0, csvServices.search(url, seperator, false, list).size());
	}

	@Test
	public void searchWithList_SearchOnExistingKey_SizeEquality() throws FileNotFoundException {
		String[] keys = { "This", "Is", "Line", "To", "Serach", "For", "Word", "In" };
		String[] keys2 = { "1", "2", "3", "4", "5" };
		String[] keys3 = { "Hello", "my", "name", "is", "World", "and", "I", "love", "Java", "Language" };
		csvServices.append(url, seperator, keys);
		csvServices.append(url, seperator, keys2);
		csvServices.append(url, seperator, keys3);
		List<String> list = new ArrayList<>();
		list.add("Muntaser");
		list.add("Is");
		assertEquals(1, csvServices.search(url, seperator, false, list).size());
	}

	@Test
	public void searchWithList_SearchOnExistingKey_CheckDublicateValues() throws FileNotFoundException {
		String[] keys = { "This", "Is", "Line", "To", "Serach", "For", "Word", "In" };
		csvServices.append(url, seperator, keys);
		List<String> list = new ArrayList<>();
		list.add("This");
		list.add("Is");
		assertEquals(1, csvServices.search(url, seperator, false, "Word").size());
	}

	

}
