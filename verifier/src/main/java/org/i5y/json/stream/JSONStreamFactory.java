package org.i5y.json.stream;

import java.io.Reader;
import java.io.Writer;

import org.i5y.json.stream.JSONTypeSafeWriters.ArrayWriter;
import org.i5y.json.stream.JSONTypeSafeWriters.ObjectWriter;

public interface JSONStreamFactory {

	public JSONWriter createWriter(Writer writer);

	public ObjectWriter createObjectWriter(Writer writer);
	public ArrayWriter createArrayWriter(Writer writer);

	public JSONParser createParser(Reader reader);
}
