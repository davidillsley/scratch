package org.i5y.json.om.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.i5y.json.om.JSONType;
import org.i5y.json.om.JSONValue;
import org.i5y.json.om.JSONModelBuilder.Query;

class QueryImpl implements Query {

	final String[] elements;
	final JSONType type;

	public QueryImpl(final String query, final JSONType type) {
		elements = query.split("\\.");
		this.type = type;
	}

	@Override
	public Iterator<JSONValue> execute(List<JSONValue> array) {
		return executeImpl(new ArrayValueImpl(array));
	}

	@Override
	public Iterator<JSONValue> execute(Map<String, JSONValue> object) {
		return executeImpl(new ObjectValueImpl(object));
	}

	private Iterator<JSONValue> executeImpl(JSONValue initialValue) {
		JSONValue currentElement = initialValue;
		for (int i = 0; i < elements.length; i++) {
			JSONValue jv = (JSONValue) currentElement;
			if (jv.getType() == JSONType.ARRAY) {
				int index = Integer.parseInt(elements[i]);
				if (index >= 0 && index < jv.asArray().size()) {
					currentElement = jv.asArray().get(index);
				} else {
					currentElement = null;
				}
			} else if (jv.getType() == JSONType.OBJECT) {
				currentElement = jv.asObject().get(elements[i]);
			} else {
				currentElement = null;
			}
		}

		if (currentElement != null && type != null
				&& type != currentElement.getType()) {
			currentElement = null;
		}
		
		final JSONValue result = currentElement;
		return new Iterator<JSONValue>() {

			boolean read = false;

			@Override
			public boolean hasNext() {
				return !read && result != null;
			}

			@Override
			public JSONValue next() {
				read = true;
				if (result != null) {
					return result;
				}
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public Iterator<JSONValue> execute(JSONValue value) {
		return executeImpl(value);
	}
}