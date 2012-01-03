package org.i5y.json.stream.impl;

import org.i5y.json.stream.JSONEvent;
import org.i5y.json.stream.JSONParser.Precision;

import junit.framework.TestCase;

public class JSONParserImplTest extends TestCase {

	public void testStart() throws Exception {
		JSONParserImpl jpi = new JSONParserImpl("");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.INPUT_END, jpi.next());

		jpi = new JSONParserImpl("   \r\n  \r \n  ");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.INPUT_END, jpi.next());

		jpi = new JSONParserImpl("   z");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ERROR, jpi.next());
		
		jpi = new JSONParserImpl("{");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());

		jpi = new JSONParserImpl(" {");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());

		jpi = new JSONParserImpl("  {");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());

		jpi = new JSONParserImpl("\r{");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());

		jpi = new JSONParserImpl("\n{");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());

		jpi = new JSONParserImpl("  \r\n  {");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());

		jpi = new JSONParserImpl("  \r  \r\n{");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());

		jpi = new JSONParserImpl("[");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());

		jpi = new JSONParserImpl(" [");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());

		jpi = new JSONParserImpl("  [");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());

		jpi = new JSONParserImpl("\r[");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());

		jpi = new JSONParserImpl("\n[");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());

		jpi = new JSONParserImpl("  \r\n  [");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());

		jpi = new JSONParserImpl("  \r  \r\n[");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
	}

	public void testSimpleObject() throws Exception {
		JSONParserImpl jpi = new JSONParserImpl("{\"property1\":\"value1\"}");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("value1", jpi.string());
		assertEquals(JSONEvent.OBJECT_END, jpi.next());
		
		jpi = new JSONParserImpl(
				"{\"property1\" \r \n \r\n  : \r \n \r\n \"value1\"}");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("value1", jpi.string());
		assertEquals(JSONEvent.OBJECT_END, jpi.next());

		jpi = new JSONParserImpl("{\"property1\":");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.ERROR, jpi.next());
		
		jpi = new JSONParserImpl("{\"property1\":\"value1\" , \"property2\" : \"value2\"}");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("value1", jpi.string());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property2", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("value2", jpi.string());
		assertEquals(JSONEvent.OBJECT_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("{\"property1\":\"value1\" , \"property2\" : {");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("value1", jpi.string());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property2", jpi.string());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());

		jpi = new JSONParserImpl("{\"property1\",\"wont get here\"");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.ERROR, jpi.next());

		jpi = new JSONParserImpl("{\"property1\":\"startofval");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.ERROR, jpi.next());

		jpi = new JSONParserImpl("{\"property1\":,");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.ERROR, jpi.next());
		
		jpi = new JSONParserImpl("{\"property1\":{");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());

		jpi = new JSONParserImpl("{\"property1\":[");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property1", jpi.string());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());

		jpi = new JSONParserImpl("{\"property\\\"1\":\"value1\"}");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("property\\\"1", jpi.string());

		jpi = new JSONParserImpl("{");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.ERROR, jpi.next());

		jpi = new JSONParserImpl("{   \r  \r\n  \n ");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.ERROR, jpi.next());

		jpi = new JSONParserImpl("{   \r  \r\n  \n \"startofpropnamenoclose");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.ERROR, jpi.next());
	}
	
	public void testSimpleArray() throws Exception {
		JSONParserImpl jpi = new JSONParserImpl("[\"a\" , \"b\"]");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("a", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("b", jpi.string());
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("[\"a\" , \"b\"] ");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("a", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("b", jpi.string());
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());

		jpi = new JSONParserImpl("[\"a\",\"b\"] ");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("a", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("b", jpi.string());
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("[\"a\" , \"b\"]  [\"a\" , \"b\"]");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("a", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("b", jpi.string());
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("a", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("b", jpi.string());
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("[\"a\" , {\"a\":\"b\"}]");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("a", jpi.string());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals(JSONEvent.OBJECT_END, jpi.next());
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
	}
	
	public void testagain() throws Exception{
		JSONParserImpl jpi = new JSONParserImpl("{\"a\":{\"c\":\"b\"}}");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("a", jpi.string());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals("c", jpi.string());
		assertEquals(JSONEvent.STRING_LITERAL, jpi.next());
		assertEquals("b", jpi.string());
		assertEquals(JSONEvent.OBJECT_END, jpi.next());
		assertEquals(JSONEvent.OBJECT_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("{}");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.OBJECT_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("[]");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("[true, false]");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.BOOLEAN_LITERAL, jpi.next());
		assertEquals(true, jpi.asBoolean());
		assertEquals(JSONEvent.BOOLEAN_LITERAL, jpi.next());
		assertEquals(false, jpi.asBoolean());
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("{\"a\":true, \"b\":false}");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.OBJECT_START, jpi.next());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals(JSONEvent.BOOLEAN_LITERAL, jpi.next());
		assertEquals(true, jpi.asBoolean());
		assertEquals(JSONEvent.PROPERTY_NAME, jpi.next());
		assertEquals(JSONEvent.BOOLEAN_LITERAL, jpi.next());
		assertEquals(false, jpi.asBoolean());
		assertEquals(JSONEvent.OBJECT_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("[1 , 1.0 , -1 , -1.0 , -1.0e10 , 1e-10 , 1e10 , 2147483647 , 9223372036854775807 , 1.4E-45 , 3.4028235E38 ]");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.INT, jpi.precision()); // 1
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // 1.0
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.INT, jpi.precision()); // -1
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // -1.0
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // -1.0e10
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // 1e-10
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.LONG, jpi.precision()); // 1e10
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.INT, jpi.precision()); // 2147483647
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.LONG, jpi.precision()); // 9223372036854775807
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // 1.4E-45
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // 3.4028235E38
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
		
		jpi = new JSONParserImpl("[1,1.0,-1,-1.0,-1.0e10,1e-10,1e10,2147483647,9223372036854775807,1.4E-45,3.4028235E38]");
		assertEquals(JSONEvent.INPUT_START, jpi.current());
		assertEquals(JSONEvent.ARRAY_START, jpi.next());
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.INT, jpi.precision()); // 1
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // 1.0
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.INT, jpi.precision()); // -1
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // -1.0
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // -1.0e10
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // 1e-10
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.LONG, jpi.precision()); // 1e10
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.INT, jpi.precision()); // 2147483647
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.LONG, jpi.precision()); // 9223372036854775807
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // 1.4E-45
		assertEquals(JSONEvent.NUMBER_LITERAL, jpi.next());
		assertEquals(Precision.BIG_DECIMAL, jpi.precision()); // 3.4028235E38
		assertEquals(JSONEvent.ARRAY_END, jpi.next());
		assertEquals(JSONEvent.INPUT_END, jpi.next());
	}
}
