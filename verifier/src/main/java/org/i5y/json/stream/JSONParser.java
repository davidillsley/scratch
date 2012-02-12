package org.i5y.json.stream;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface JSONParser {

	public enum Precision {
		INT, LONG, BIG_INTEGER, FLOAT, DOUBLE, BIG_DECIMAL
	}

	JSONEvent next();

	JSONEvent current();

	void advance();

	JSONEvent skip(int skipNumber);

	JSONEvent skipEnclosing();

	String string();

	String errorMessage();
	
	boolean asBoolean();
	
	Precision precision();

	int asInt();

	long asLong();

	float asFloat();

	double asDouble();

	BigInteger asBigInteger();

	BigDecimal asBigDecimal();
}
