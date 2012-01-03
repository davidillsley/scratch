package org.i5y.json.om;

import java.util.List;
import java.util.Map;

/**
 * Interface representing JSON values. Calls to an asXXX method not corresponding to
 * the type returned by getType() results in an IllegalArgumentException
 */
public interface JSONValue {
	JSONType getType();

	String asRawString();

	String asString();

	JSONNumber asNumber();

	boolean asBoolean();

	Map<String, JSONValue> asObject();

	List<JSONValue> asArray();
}
