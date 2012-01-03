package org.i5y.json.om;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public interface JSONModelBuilder {

	JSONValue value(String value);
	JSONValue value(int value);
	JSONValue value(long value);
	JSONValue value(float value);
	JSONValue value(double value);
	JSONValue value(boolean value);
	JSONValue value(BigInteger value);
	JSONValue value(BigDecimal value);
	JSONValue nullValue();
	
	List<JSONValue> array(String... values);
	List<JSONValue> array(int... values);
	List<JSONValue> array(long... values);
	List<JSONValue> array(float... values);
	List<JSONValue> array(double... values);
	List<JSONValue> array(BigInteger... values);
	List<JSONValue> array(BigDecimal... values);
	List<JSONValue> array(boolean... values);
	List<JSONValue> array(JSONValue... values);
	@SuppressWarnings("unchecked")
	List<JSONValue> array(Map<String, JSONValue>... values);
	@SuppressWarnings("unchecked")
	List<JSONValue> array(List<JSONValue>... values);
	
	Map<String, JSONValue> object(String name, JSONValue value);
	
	public JSONArrayBuilder arrayBuilder();
	public JSONObjectBuilder objectBuilder();
	
	public Map<String, JSONValue> parseObject(Reader input);
	public List<JSONValue> parseArray(Reader input);
	public JSONValue parse(Reader input);
	
	public void write(Writer writer, List<JSONValue> array) throws IOException;

	public void write(Writer writer, Map<String, JSONValue> object) throws IOException;
	
	public interface Query{
		Iterator<JSONValue> execute(List<JSONValue> array);
		Iterator<JSONValue> execute(Map<String, JSONValue> array);
		Iterator<JSONValue> execute(JSONValue value);
	}
	
	public Query query(String query);
	public Query query(String query, JSONType type);
}
