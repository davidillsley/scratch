package org.i5y.json.wrap;

import java.io.Closeable;
import java.io.Writer;

import javax.json.JsonArrayVisitor;
import javax.json.JsonObjectVisitor;
import javax.json.stream.JsonWriter;

public class DirectWriter {

	public interface ObjectWriter<T> {

		ObjectWriter<T> string(String name, String value);

		ObjectWriter<T> number(String name, Number value);

		ObjectWriter<T> bool(String name, boolean value);

		ObjectWriter<ObjectWriter<T>> object(String name);

		ArrayWriter<ObjectWriter<T>> array(String name);

		T end();
	}

	public interface ArrayWriter<T> {

		ArrayWriter<T> string(String value);

		ArrayWriter<T> number(Number value);

		ArrayWriter<T> bool(boolean value);

		ObjectWriter<ArrayWriter<T>> object();

		ArrayWriter<ArrayWriter<T>> array();

		T end();
	}

	public static ObjectWriter<Closeable> objectWriter(Writer writer) {
		JsonWriter jsonWriter = JsonWriter.create(writer);
		return new ObjectWriterImpl<Closeable>(jsonWriter,
				jsonWriter.visitObject());
	}

	public static ArrayWriter<Closeable> arrayWriter(Writer writer) {
		JsonWriter jsonWriter = JsonWriter.create(writer);
		return new ArrayWriterImpl<Closeable>(jsonWriter,
				jsonWriter.visitArray());
	}

	private static class ObjectWriterImpl<U> implements ObjectWriter<U> {

		private final U wrapper;
		private final JsonObjectVisitor jsonObjectVisitor;

		ObjectWriterImpl(U wrapper, JsonObjectVisitor jsonObjectVisitor) {
			this.wrapper = wrapper;
			this.jsonObjectVisitor = jsonObjectVisitor;
		}

		@Override
		public ObjectWriter<U> string(String name, String value) {
			jsonObjectVisitor.visitString(name, value);
			return this;
		}

		@Override
		public ObjectWriter<U> number(String name, Number value) {
			jsonObjectVisitor.visitNumber(name, value);
			return this;
		}

		@Override
		public ObjectWriter<U> bool(String name, boolean value) {
			if (value) {
				jsonObjectVisitor.visitTrue(name);
			} else {
				jsonObjectVisitor.visitTrue(name);
			}
			return this;
		}

		@Override
		public ObjectWriter<ObjectWriter<U>> object(String name) {
			JsonObjectVisitor objectVisitor = jsonObjectVisitor
					.visitObject(name);
			return new ObjectWriterImpl<ObjectWriter<U>>(this, objectVisitor);
		}

		@Override
		public ArrayWriter<ObjectWriter<U>> array(String name) {
			JsonArrayVisitor arrayVisitor = jsonObjectVisitor.visitArray(name);
			return new ArrayWriterImpl<ObjectWriter<U>>(this, arrayVisitor);
		}

		@Override
		public U end() {
			jsonObjectVisitor.visitEnd();
			return wrapper;
		}
	}

	private static class ArrayWriterImpl<U> implements ArrayWriter<U> {
		private final U wrapper;
		private final JsonArrayVisitor jsonArrayVisitor;

		public ArrayWriterImpl(U wrapper, JsonArrayVisitor jsonArrayVisitor) {
			this.wrapper = wrapper;
			this.jsonArrayVisitor = jsonArrayVisitor;
		}

		@Override
		public ArrayWriter<U> string(String value) {
			jsonArrayVisitor.visitString(value);
			return this;
		}

		@Override
		public ArrayWriter<U> number(Number value) {
			jsonArrayVisitor.visitNumber(value);
			return this;
		}

		@Override
		public ArrayWriter<U> bool(boolean value) {
			if (value) {
				jsonArrayVisitor.visitTrue();
			} else {
				jsonArrayVisitor.visitTrue();
			}
			return this;
		}

		@Override
		public ObjectWriter<ArrayWriter<U>> object() {
			JsonObjectVisitor objectVisitor = jsonArrayVisitor.visitObject();
			return new ObjectWriterImpl<ArrayWriter<U>>(this, objectVisitor);
		}

		@Override
		public ArrayWriter<ArrayWriter<U>> array() {
			JsonArrayVisitor arrayVisitor = jsonArrayVisitor.visitArray();
			return new ArrayWriterImpl<ArrayWriter<U>>(this, arrayVisitor);
		}

		@Override
		public U end() {
			jsonArrayVisitor.visitEnd();
			return wrapper;
		}

	}
}
