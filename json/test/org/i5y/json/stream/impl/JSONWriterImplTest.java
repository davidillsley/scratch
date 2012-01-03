package org.i5y.json.stream.impl;

import java.io.OutputStreamWriter;

import junit.framework.TestCase;

public class JSONWriterImplTest extends TestCase {

	public void testSimpleOutput() throws Exception {
		JSONWriterImpl jgi = new JSONWriterImpl(new OutputStreamWriter(
				System.out));
		jgi.startObject().endObject().flush();
		jgi = new JSONWriterImpl(new OutputStreamWriter(System.out));
		jgi.startObject().property("a", "b").endObject().flush();
		jgi.startObject().property("a", "b").propertyName("x").literal("\u0031\r\naa")
				.endObject().flush();
	}
}
