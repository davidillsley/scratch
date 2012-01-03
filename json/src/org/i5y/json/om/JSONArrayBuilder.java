package org.i5y.json.om;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Builder object for JSON Arrays (List<JSONValue>) with add() methods for
 * individual value types
 */
public interface JSONArrayBuilder {
	List<JSONValue> build();

	JSONArrayBuilder add(JSONValue value);
	JSONArrayBuilder add(String value);
	JSONArrayBuilder add(int value);
	JSONArrayBuilder add(long value);
	JSONArrayBuilder add(float value);
	JSONArrayBuilder add(double value);
	JSONArrayBuilder add(BigInteger value);
	JSONArrayBuilder add(BigDecimal value);
	JSONArrayBuilder add(Map<String, JSONValue> value);
	JSONArrayBuilder add(List<JSONValue> value);
	JSONArrayBuilder addNull(String name);
}