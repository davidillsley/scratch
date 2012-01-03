package org.i5y.json.om.impl;

import java.util.List;
import java.util.Map;

import org.i5y.json.om.JSONNumber;
import org.i5y.json.om.JSONType;
import org.i5y.json.om.JSONValue;

public class ArrayValueImpl implements JSONValue {
	final List<JSONValue> value;

	public ArrayValueImpl(final List<JSONValue> value) {
		this.value = value;
	}

	@Override
	public JSONType getType() {
		return JSONType.ARRAY;
	}

	@Override
	public String asRawString() {
		throw new IllegalStateException();
	}

	@Override
	public String asString() {
		throw new IllegalStateException();
	}

	@Override
	public JSONNumber asNumber() {
		throw new IllegalStateException();
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
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
