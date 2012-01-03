package org.i5y.json.om.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.i5y.json.om.JSONObjectBuilder;
import org.i5y.json.om.JSONValue;

public class JSONObjectBuilderImpl implements JSONObjectBuilder {

	private final Map<String, JSONValue> object = new HashMap<String, JSONValue>();

	private boolean built = false;

	@Override
	public Map<String, JSONValue> build() {
		built = true;
		return object;
	}

	private final void throwIfBuilt() {
		if (built) {
			throw new IllegalStateException();
		}
	}

	@Override
	public JSONObjectBuilder addProperty(String name, JSONValue value) {
		throwIfBuilt();
		object.put(name, value);
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name, String value) {
		throwIfBuilt();
		object.put(name, new StringValueImpl(value));
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name, int value) {
		throwIfBuilt();
		object.put(name, new IntValueImpl(value));
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name, long value) {
		throwIfBuilt();
		object.put(name, new LongValueImpl(value));
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name, float value) {
		throwIfBuilt();
		object.put(name, new FloatValueImpl(value));
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name, double value) {
		throwIfBuilt();
		object.put(name, new DoubleValueImpl(value));
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name, BigInteger value) {
		throwIfBuilt();
		object.put(name, new BigIntegerValueImpl(value));
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name, BigDecimal value) {
		throwIfBuilt();
		object.put(name, new BigDecimalValueImpl(value));
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name,
			Map<String, JSONValue> value) {
		throwIfBuilt();
		object.put(name, new ObjectValueImpl(value));
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name, List<JSONValue> value) {
		throwIfBuilt();
		object.put(name, new ArrayValueImpl(value));
		return this;
	}

	@Override
	public JSONObjectBuilder addNullProperty(String name) {
		throwIfBuilt();
		object.put(name, new NullValueImpl());
		return this;
	}

	@Override
	public JSONObjectBuilder addProperty(String name, boolean value) {
		throwIfBuilt();
		object.put(name, value ? new TrueValueImpl() : new FalseValueImpl());
		return this;
	}

}
