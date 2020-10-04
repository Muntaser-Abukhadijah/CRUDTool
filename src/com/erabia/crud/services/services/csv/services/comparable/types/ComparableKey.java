package com.erabia.crud.services.services.csv.services.comparable.types;

import com.erabia.crud.services.services.csv.services.enums.Operations;

abstract public class ComparableKey {

	abstract public boolean compare(String key, Operations operation);

	abstract public Operations getOperator();

	public boolean getBoolean(String key) {
		if (key == null||(!key.equalsIgnoreCase("true")&&!key.equalsIgnoreCase("false")))
			throw new IllegalArgumentException("Not valid value for key");
		try {
			Boolean val = Boolean.parseBoolean(key);
			return val;
		} catch (Exception e) {
			throw new IllegalArgumentException("This value can't be parsed into boolean");
		}
	}

	public Number getNumber(String key) {
		if (key == null)
			throw new IllegalArgumentException("Not valid value for key");
		try {
			Double val = Double.parseDouble(key);
			return val;
		} catch (Exception e) {
			throw new IllegalArgumentException("This value can't be parsed into Number");
		}
	}


}
