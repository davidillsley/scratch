package org.i5y.json.examples;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ServiceLoader;

import org.i5y.json.stream.JSONStreamFactory;

/**
 * The Type Safe Stream Writers (ArrayWriter and ObjectWriter) improve on the
 * basic JSONWriter (for some use cases) by returning interfaces which limit
 * subsequent calls to valid ones based on the current state.
 */
public class TypeSafeStreamWriterExample {

	public static void main(String[] args) throws IOException {
		// API is designed such that implementations can be plugable...
		ServiceLoader<JSONStreamFactory> parserLoader = ServiceLoader
				.load(JSONStreamFactory.class);
		JSONStreamFactory streamFactory = parserLoader.iterator().next();
		Writer writer = new StringWriter();
		streamFactory.createObjectWriter(writer)
		              .startObject()
		              .property("username", "davidillsley")
		              .defineProperty("RealName")
		              .literal("David")
		              // Return type of this .literal() is InsideObject<ObjectWriter>
		              // which only defines legal methods. IllegalStateException not possible.
		              .endObject()
		              .close();
		System.out.println(writer.toString());
	}

}
