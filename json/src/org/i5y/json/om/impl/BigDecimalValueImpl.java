package org.i5y.json.om.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.i5y.json.om.JSONNumber;
import org.i5y.json.om.JSONType;
import org.i5y.json.om.JSONValue;

public class BigDecimalValueImpl implements JSONValue {

	private final BigDecimal value;

	public BigDecimalValueImpl(final BigDecimal value) {
		this.value = value;
	}

	@Override
	public JSONType getType() {
		return JSONType.NUMBER;
	}

	@Override
	public String asRawString() {
		return value.toString();
	}

	@Override
	public String asString() {
		throw new IllegalStateException();
	}

	@Override
	public JSONNumber asNumber() {
		return new JSONNumber() {

			@Override
			public Precision precision() {
				return Precision.BIG_DECIMAL;
			}

			@Override
			public int asInt() {
				return value.intValue();
			}

			@Override
			public long asLong() {
				return value.longValue();
			}

			@Override
			public float asFloat() {
				return value.floatValue();
			}

			@Override
			public double asDouble() {
				return value.doubleValue();
			}

			@Override
			public BigInteger asBigInteger() {
				return value.toBigInteger();
			}

			@Override
			public BigDecimal asBigDecimal() {
				return value;
			}
		};
	}

	@Override
	public boolean asBoolean() {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, JSONValue> asObject() {
		throw new IllegalStateException();
	}

	@Override
	public List<JSONValue> asArray() {
		throw new IllegalStateException();
	}
}
