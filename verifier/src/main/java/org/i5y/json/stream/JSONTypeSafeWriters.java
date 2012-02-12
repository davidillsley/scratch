package org.i5y.json.stream;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * These interfaces provide a <em>type safe</em> and fluent interface for outputting
 * JSON using a streaming approach.
 * <pre>
 * protected void doGet(HttpServletRequest request,
 *                      HttpServletResponse response) throws ServletException, IOException {
 *       streamFactory.createObjectWriter(response.getWriter())
 *           .startObject()
 *           .property("stock","ACME")
 *           .property("price", 100)
 *           .property("lastUpdated",new Date().toString())
 *           .endObject()
 *           .close();
 * }
 * </pre>
 * @author davidillsley
 * 
 */
public interface JSONTypeSafeWriters {

	public interface ObjectWriter {
		InsideObject<ObjectWriter> startObject();

		void flush() throws IOException;

		void close() throws IOException;
	}

	public interface ArrayWriter {
		void flush() throws IOException;

		void close() throws IOException;

		InsideArray<ArrayWriter> startArray();
	}

	public interface InsideObject<T> {
		InsideObject<T> property(String name, String literal);

		InsideObject<T> property(String name, int literal);

		InsideObject<T> property(String name, long literal);

		InsideObject<T> property(String name, BigInteger literal);

		InsideObject<T> property(String name, float literal);

		InsideObject<T> property(String name, double literal);

		InsideObject<T> property(String name, BigDecimal literal);

		InsideObject<T> property(String name, boolean literal);

		InsideObject<T> propertyNull(String name);

		PostPropertyName<InsideObject<T>> defineProperty(String name);

		T endObject();
	}

	public interface PostPropertyName<T> {
		T literal(String literal);

		T literal(int literal);

		T literal(long literal);

		T literal(BigInteger literal);

		T literal(float literal);

		T literal(double literal);

		T literal(BigDecimal literal);

		T literal(boolean literal);

		T literalNull();

		InsideObject<T> startObject();

		InsideArray<T> startArray();
	}

	public interface InsideArray<T> {
		InsideArray<T> literal(String literal);

		InsideArray<T> literal(int literal);

		InsideArray<T> literal(long literal);

		InsideArray<T> literal(BigInteger literal);

		InsideArray<T> literal(float literal);

		InsideArray<T> literal(double literal);

		InsideArray<T> literal(BigDecimal literal);

		InsideArray<T> literal(boolean literal);

		InsideArray<T> literalNull();

		InsideObject<InsideArray<T>> startObject();

		InsideArray<InsideArray<T>> startArray();

		T endArray();
	}
}
