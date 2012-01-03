package org.i5y.json.om;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface JSONNumber {
	public enum Precision {
		INT, LONG, BIG_INTEGER, FLOAT, DOUBLE, BIG_DECIMAL
	}

	Precision precision();

	int asInt();

	long asLong();

	float asFloat();

	double asDouble();

	BigInteger asBigInteger();

	BigDecimal asBigDecimal();
}
