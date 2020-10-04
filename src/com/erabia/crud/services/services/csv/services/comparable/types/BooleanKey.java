package com.erabia.crud.services.services.csv.services.comparable.types;

import com.erabia.crud.services.services.csv.services.enums.BooleanOperations;
import com.erabia.crud.services.services.csv.services.enums.Operations;

public class BooleanKey extends ComparableKey {

	String key;
	BooleanOperations operation;

	public BooleanKey(String key, BooleanOperations operation) {
		super();
		if(key==null)
			throw new IllegalArgumentException("Null is not valid value for key");
		if(operation==null)
			throw new IllegalArgumentException("Null is not valid value for operation");
		this.key = key;
		this.operation = operation;
	}

	public Operations getOperator() {
		switch (operation) {
		case EQUAL:
			return Operations.EQUAL;
		case NOT_EQUAL:
			return Operations.NOT_EQUAL;
		}
		return Operations.EQUAL;
	}

	@Override
	public boolean compare(String key, Operations operation) {
		if(key==null) 
			throw new IllegalArgumentException("null is not a valid value for key");
		if(operation==null) 
			throw new IllegalArgumentException("null is not a valid value for operation");

		Boolean originKey = getBoolean(this.key);
		Boolean otherKey = getBoolean(key);

		
		switch (operation) {
		case EQUAL:
			return originKey.equals(otherKey);
		case NOT_EQUAL:
			return !originKey.equals(otherKey);
		default:
			throw new IllegalArgumentException("Type of operation is not supported");
		}
	}

}
