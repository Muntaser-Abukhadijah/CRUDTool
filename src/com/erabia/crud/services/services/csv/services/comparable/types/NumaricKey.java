package com.erabia.crud.services.services.csv.services.comparable.types;

import com.erabia.crud.services.services.csv.services.enums.Operations;

public class NumaricKey extends ComparableKey {

	String key;
	Operations operation;
	
	public NumaricKey(String key,Operations operation) {
		super();	
		if(key==null)
			throw new IllegalArgumentException("Null is not valid value for key");
		if(operation==null)
			throw new IllegalArgumentException("Null is not valid value for operation");
		this.key = key;
		this.operation=operation;
	}

	public Operations getOperator() {
		return operation;
	}
	
	@Override
	public boolean compare(String key, Operations operation) {
		if(key==null)
			throw new IllegalArgumentException("Null is not a valid value for key");	
		if(operation==null)
			throw new IllegalArgumentException("Null is not valid value for operation");
		
		Number originKey = getNumber(this.key);
		Number otherKey = getNumber(key);

		switch (operation) {
		case EQUAL:
			return (double)originKey == (double)otherKey;
		case NOT_EQUAL:
			return (double)originKey!= (double)otherKey; 
		case LESS_THAN:
			return (double)originKey > (double)otherKey;
		case LESS_THAN_OR_EQUAL:
			return (double)originKey >= (double)otherKey;
		case GREATER_THAN:
			return (double)originKey < (double)otherKey;
		case GREATER_THAN_OR_EQUAL:
			return (double)originKey <= (double)otherKey;
		default:
			throw new IllegalArgumentException("Type of operation is not supported");
		}
	}	
}
