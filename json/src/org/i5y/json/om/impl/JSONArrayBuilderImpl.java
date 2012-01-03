package org.i5y.json.om.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.i5y.json.om.JSONArrayBuilder;
import org.i5y.json.om.JSONValue;

public class JSONArrayBuilderImpl implements JSONArrayBuilder {

	final List<JSONValue> array = new ArrayList<>();
	private boolean built = false;

	private final void throwIfBuilt() {
		if (built) {
			throw new IllegalStateException();
		}
	}

	@Override
	public List<JSONValue> build() {
		built = true;
		return array;
	}

	@Override
	public JSONArrayBuilder add(JSONValue value) {
		throwIfBuilt();
		array.add(value);
		return this;
	}

	@Override
	public JSONArrayBuilder add(String value) {
		throwIfBuilt();
		array.add(new StringValueImpl(value));
		return this;
	}

	@Override
	public JSONArrayBuilder add(int value) {
		throwIfBuilt();
		array.add(new IntValueImpl(value));
		return this;
	}

	@Override
	public JSONArrayBuilder add(long value) {
		throwIfBuilt();
		array.add(new LongValueImpl(value));
		return this;
	}

	@Override
	public JSONArrayBuilder add(float value) {
		throwIfBuilt();
		array.add(new FloatValueImpl(value));
		return this;
	}

	@Override
	public JSONArrayBuilder add(double value) {
		throwIfBuilt();
		array.add(new DoubleValueImpl(value));
		return this;
	}

	@Override
	public JSONArrayBuilder add(BigInteger value) {
		throwIfBuilt();
		array.add(new BigIntegerValueImpl(value));
		return this;
	}

	@Override
	public JSONArrayBuilder add(BigDecimal value) {
		throwIfBuilt();
		array.add(new BigDecimalValueImpl(value));
		return this;
	}

	@Override
	public JSONArrayBuilder add(Map<String, JSONValue> value) {
		throwIfBuilt();
		array.add(new ObjectValueImpl(value));
		return this;
	}

	@Override
	public JSONArrayBuilder add(List<JSONValue> value) {
		throwIfBuilt();
		array.add(new ArrayValueImpl(value));
		return this;
	}

	@Override
	public JSONArrayBuilder addNull(String name) {
		throwIfBuilt();
		array.add(new NullValueImpl());
		return this;
	}

}
