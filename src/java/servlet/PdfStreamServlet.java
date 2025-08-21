package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.persistence.EntityManager;
import model.Document;
import util.JPAUtil;

@WebServlet(name = "PdfStreamServlet", urlPatterns = {"/documents/stream"})
public class PdfStreamServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id");
		if (idParam == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing document id");
			return;
		}
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Document doc = em.find(Document.class, Integer.valueOf(idParam));
			if (doc == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			String filePath = doc.getFilePath();
			// Force disable range/byte serving which may block some PDF.js setups
			response.setHeader("Accept-Ranges", "none");
			String fileName = "document.pdf";
			if (doc.getCourseId() != null && doc.getCourseId().getTitle() != null) {
				fileName = doc.getCourseId().getTitle().replaceAll("[\\\\/:*?\"<>|]", "_") + ".pdf";
			}
			response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
			if (filePath != null && (filePath.startsWith("http://") || filePath.startsWith("https://"))) {
				URL url = new URL(filePath);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(10000);
				conn.setReadTimeout(20000);
				int code = conn.getResponseCode();
				if (code != 200) {
					response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
					return;
				}
				String contentType = conn.getContentType();
				if (contentType == null || !contentType.toLowerCase().contains("pdf")) {
					contentType = "application/pdf";
				}
				response.setContentType(contentType);
				int len = conn.getContentLength();
				if (len > 0) response.setContentLength(len);
				try (java.io.InputStream is = conn.getInputStream(); OutputStream out = response.getOutputStream()) {
					byte[] buffer = new byte[8192];
					int bytesRead;
					while ((bytesRead = is.read(buffer)) != -1) {
						out.write(buffer, 0, bytesRead);
					}
				}
			} else {
				File file = new File(filePath);
				if (!file.exists()) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				response.setContentType("application/pdf");
				response.setContentLengthLong(file.length());
				try (FileInputStream fis = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
					byte[] buffer = new byte[8192];
					int bytesRead;
					while ((bytesRead = fis.read(buffer)) != -1) {
						out.write(buffer, 0, bytesRead);
					}
				}
			}
		} finally {
			em.close();
		}
	}
}


