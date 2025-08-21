package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "DictionaryProxyServlet", urlPatterns = {"/api/dictionary"})
public class DictionaryProxyServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String word = request.getParameter("word");
		if (word == null || word.isBlank()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing word");
			return;
		}
		String encoded = URLEncoder.encode(word, StandardCharsets.UTF_8);
		// Using free dictionary API: https://api.dictionaryapi.dev
		URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + encoded);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(8000);
		conn.setReadTimeout(15000);
		int code = conn.getResponseCode();
		response.setStatus(code);
		response.setContentType("application/json;charset=UTF-8");
		try (InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
			 OutputStream os = response.getOutputStream()) {
			byte[] buf = new byte[4096];
			int n;
			while ((n = is.read(buf)) != -1) {
				os.write(buf, 0, n);
			}
		}
	}
}


