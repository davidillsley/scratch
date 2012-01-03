package org.i5y.json.examples;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import org.i5y.json.om.JSONModelBuilder;
import org.i5y.json.om.JSONModelBuilder.Query;
import org.i5y.json.om.JSONType;
import org.i5y.json.om.JSONValue;

public class ObjectModelExample {

	public static void main(String[] args) {
		// API is designed such that implementations can be plugable...
		ServiceLoader<JSONModelBuilder> modelBuilderLoader = ServiceLoader
				.load(JSONModelBuilder.class);

		JSONModelBuilder mb = modelBuilderLoader.iterator().next();
		// First off, build a simple object structure (note that objects and arrays are
		// generically types Maps and Lists respectively. This make them easy to use and
		// hopefully makes them magically pick up Java 8 collections improvements automatically
		// N.B It's still possible for implementation to use custom Map/List implementations
		// backed by the parser for performance optimisations etc.
		Map<String, JSONValue> userdetails = mb
				.objectBuilder()
				.addProperty("username", "davidillsley")
				.addProperty(
						"details",
						mb.objectBuilder()
								.addProperty("email", "david@illsley.org")
								.addProperty("freeDaysLeft", 30)
								.addProperty("hasPaid", false)
								.addProperty(
										"lastLoginDates",
										mb.array("2011-11-01", "2011-11-05",
												"2011-11-20", "2011-11-29",
												"2011-12-16")).build()).build();

		// We can get values from the 'object' using the standard map methods.
		// To distingush between JSON null values and lack of presence, we use a
		// JSONValue with JSONType.NULL for null values present in the JSON.
		JSONValue usernameValue = userdetails.get("username");
		if(usernameValue != null){
			// In most cases, the author will know the type of a given field
			// and so can call the asXXX without checking. N.B. If they're wrong,
			// an IllegalArgumentException is thrown.
			System.out.println("username = "+usernameValue.asString());
		}
		
		// Now lets use a query to find something at depth (rather than lots of
		// get()s and null checking)
		Query oldestLoginDate = mb.query("details.lastLoginDates.0",
				JSONType.STRING);
		Iterator<JSONValue> result = oldestLoginDate.execute(userdetails);
		if (result.hasNext()) {
			// Because we passed in the expected type to the query, we know the
			// resulting value has the correct type (there's the option to be
			// more dynamic as well).
			System.out.println("Oldest login date:" + result.next().asString());
		} else {
			System.out.println("No previous logins");
		}

		Query nickname = mb.query("nickname");
		Iterator<JSONValue> nicknameResult = nickname.execute(userdetails);
		if (nicknameResult.hasNext()) {
			JSONValue value = nicknameResult.next();
			// In this case, we didn't specify the type in the query, so we
			// could check the type before calling the asXXX() method
			if (value.getType() == JSONType.STRING) {
				System.out.println("nickname:"
						+ nicknameResult.next().asString());
			}
		} else {
			System.out.println("No nickname");
		}

		
		// We can now serialize our object model. * Should add an options object to control prettyprinting etc.
		try {
			System.out.println("*** Serialized object model:");
			mb.write(new OutputStreamWriter(System.out), userdetails);
			System.out.println("\n***");
		} catch (IOException e) {
			e.printStackTrace();
		}

		InputStream is = ObjectModelExample.class.getClassLoader()
				.getResourceAsStream(
						"org/i5y/json/examples/ObjectModelExample.json");
		
		// Read in our well known structure from file and run our existing query over it
		Map<String, JSONValue> userdetails2 = mb.parseObject(new InputStreamReader(is));
		Iterator<JSONValue> result2 = oldestLoginDate.execute(userdetails2);
		if (result2.hasNext()) {
			// Because we passed in the expected type to the query, we know the
			// resulting value has the correct type (there's the option to be
			// more dynamic as well).
			System.out.println("Oldest login date:" + result2.next().asString());
		} else {
			System.out.println("No previous logins");
		}
	}

}
