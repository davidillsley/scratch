package org.i5y.json.om.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;

import org.i5y.json.om.JSONArrayBuilder;
import org.i5y.json.om.JSONModelBuilder;
import org.i5y.json.om.JSONNumber;
import org.i5y.json.om.JSONObjectBuilder;
import org.i5y.json.om.JSONType;
import org.i5y.json.om.JSONValue;
import org.i5y.json.stream.JSONEvent;
import org.i5y.json.stream.JSONParser;
import org.i5y.json.stream.JSONStreamFactory;
import org.i5y.json.stream.JSONWriter;

public class JSONModelBuilderImpl implements JSONModelBuilder {

	@Override
	public List<JSONValue> array(BigDecimal... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (BigDecimal value : values) {
			array.add(new BigDecimalValueImpl(value));
		}
		return array;
	}

	@Override
	public List<JSONValue> array(BigInteger... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (BigInteger value : values) {
			array.add(new BigIntegerValueImpl(value));
		}
		return array;
	}

	@Override
	public List<JSONValue> array(boolean... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (boolean value : values) {
			array.add(value ? new TrueValueImpl() : new FalseValueImpl());
		}
		return array;
	}

	@Override
	public List<JSONValue> array(double... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (double value : values) {
			array.add(new DoubleValueImpl(value));
		}
		return array;
	}

	@Override
	public List<JSONValue> array(float... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (float value : values) {
			array.add(new FloatValueImpl(value));
		}
		return array;
	}

	@Override
	public List<JSONValue> array(int... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (int value : values) {
			array.add(new IntValueImpl(value));
		}
		return array;
	}

