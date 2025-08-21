package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthTokenUtil {
	private static final String SECRET = "CHANGE_ME_SECRET_2025_DigiBook";

	public static String generateToken(int userId) {
		String payload = String.valueOf(userId);
		String signature = hmacSha256(payload, SECRET);
		return payload + ":" + signature;
	}

	public static Integer verifyAndExtractUserId(String token) {
		if (token == null || !token.contains(":")) return null;
		String[] parts = token.split(":", 2);
		String payload = parts[0];
		String signature = parts[1];
		String expected = hmacSha256(payload, SECRET);
		if (expected.equals(signature)) {
			try {
				return Integer.parseInt(payload);
			} catch (NumberFormatException ex) {
				return null;
			}
		}
		return null;
	}

	private static String hmacSha256(String data, String key) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(key.getBytes(StandardCharsets.UTF_8));
			byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte b : hash) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}

