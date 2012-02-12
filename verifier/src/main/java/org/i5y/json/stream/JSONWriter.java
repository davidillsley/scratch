package org.i5y.json.stream;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface JSONWriter {

	JSONWriter startObject();
	JSONWriter endObject();
	JSONWriter propertyName(String name);
	JSONWriter property(String name, String literal);
	JSONWriter property(String name, int literal);
	JSONWriter property(String name, long literal);
	JSONWriter property(String name, BigInteger literal);
	JSONWriter property(String name, float literal);
	JSONWriter property(String name, double literal);
	JSONWriter property(String name, BigDecimal literal);
	JSONWriter property(String name, boolean literal);
	JSONWriter nullProperty(String name);
	JSONWriter literal(String literal);
	JSONWriter literal(int literal);
	JSONWriter literal(long literal);
	JSONWriter literal(BigInteger literal);
	JSONWriter literal(float literal);
	JSONWriter literal(double literal);
	JSONWriter literal(BigDecimal literal);
	JSONWriter nullLiteral();
	JSONWriter literal(boolean literal);
	JSONWriter startArray();
	JSONWriter endArray();
	
	void flush() throws IOException;
	void close() throws IOException;
}
