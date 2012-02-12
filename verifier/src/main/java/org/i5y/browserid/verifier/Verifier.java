package org.i5y.browserid.verifier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.i5y.json.stream.JSONEvent;
import org.i5y.json.stream.impl.JSONParserImpl;

public class Verifier {

	public static class Results {
		public final boolean verified;
		public final String identity;

		public Results(boolean verified, String identity) {
			this.verified = verified;
			this.identity = identity;
		}
	}

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	private static Map<String, String> retrieveCertificate(String issuer) {
		try {
			URL issuerCertURL = new URL("https://" + issuer
					+ "/.well-known/browserid");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) issuerCertURL
					.openConnection();

			InputStream is = httpUrlConnection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int next = is.read();
			while (next >= 0) {
				baos.write(next);
				next = is.read();
			}
			is.close();
			byte[] bytes = baos.toByteArray();
			Map<String, String> details = new HashMap<String, String>();
			JSONParserImpl jpi = new JSONParserImpl(new InputStreamReader(
					new ByteArrayInputStream(bytes)));
			while (jpi.next() != JSONEvent.PROPERTY_NAME
					&& !"public-key".equals(jpi.string()))
				;
			while (jpi.next() != JSONEvent.OBJECT_END) {
				if (jpi.current() == JSONEvent.PROPERTY_NAME) {
					String propertyName = jpi.string();
					jpi.advance();
					String propertyValue = jpi.string();
					details.put(propertyName, propertyValue);
				}
			}
			return details;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Results verify(String bundle, String audience) {
		int delimitedAt = bundle.indexOf("~");
		String part1 = bundle.substring(0, delimitedAt);
		String part2 = bundle.substring(delimitedAt + 1, bundle.length());

		String[] part1sections = part1.split("\\.");

		String part1bodyDecoded = new String(decodeURLBase64(part1sections[1]));
		JSONParserImpl jpi = new JSONParserImpl(part1bodyDecoded);

		String iss = null;
		String exp = null;
		String iat = null;
		String principal = null;
		Map<String, String> identityCert = null;

		while (jpi.next() != JSONEvent.INPUT_END
				&& (iss == null || exp == null || iat == null
						|| principal == null || identityCert == null)) {
			if (jpi.current() == JSONEvent.PROPERTY_NAME) {
				String propertyName = jpi.string();
				if ("exp".equals(propertyName)) {
					jpi.advance();
					exp = jpi.string();
				} else if ("iat".equals(propertyName)) {
					jpi.advance();
					iat = jpi.string();
				} else if ("principal".equals(propertyName)) {
					while (jpi.next() != JSONEvent.OBJECT_END) {
						if (jpi.current() == JSONEvent.PROPERTY_NAME
								&& "email".equals(jpi.string())) {
							jpi.advance();
							principal = jpi.string();
						}
					}
				} else if ("public-key".equals(propertyName)) {
					identityCert = new HashMap<String, String>();
					while (jpi.next() != JSONEvent.OBJECT_END) {
						if (jpi.current() == JSONEvent.PROPERTY_NAME) {
							propertyName = jpi.string();
							jpi.advance();
							identityCert.put(propertyName, jpi.string());
						}
					}
				} else if ("iss".equals(propertyName)) {
					jpi.advance();
					iss = jpi.string();
				}
			}
		}

		Map<String, String> issuerCertificate = retrieveCertificate(iss);

		boolean part1Verified = verify(issuerCertificate, part1sections[0]
				+ "." + part1sections[1], part1sections[2]);

		String[] part2sections = part2.split("\\.");

		String header2 = part2sections[0];

		String body2 = part2sections[1];
		String part2bodyDecoded = new String(decodeURLBase64(part2sections[1]));

		jpi = new JSONParserImpl(part2bodyDecoded);

		String part2exp = null;
		String aud = null;
		while (jpi.next() != JSONEvent.INPUT_END
				&& (part2exp == null || aud == null)) {
			if (jpi.current() == JSONEvent.PROPERTY_NAME) {
				String propertyName = jpi.string();
				if ("exp".equals(propertyName)) {
					jpi.advance();
					part2exp = jpi.string();
				} else if ("aud".equals(propertyName)) {
					jpi.advance();
					aud = jpi.string();
				}
			}
		}

		boolean part2Verified = verify(identityCert, header2 + "." + body2,
				part2sections[2]);

		long part1expms = Long.valueOf(exp);
		long iatms = Long.valueOf(iat);
		long part2expms = Long.valueOf(part2exp);
		long currentms = System.currentTimeMillis();

		System.out.println("Current Time: " + currentms);
		System.out.println("Issued At: " + iatms);
		System.out.println("Part 1 Expiry: " + part1expms);
		System.out.println("Part 2 Export: " + part2expms);
		boolean timingValid = (iatms < currentms) && (currentms < part1expms)
				&& (currentms < part2expms);
		System.out.println("Timing Valid: " + timingValid);

		System.out.println("Expected Audience: " + audience);
		System.out.println("Assertion Audience: " + aud);
		boolean audienceValid = audience.equalsIgnoreCase(aud);
		System.out.println("Audience Valid: " + audienceValid);

		String expectedIssuer = principal.split("@")[1];
		System.out.println("Expected Issuer: " + expectedIssuer
				+ " (or browserid.org)");
		System.out.println("Actual Issuer: " + iss);
		boolean validIssuer = iss.equalsIgnoreCase(expectedIssuer)
				|| "browserid.org".equalsIgnoreCase(iss);
		System.out.println("Valid Issuer: " + validIssuer);

		System.out.println("Part 1 verified: " + part1Verified);
		System.out.println("Part 2 verified: " + part2Verified);
		
		boolean fullyVerified = timingValid && audienceValid && validIssuer
				&& part1Verified && part2Verified;
		
		System.out.println("Fully Verified: "+fullyVerified);
		
		return new Results(fullyVerified, fullyVerified ? principal : "");
	}

	private static boolean verify(Map<String, String> key, String message,
			String signature) {
		if ("DS".equals(key.get("algorithm"))) {
			return verifyDSA(key.get("y"), key.get("p"), key.get("q"),
					key.get("g"), message, signature);
		} else {
			return verifyRSA(key.get("n"), key.get("e"), message, signature);
		}
	}

	private static boolean verifyRSA(String n, String e, String message,
			String signature) {
		try {
			RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
					new BigInteger(n, 10), new BigInteger(e, 10));
			Signature sig = Signature.getInstance("SHA256withRSA");

			PublicKey pk = KeyFactory.getInstance("RSA").generatePublic(
					publicKeySpec);

			sig.initVerify(pk);
			sig.update(message.getBytes());

			byte[] sigBytes = decodeURLBase64(signature);
			return sig.verify(sigBytes);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private static byte[] decodeURLBase64(String input) {
		String transformed = input.replace('-', '+').replace('_', '/');
		final String padding;
		switch (transformed.length() % 4) {
		case 0:
			padding = "";
			break;
		case 2:
			padding = "==";
			break;
		case 3:
			padding = "=";
			break;
		default:
			throw new IllegalArgumentException();
		}
		return Base64.decode(transformed + padding);
	}

	private static boolean verifyDSA(String yString, String pString,
			String qString, String gString, String message, String signature) {

		String fullSignatureHex = new BigInteger(decodeURLBase64("AAAA"
				+ signature)).toString(16);

		String rStringHex = fullSignatureHex.substring(0,
				fullSignatureHex.length() / 2);
		String sStringHex = fullSignatureHex.substring(
				fullSignatureHex.length() / 2, fullSignatureHex.length());

		BigInteger r = new BigInteger(rStringHex, 16);
		BigInteger s = new BigInteger(sStringHex, 16);

		DERSequence derSignature = new DERSequence(new DERInteger[] {
				new DERInteger(r), new DERInteger(s) });
		try {
			Signature sig = Signature.getInstance("SHA256withDSA", "BC");

			DSAPublicKeySpec spec = new DSAPublicKeySpec(new BigInteger(
					yString, 16), new BigInteger(pString, 16), new BigInteger(
					qString, 16), new BigInteger(gString, 16));

			PublicKey pk = KeyFactory.getInstance("DSA").generatePublic(spec);

			sig.initVerify(pk);
			sig.update(message.getBytes());
			return sig.verify(derSignature.getDEREncoded());
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
