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
import java.net.URL;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "TranslateProxyServlet", urlPatterns = {"/api/translate"})
public class TranslateProxyServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("text");
		String target = request.getParameter("target");
		String source = request.getParameter("source");
		if (text == null || text.isBlank()) { response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing text"); return; }
		if (target == null || target.isBlank()) target = "vi";
		if (source == null || source.isBlank()) source = "auto";
		// LibreTranslate public endpoint
		URL url = new URL("https://libretranslate.com/translate");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		conn.setDoOutput(true);
		String payload = "{\"q\":" + toJson(text) + ",\"source\":\"" + source + "\",\"target\":\"" + target + "\"}";
		try (OutputStream os = conn.getOutputStream()) {
			os.write(payload.getBytes(StandardCharsets.UTF_8));
		}
		int code = conn.getResponseCode();
		response.setStatus(code);
		response.setContentType("application/json;charset=UTF-8");
		try (InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
			 OutputStream os = response.getOutputStream()) {
			byte[] buf = new byte[4096];
			int n; while ((n = is.read(buf)) != -1) os.write(buf, 0, n);
		}
	}

	private String toJson(String s) {
		String escaped = s.replace("\\", "\\\\").replace("\"", "\\\"");
		return "\"" + escaped + "\"";
	}
}


