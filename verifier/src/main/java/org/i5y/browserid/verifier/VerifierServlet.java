package org.i5y.browserid.verifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.i5y.browserid.verifier.Verifier.Results;
import org.i5y.json.stream.impl.JSONStreamFactoryImpl;

@WebServlet(name = "verifier", urlPatterns = { "/verifier" })
public class VerifierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		URI requestURI = URI.create(request.getRequestURL().toString());

		String bundle = getCertAndAssertion(request);

		int requestPort = requestURI.getPort();

		String expectedAudience = requestURI.getScheme()
				+ "://"
				+ requestURI.getHost()
				+ (requestPort == 80 || requestPort <= 0 ? "" : ":"
						+ requestURI.getPort());

		Results results = Verifier.verify(bundle, expectedAudience);
		response.setContentType("application/json");
		new JSONStreamFactoryImpl().createObjectWriter(response.getWriter())
				.startObject().defineProperty("verified")
				.literal(results.verified).defineProperty("identity")
				.literal(results.identity).endObject().close();
	}

	private String getCertAndAssertion(HttpServletRequest request)
			throws ServletException, IOException {
		String full = "";
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		String line = br.readLine();
		while (line != null) {
			full += line;
			// System.out.println(line);
			line = br.readLine();
		}
		return full.trim().split("=")[1];
	}
}
