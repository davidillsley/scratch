package org.i5y.json.examples;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.i5y.json.stream.JSONEvent;
import org.i5y.json.stream.JSONParser;
import org.i5y.json.stream.JSONStreamFactory;

public class StreamParsingExample {

	public static void main(String[] args) throws FileNotFoundException {
		// API is designed such that implementations can be plugable...
		ServiceLoader<JSONStreamFactory> parserLoader = ServiceLoader
				.load(JSONStreamFactory.class);
		JSONStreamFactory streamFactory = parserLoader.iterator().next();
		JSONParser parser = streamFactory
				.createParser(new InputStreamReader(
						StreamParsingExample.class
								.getClassLoader()
								.getResourceAsStream(
										"org/i5y/json/examples/StreamParsingExample.json")));

		// The example json file contains an array of objects we only need the
		// dates from each object, so building a full object model isn't
		// necessary.
		boolean propertyIsDate = false;
		List<String> dates = new ArrayList<>();
		// Keep pulling events from the parser until we've consumed the input
		// or encountered an error.
		while (parser.next() != JSONEvent.INPUT_END
				&& parser.current() != JSONEvent.ERROR) {
			switch (parser.current()) {
			case PROPERTY_NAME: {
				// When we find a "date" property, flag it so we grab the next
				// string literal.
				if ("date".equals(parser.string())) {
					propertyIsDate = true;
				}
				break;
			}
			case STRING_LITERAL: {
				if (propertyIsDate) {
					dates.add(parser.string());
					propertyIsDate = false;
					// Don't need to examine any remaining properties in this
					// object, so skip to the end of it.
					parser.skipEnclosing();
				}
				break;
			}
			}
		}
		if (parser.current() == JSONEvent.ERROR) {
			System.out.println(parser.errorMessage());
		} else {
			System.out.println(dates);
		}
	}

}
