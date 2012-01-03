package org.i5y.json.om;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Builder object for JSON Objects (Map<String, JSONValue>) with addProperty() methods for
 * individual value types
 */
public interface JSONObjectBuilder{
	Map<String, JSONValue> build();
	JSONObjectBuilder addProperty(String name, JSONValue value);
	JSONObjectBuilder addProperty(String name, String value);
	JSONObjectBuilder addProperty(String name, int value);
	JSONObjectBuilder addProperty(String name, boolean value);
	JSONObjectBuilder addProperty(String name, long value);
	JSONObjectBuilder addProperty(String name, float value);
	JSONObjectBuilder addProperty(String name, double value);
	JSONObjectBuilder addProperty(String name, BigInteger value);
	JSONObjectBuilder addProperty(String name, BigDecimal value);
	JSONObjectBuilder addProperty(String name, Map<String, JSONValue> value);
	JSONObjectBuilder addProperty(String name, List<JSONValue> value);
	JSONObjectBuilder addNullProperty(String name);
}