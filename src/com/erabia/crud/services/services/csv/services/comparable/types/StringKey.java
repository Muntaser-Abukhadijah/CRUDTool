package com.erabia.crud.services.services.csv.services.comparable.types;

import com.erabia.crud.services.services.csv.services.enums.Operations;

public class StringKey extends ComparableKey {
	
	private String key;
	Operations operation;

	public StringKey(String key, Operations operation) {
		super();
		if(key==null)
			throw new IllegalArgumentException("Null is not valid value for key");
		if(operation==null)
			throw new IllegalArgumentException("Null is not valid value for operation");
		this.key = key;
		this.operation =operation;
	}
	
	public Operations getOperator() {
		return operation;
	}
	
	@Override
	public boolean compare(String key, Operations operation) {
		if(key==null)
			throw new IllegalArgumentException("Null is not valid value for key");
		if(operation==null)
			throw new IllegalArgumentException("Null is not valid value for operation");
		
		switch (operation) {
		case EQUAL:
			return this.key.equals(key);
		case NOT_EQUAL:
			return !(this.key.equals(key));
		case LESS_THAN:
			return this.key.compareTo(key)<0?true:false;
		case LESS_THAN_OR_EQUAL:
			return this.key.compareTo(key)<=0?true:false;
		case GREATER_THAN:
			return this.key.compareTo(key)>0?true:false;
		case GREATER_THAN_OR_EQUAL:
			return this.key.compareTo(key)>=0?true:false;
		default:
			throw new IllegalArgumentException("Type of operation is not supported");
		}
	}
	
}
