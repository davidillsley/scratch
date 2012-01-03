package org.i5y.json.om.impl;

import java.util.List;
import java.util.Map;

import org.i5y.json.om.JSONNumber;
import org.i5y.json.om.JSONType;
import org.i5y.json.om.JSONValue;

class StringValueImpl implements JSONValue {

	private final String rawValue;

	StringValueImpl(String rawValue) {
		this.rawValue = rawValue;
	}

	@Override
	public final JSONType getType() {
		return JSONType.STRING;
	}

	@Override
	public String asRawString() {
		return rawValue;
	}

	@Override
	public final String asString() {
		// TODO pre-process
		return rawValue;
	}

	@Override
	public final JSONNumber asNumber() {
		throw new IllegalStateException();
	}

	@Override
	public final boolean asBoolean() {
		throw new IllegalStateException();
	}

	@Override
	public final Map<String, JSONValue> asObject() {
		throw new IllegalStateException();
	}

	@Override
	public final List<JSONValue> asArray() {
		throw new IllegalStateException();
	}

	@Override
	public String toString() {
		return rawValue.toString();
	}
}
