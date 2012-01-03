package org.i5y.json.om.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.i5y.json.om.JSONNumber;

class JSONNumberImpl implements JSONNumber{

	private int parsedInt;
	
	public JSONNumberImpl(String rawString){
		parsedInt = Integer.parseInt(rawString);
	}
	
	@Override
	public int asInt() {
		return parsedInt;
	}

	@Override
	public long asLong() {
		return parsedInt;
	}

	@Override
	public float asFloat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double asDouble() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigInteger asBigInteger() {
		return BigInteger.valueOf(parsedInt);
	}

	@Override
	public BigDecimal asBigDecimal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Precision precision() {
		// TODO Auto-generated method stub
		return null;
	}
}
