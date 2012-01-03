package org.i5y.json.om.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.i5y.json.om.JSONNumber;
import org.i5y.json.om.JSONType;
import org.i5y.json.om.JSONValue;

public class DoubleValueImpl implements JSONValue {

	private final double value;

	public DoubleValueImpl(final double value) {
		this.value = value;
	}

	@Override
	public JSONType getType() {
		return JSONType.NUMBER;
	}

	@Override
	public String asRawString() {
		return Double.toString(value);
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
				return Precision.DOUBLE;
			}

			@Override
			public int asInt() {
				return (int) value;
			}

			@Override
			public long asLong() {
				return (int) value;
			}

			@Override
			public float asFloat() {
				return (float)value;
			}

			@Override
			public double asDouble() {
				return value;
			}

			@Override
			public BigInteger asBigInteger() {
				return BigDecimal.valueOf(value).toBigInteger();
			}

			@Override
			public BigDecimal asBigDecimal() {
				return BigDecimal.valueOf(value);
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
