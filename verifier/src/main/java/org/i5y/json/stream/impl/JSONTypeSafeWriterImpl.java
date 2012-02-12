package org.i5y.json.stream.impl;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.i5y.json.stream.JSONWriter;
import org.i5y.json.stream.JSONTypeSafeWriters;

class JSONTypeSafeWriterImpl implements JSONTypeSafeWriters {

	private static class InsideArrayImpl<T> implements InsideArray<T>{

		final T wrapper;
		final JSONWriter generator;
		InsideArrayImpl(final T wrapper, JSONWriter generator){
			this.wrapper = wrapper;
			this.generator = generator;
		}
		
		@Override
		public InsideArray<T> literal(String literal) {
			generator.literal(literal);
			return this;
		}

		@Override
		public InsideArray<T> literal(int literal) {
			generator.literal(literal);
			return this;
		}

		@Override
		public InsideArray<T> literal(long literal) {
			generator.literal(literal);
			return this;
		}

		@Override
		public InsideArray<T> literal(BigInteger literal) {
			generator.literal(literal);
			return this;
		}

		@Override
		public InsideArray<T> literal(float literal) {
			generator.literal(literal);
			return this;
		}

		@Override
		public InsideArray<T> literal(double literal) {
			generator.literal(literal);
			return this;
		}

		@Override
		public InsideArray<T> literal(BigDecimal literal) {
			generator.literal(literal);
			return this;
		}

		@Override
		public InsideArray<T> literal(boolean literal) {
			generator.literal(literal);
			return this;
		}

		@Override
		public InsideArray<T> literalNull() {
			generator.nullLiteral();
			return this;
		}

		@Override
		public InsideObject<InsideArray<T>> startObject() {
			generator.startObject();
			return new InsideObjectImpl<InsideArray<T>>(this, generator);
		}

		@Override
		public InsideArray<InsideArray<T>> startArray() {
			generator.startArray();
			return new InsideArrayImpl<InsideArray<T>>(this, generator);
		}

		@Override
		public T endArray() {
			generator.endArray();
			return wrapper;
		}
	}
	
	private static class InsideObjectImpl<T> implements InsideObject<T>{

		final T wrapper;
		final JSONWriter generator;
		
		public InsideObjectImpl(final T wrapper, final JSONWriter generator){
			this.wrapper= wrapper;
			this.generator = generator;
		}
		
		@Override
		public InsideObject<T> property(String name, String literal) {
			generator.property(name, literal);
			return this;
		}

		@Override
		public InsideObject<T> property(String name, int literal) {
			generator.property(name, literal);
			return this;
		}

		@Override
		public InsideObject<T> property(String name, long literal) {
			generator.property(name, literal);
			return this;
		}

		@Override
		public InsideObject<T> property(String name, BigInteger literal) {
			generator.property(name, literal);
			return this;
		}

		@Override
		public InsideObject<T> property(String name, float literal) {
			generator.property(name, literal);
			return this;
		}

		@Override
		public InsideObject<T> property(String name, double literal) {
			generator.property(name, literal);
			return this;
		}

		@Override
		public InsideObject<T> property(String name, BigDecimal literal) {
			generator.property(name, literal);
			return this;
		}

		@Override
		public InsideObject<T> property(String name, boolean literal) {
			generator.property(name, literal);
			return this;
		}

		@Override
		public InsideObject<T> propertyNull(String name) {
			generator.nullProperty(name);
			return this;
		}

		@Override
		public PostPropertyName<InsideObject<T>> defineProperty(String name) {
			generator.propertyName(name);
			return new PostPropertyNameImpl<InsideObject<T>>(this, generator);
		}

		@Override
		public T endObject() {
			generator.endObject();
			return wrapper;
		}
		
	}
	
	private static class PostPropertyNameImpl<T> implements PostPropertyName<T>{

		final T wrapper;
		final JSONWriter generator;
		public PostPropertyNameImpl(final T wrapper, final JSONWriter generator){
			this.wrapper = wrapper;
			this.generator = generator;
		}
		
		@Override
		public T literal(String literal) {
			generator.literal(literal);
			return wrapper;
		}

		@Override
		public T literal(int literal) {
			generator.literal(literal);
			return wrapper;
		}

		@Override
		public T literal(long literal) {
			generator.literal(literal);
			return wrapper;
		}

		@Override
		public T literal(BigInteger literal) {
			generator.literal(literal);
			return wrapper;
		}

		@Override
		public T literal(float literal) {
			generator.literal(literal);
			return wrapper;
		}

		@Override
		public T literal(double literal) {
			generator.literal(literal);
			return wrapper;
		}

		@Override
		public T literal(BigDecimal literal) {
			generator.literal(literal);
			return wrapper;
		}

		@Override
		public T literal(boolean literal) {
			generator.literal(literal);
			return wrapper;
		}

		@Override
		public T literalNull() {
			generator.nullLiteral();
			return wrapper;
		}

		@Override
		public InsideObject<T> startObject() {
			generator.startObject();
			return new InsideObjectImpl<T>(wrapper, generator);
		}

		@Override
		public InsideArray<T> startArray() {
			generator.startArray();
			return new InsideArrayImpl<T>(wrapper, generator);
		}
	}
	
	public static class ArrayWriterImpl implements ArrayWriter{
		
		final JSONWriter generator;
		
		public ArrayWriterImpl(Writer writer){
			 generator = new JSONWriterImpl(writer);
		}
		
		@Override
		public InsideArray<ArrayWriter> startArray() {
			generator.startArray();
			return new InsideArrayImpl<ArrayWriter>(this,generator);
		}

		@Override
		public void flush() throws IOException {
			generator.flush();
		}

		@Override
		public void close() throws IOException {
			generator.close();
		}
	}

	public static class ObjectWriterImpl implements ObjectWriter{
		
		final JSONWriter generator;
		
		public ObjectWriterImpl(Writer writer){
			 generator = new JSONWriterImpl(writer);
		}
		
		@Override
		public InsideObject<ObjectWriter> startObject() {
			generator.startObject();
			return new InsideObjectImpl<ObjectWriter>(this,generator);
		}

		@Override
		public void flush() throws IOException {
			generator.flush();
		}

		@Override
		public void close() throws IOException {
			generator.close();
		}
	}
}
