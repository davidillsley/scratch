package org.i5y.json.wrap;

import java.io.IOException;
import java.io.Writer;

public class DirectWriterExample {
	public static void main(String[] args) throws IOException{
		String n = "8783457934759345";
		String e = "980347587364587634587";
		Writer writer = null;
		DirectWriter.objectWriter(writer)
					.object("public-key")
						.string("algorithm", "RS")
						.string("n", n)
						.string("e", e)
						.end()
					.string("authentication", "/")
					.string("provisioning", "/provision")
					.end()
				.close();
		
		DirectWriter.objectWriter(writer)
					.string("firstName", "John")
					.string("lastName", "Smith")
					.number("age", 25)
					.array("phoneNumber")
						.object()
							.string("type", "home")
							.string("number", "212 555-1234")
							.end()
						.object()
							.string("type", "fax")
							.string("number", "646 555-4567")
							.end()
						.end()
					.end()
				.close();
	}
}
