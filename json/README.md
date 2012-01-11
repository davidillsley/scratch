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

The Object Model API uses standard List<> and Map<> interfaces, alongside a holder - JSONValue - so that unsafe casting isn't necessary.

Complex navigation is expected to be done using a simple path language.

Examples of the above, and the other interfaces at https://github.com/davidillsley/scratch/tree/master/json/src/org/i5y/json/examples

(Rough) Javadoc at: http://downloads.illsley.org/temp/json/