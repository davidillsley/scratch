Some experimentation with Java JSON APIs/Parser implementation.

So far, I'm happiest about the 'TypeSafeWriter' which uses generics to provide a fluent API which enforces valid JSON generation.

From the javadoc of JSONTypeSafeWriters:

These interfaces provide a <em>type safe</em> and fluent interface for outputting JSON using a streaming approach.
<pre>
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  streamFactory.createObjectWriter(response.getWriter())
  .startObject()
  .property("stock","ACME")
  .property("price", 100)
  .property("lastUpdated",new Date().toString())
  .endObject()
  .close();
}
</pre>