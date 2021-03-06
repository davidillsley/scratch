package org.i5y.json.om.impl;

import java.util.List;
import java.util.Map;

import org.i5y.json.om.JSONNumber;
import org.i5y.json.om.JSONType;
import org.i5y.json.om.JSONValue;

class NullValueImpl implements JSONValue {

	@Override
	public final JSONType getType() {
		return JSONType.NULL;
	}

	@Override
	public String asRawString() {
		return "null";
	}

	@Override
	public final String asString() {
		throw new IllegalStateException();
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

}