	@Override
	public List<JSONValue> array(JSONValue... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (JSONValue value : values) {
			array.add(value);
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JSONValue> array(List<JSONValue>... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (List<JSONValue> value : values) {
			array.add(new ArrayValueImpl(value));
		}
		return array;
	}

	@Override
	public List<JSONValue> array(long... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (Long value : values) {
			array.add(new LongValueImpl(value));
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JSONValue> array(Map<String, JSONValue>... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (Map<String, JSONValue> value : values) {
			array.add(new ObjectValueImpl(value));
		}
		return array;
	}

	@Override
	public List<JSONValue> array(String... values) {
		List<JSONValue> array = new ArrayList<>(values.length);
		for (String value : values) {
			array.add(new StringValueImpl(value));
		}
		return array;
	}

	@Override
	public JSONArrayBuilder arrayBuilder() {
		return new JSONArrayBuilderImpl();
	}

	@Override
	public JSONValue nullValue() {
		return new NullValueImpl();
	}

	@Override
	public Map<String, JSONValue> object(String name, JSONValue value) {
		Map<String, JSONValue> object = new HashMap<>();
		object.put(name, value);
		return object;
	}

	@Override
	public JSONObjectBuilder objectBuilder() {
		return new JSONObjectBuilderImpl();
	}

	public JSONValue parse(String input) {
		return parse(new StringReader(input));
	}
	
	public List<JSONValue> parseArray(String input) {
		return parseArray(new StringReader(input));
	}
	
	public Map<String, JSONValue> parseObject(String input) {
		return parseObject(new StringReader(input));
	}
	
	@Override
	public JSONValue parse(Reader input) {
		ServiceLoader<JSONStreamFactory> loader = ServiceLoader
				.load(JSONStreamFactory.class);
		JSONParser parser = loader.iterator().next().createParser(input);
		JSONValue resultObject = null;
		switch (parser.next()) {
		case ARRAY_START: {
			List<JSONValue> array = new ArrayList<JSONValue>();
			resultObject = new ArrayValueImpl(array);
			populateArray(array, parser);
			if (parser.current() == JSONEvent.ERROR) {
				throw new IllegalStateException(parser.errorMessage());
			}
			if (parser.next() != JSONEvent.INPUT_END) {
				throw new IllegalStateException(parser.current().toString());
			}
			break;
		}
		case OBJECT_START: {
			Map<String, JSONValue> object = new HashMap<String, JSONValue>();
			resultObject = new ObjectValueImpl(object);
			populateObject(object, parser);
			if (parser.current() == JSONEvent.ERROR) {
				throw new IllegalStateException(parser.errorMessage());
			}
			if (parser.next() != JSONEvent.INPUT_END) {
				throw new IllegalStateException(parser.current().toString());
			}
			break;
		}
		case INPUT_END:
			break;
		default:
			throw new IllegalStateException();
		}
		return resultObject;
	}

	@Override
	public List<JSONValue> parseArray(Reader input) {
		ServiceLoader<JSONStreamFactory> loader = ServiceLoader
				.load(JSONStreamFactory.class);
		JSONParser parser = loader.iterator().next().createParser(input);
		List<JSONValue> resultObject = null;
		switch (parser.next()) {
		case ARRAY_START: {
			resultObject = new ArrayList<JSONValue>();
			populateArray(resultObject, parser);
			if (parser.current() == JSONEvent.ERROR) {
				throw new IllegalStateException(parser.errorMessage());
			}
			if (parser.next() != JSONEvent.INPUT_END) {
				throw new IllegalStateException(parser.current().toString());
			}
			break;
		}
		case INPUT_END:
			break;
		default:
			throw new IllegalStateException();
		}
		return resultObject;
	}

	@Override
	public Map<String, JSONValue> parseObject(Reader input) {
		ServiceLoader<JSONStreamFactory> loader = ServiceLoader
				.load(JSONStreamFactory.class);
		JSONParser parser = loader.iterator().next().createParser(input);
		Map<String, JSONValue> resultObject = null;
		switch (parser.next()) {
		case OBJECT_START: {
			resultObject = new HashMap<String, JSONValue>();
			populateObject(resultObject, parser);
			if (parser.current() == JSONEvent.ERROR) {
				throw new IllegalStateException(parser.errorMessage());
			}
			if (parser.next() != JSONEvent.INPUT_END) {
				throw new IllegalStateException(parser.current().toString());
			}
			break;
		}
		case INPUT_END:
			break;
		default:
			throw new IllegalStateException();
		}
		return resultObject;
	}

	private final void populateArray(List<JSONValue> array, JSONParser parser) {
		boolean errored = false;
		while (!errored && parser.next() != JSONEvent.ARRAY_END) {
			switch (parser.current()) {
			case STRING_LITERAL: {
				array.add(new StringValueImpl(parser.string()));
				break;
			}
			case BOOLEAN_LITERAL: {
				array.add(parser.asBoolean() ? new TrueValueImpl()
						: new FalseValueImpl());
				break;
			}
			case NUMBER_LITERAL: {
				switch (parser.precision()) {
				case INT: {
					array.add(new IntValueImpl(parser.asInt()));
					break;
				}
				case LONG: {
					array.add(new LongValueImpl(parser.asLong()));
					break;
				}
				case BIG_INTEGER: {
					array.add(new BigIntegerValueImpl(parser.asBigInteger()));
					break;
				}
				case FLOAT: {
					array.add(new FloatValueImpl(parser.asFloat()));
					break;
				}
				case DOUBLE: {
					array.add(new DoubleValueImpl(parser.asDouble()));
					break;
				}
				case BIG_DECIMAL: {
					array.add(new BigDecimalValueImpl(parser.asBigDecimal()));
					break;
				}
				}
				break;
			}
			case NULL_LITERAL: {
				array.add(new NullValueImpl());
				break;
			}
			case OBJECT_START: {
				Map<String, JSONValue> newObj = new HashMap<>();
				array.add(new ObjectValueImpl(newObj));
				populateObject(newObj, parser);
				break;
			}
			case ARRAY_START: {
				List<JSONValue> newArray = new ArrayList<>();
				array.add(new ArrayValueImpl(newArray));
				populateArray(newArray, parser);
				break;
			}
			case ERROR:
				errored = true;
				break;
			}
		}
	}

	private final void populateObject(Map<String, JSONValue> object,
			JSONParser parser) {
		String propertyName = null;
		boolean errored = false;
		while (!errored && parser.next() != JSONEvent.OBJECT_END) {
			switch (parser.current()) {
			case PROPERTY_NAME: {
				propertyName = parser.string();
				break;
			}
			case STRING_LITERAL: {
				object.put(propertyName, new StringValueImpl(parser.string()));
				break;
			}
			case BOOLEAN_LITERAL: {
				object.put(propertyName,
						parser.asBoolean() ? new TrueValueImpl()
								: new FalseValueImpl());
				break;
			}
			case NUMBER_LITERAL: {
				switch (parser.precision()) {
				case INT: {
					object.put(propertyName, new IntValueImpl(parser.asInt()));
					break;
				}
				case LONG: {
					object.put(propertyName, new LongValueImpl(parser.asLong()));
					break;
				}
				case BIG_INTEGER: {
					object.put(propertyName,
							new BigIntegerValueImpl(parser.asBigInteger()));
					break;
				}
				case FLOAT: {
					object.put(propertyName,
							new FloatValueImpl(parser.asFloat()));
					break;
				}
				case DOUBLE: {
					object.put(propertyName,
							new DoubleValueImpl(parser.asDouble()));
					break;
				}
				case BIG_DECIMAL: {
					object.put(propertyName,
							new BigDecimalValueImpl(parser.asBigDecimal()));
					break;
				}
				}
				break;
			}
			case NULL_LITERAL: {
				object.put(propertyName, new NullValueImpl());
				break;
			}
			case OBJECT_START: {
				Map<String, JSONValue> newObj = new HashMap<>();
				object.put(propertyName, new ObjectValueImpl(newObj));
				populateObject(newObj, parser);
				break;
			}
			case ARRAY_START: {
				List<JSONValue> newArray = new ArrayList<>();
				object.put(propertyName, new ArrayValueImpl(newArray));
				populateArray(newArray, parser);
				break;
			}
			case ERROR:
				errored = true;
				break;
			}
		}
	}

	@Override
	public JSONValue value(BigDecimal value) {
		return new BigDecimalValueImpl(value);
	}

	@Override
	public JSONValue value(BigInteger value) {
		return new BigIntegerValueImpl(value);
	}

	@Override
	public JSONValue value(boolean value) {
		return value ? new TrueValueImpl() : new FalseValueImpl();
	}

	@Override
	public JSONValue value(double value) {
		return new DoubleValueImpl(value);
	}

	@Override
	public JSONValue value(float value) {
		return new FloatValueImpl(value);
	}

	@Override
	public JSONValue value(int value) {
		return new IntValueImpl(value);
	}

	@Override
	public JSONValue value(long value) {
		return new LongValueImpl(value);
	}

	@Override
	public JSONValue value(String value) {
		return new StringValueImpl(value);
	}

	@Override
	public Query query(String query) {
		return new QueryImpl(query, null);
	}

	@Override
	public Query query(String query, JSONType type) {
		return new QueryImpl(query, type);
	}

	@Override
	public void write(Writer writer, List<JSONValue> array) throws IOException{
		ServiceLoader<JSONStreamFactory> loader = ServiceLoader.load(JSONStreamFactory.class);
		JSONWriter jw = loader.iterator().next().createWriter(writer);
		write(jw, new ArrayValueImpl(array));
		jw.flush();
	}

	private void write(JSONWriter jw, JSONValue value) throws IOException{
		switch(value.getType()){
		case BOOLEAN: {jw.literal(value.asBoolean());break;}
		case NULL: {jw.nullLiteral();break;}
		case STRING: {jw.literal(value.asString()); break;}
		case NUMBER: {
			JSONNumber number = value.asNumber();
			switch(number.precision()){
			case BIG_DECIMAL:{jw.literal(number.asBigDecimal());break;}
			case BIG_INTEGER:{jw.literal(number.asBigInteger());break;}
			case DOUBLE:{jw.literal(number.asDouble());break;}
			case FLOAT:{jw.literal(number.asFloat());break;}
			case INT:{jw.literal(number.asInt());break;}
			case LONG:{jw.literal(number.asLong());break;}
			}
			break;
		}
		case ARRAY:{
			jw.startArray();
			for(JSONValue innerValue: value.asArray()){
				write(jw, innerValue);
			}
			jw.endArray();
			break;
			}
		case OBJECT:{
			jw.startObject();
			for(Entry<String, JSONValue> entry: value.asObject().entrySet()){
				jw.propertyName(entry.getKey());
				write(jw, entry.getValue());
			}
			jw.endObject();
			break;
		}
		}
	}
	
	@Override
	public void write(Writer writer, Map<String, JSONValue> object) throws IOException{
		ServiceLoader<JSONStreamFactory> loader = ServiceLoader.load(JSONStreamFactory.class);
		JSONWriter jw = loader.iterator().next().createWriter(writer);
		write(jw, new ObjectValueImpl(object));
		jw.flush();
	}
}
