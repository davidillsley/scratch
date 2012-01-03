package org.i5y.json.stream.impl;

import java.io.OutputStreamWriter;

import junit.framework.TestCase;

import org.i5y.json.stream.JSONStreamFactory;

public class JSONTypeSafeWriterTest extends TestCase{
	public void testTypeSafeAPI() throws Exception {
		JSONStreamFactory jsf = new JSONStreamFactoryImpl();
		jsf.createArrayWriter(new OutputStreamWriter(System.out)).startArray()
				.startObject()
				.endObject()
				.startObject()
				.defineProperty("a")
				.literal(true)
				.endObject()
				.startObject()
				.property("a", "b")
				.endObject()
				.startObject()
				.defineProperty("a")
				.startObject()
				.endObject()
				.endObject()
				.endArray()
				.flush();
	}
}
