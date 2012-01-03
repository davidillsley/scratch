package org.i5y.json.stream.impl;

import java.io.Reader;
import java.io.Writer;

import org.i5y.json.stream.JSONParser;
import org.i5y.json.stream.JSONStreamFactory;
import org.i5y.json.stream.JSONTypeSafeWriters.ArrayWriter;
import org.i5y.json.stream.JSONTypeSafeWriters.ObjectWriter;
import org.i5y.json.stream.JSONWriter;
import org.i5y.json.stream.impl.JSONTypeSafeWriterImpl.ArrayWriterImpl;
import org.i5y.json.stream.impl.JSONTypeSafeWriterImpl.ObjectWriterImpl;

public class JSONStreamFactoryImpl implements JSONStreamFactory {

	@Override
	public JSONWriter createWriter(Writer writer) {
		return new JSONWriterImpl(writer);
	}

	@Override
	public JSONParser createParser(Reader reader) {
		return new JSONParserImpl(reader);
	}

	@Override
	public ObjectWriter createObjectWriter(Writer writer) {
		return new ObjectWriterImpl(writer);
	}

	@Override
	public ArrayWriter createArrayWriter(Writer writer) {
		return new ArrayWriterImpl(writer);
	}

}
