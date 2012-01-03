package org.i5y.json.om.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.i5y.json.om.JSONValue;
import org.i5y.json.om.JSONModelBuilder.Query;

import junit.framework.TestCase;

public class JSONModelBuilderImplTest extends TestCase{

	public void testObjectFromString() throws Exception{
		JSONModelBuilderImpl mb = new JSONModelBuilderImpl();
		Map<String, JSONValue> object = mb.parseObject("");
		System.out.println(object);
		
		object = mb.parseObject("{}");
		System.out.println(object);
		
		object = mb.parseObject("{\"a\":{\"c\":\"b\"}}");
		System.out.println(object);
		
		List<JSONValue> array = mb.parseArray("");
		System.out.println(array);
		array = mb.parseArray("[]");
		System.out.println(array);
		array = mb.parseArray("[\"a\",\"b\"]");
		System.out.println(array);
		
		Query aq = mb.query("1.1");
		Iterator<JSONValue> iterator = aq.execute(mb.array(mb.array("a","b"),mb.array("c","d")));
		System.out.println(iterator.next());
		
		aq = mb.query("1.2");
		iterator = aq.execute(mb.array(mb.array("a","b"),mb.array("c","d")));
		System.out.println(iterator.hasNext());
	}
}
