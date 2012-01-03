package org.i5y.json.examples;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ServiceLoader;

import org.i5y.json.stream.JSONWriter;
import org.i5y.json.stream.JSONStreamFactory;

public class StreamWriterExample {

	public static void main(String[] args) throws IOException {
		// API is designed such that implementations can be plugable...
		ServiceLoader<JSONStreamFactory> parserLoader = ServiceLoader
				.load(JSONStreamFactory.class);
		JSONStreamFactory streamFactory = parserLoader.iterator().next();
		Writer writer = new StringWriter();
		JSONWriter generator = streamFactory.createWriter(writer);
		generator.startObject()
			     .property("username", "davidillsley")
				 .propertyName("RealName")
				 .literal("David")
				 // The second literal will cause an IllegalStateException because
				 // in an object a literal can only follow a property name declaration
				 .literal("Illsley")
				 .close();

		System.out.println(writer);
	}

}
