package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.Course;
import util.JPAUtil;

@WebServlet(name = "AssignCoversServlet", urlPatterns = {"/admin/assign-covers"})
public class AssignCoversServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String coversDirPath = getServletContext().getRealPath("/img/GIAOTRINH");
		File coversDir = new File(coversDirPath);
		if (!coversDir.exists() || !coversDir.isDirectory()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Thư mục ảnh giáo trình không tồn tại: " + coversDirPath);
			return;
		}

		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		int updated = 0;
		try {
			tx.begin();
			TypedQuery<Course> q = em.createNamedQuery("Course.findAll", Course.class);
			List<Course> courses = q.getResultList();
			String[] exts = new String[]{".jpg", ".jpeg", ".png", ".webp"};
			// Build map: normalized filename (without ext) -> file name
			Map<String, String> keyToFilename = new HashMap<>();
			File[] files = coversDir.listFiles();
			if (files != null) {
				for (File f : files) {
					if (!f.isFile()) continue;
					String name = f.getName();
					String lower = name.toLowerCase(Locale.ROOT);
					boolean ok = false;
					for (String ext : exts) {
						if (lower.endsWith(ext)) { ok = true; break; }
					}
					if (!ok) continue;
					String base = name.substring(0, name.lastIndexOf('.'));
					String normBase = normalizeKey(base);
					if (!normBase.isEmpty()) {
						keyToFilename.put(normBase, name);
					}
				}
			}

			for (Course c : courses) {
				String baseName = c.getDepartment();
				if (baseName == null || baseName.trim().isEmpty()) continue;
				List<String> keys = buildPossibleKeys(baseName);
				String matchedFilename = null;
				for (String k : keys) {
					if (keyToFilename.containsKey(k)) { matchedFilename = keyToFilename.get(k); break; }
				}
				if (matchedFilename != null) {
					String publicPath = request.getContextPath() + "/img/GIAOTRINH/" + matchedFilename;
					c.setCoverImage(publicPath);
					em.merge(c);
					updated++;
				}
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			throw new ServletException(e);
		} finally {
			em.close();
		}

		response.setContentType("text/plain; charset=UTF-8");
		response.getWriter().write("Đã cập nhật ảnh bìa cho " + updated + " khóa học.\n");
	}

	private static String normalizeKey(String input) {
		if (input == null) return "";
		String lower = input.toLowerCase(Locale.ROOT).replace('đ', 'd');
		String nfd = Normalizer.normalize(lower, Normalizer.Form.NFD);
		String withoutDiacritics = nfd.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		// keep letters and digits only
		return withoutDiacritics.replaceAll("[^a-z0-9]", "");
	}

	private static List<String> buildPossibleKeys(String title) {
		List<String> keys = new ArrayList<>();
		String norm = normalizeKey(title);
		if (!norm.isEmpty()) keys.add(norm);
		// Build initials from words of a simplified string keeping spaces
		String simplified = Normalizer.normalize(title.toLowerCase(Locale.ROOT).replace('đ', 'd'), Normalizer.Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
				.replaceAll("[^a-z0-9]+", " ")
				.trim();
		if (!simplified.isEmpty()) {
			String[] parts = simplified.split("\\s+");
			StringBuilder initials = new StringBuilder();
			for (String p : parts) {
				if (!p.isEmpty()) initials.append(p.charAt(0));
			}
			String ini = initials.toString();
			if (!ini.isEmpty()) keys.add(ini);
		}
		// Unique
		Set<String> seen = new HashSet<>();
		List<String> unique = new ArrayList<>();
		for (String k : keys) {
			if (seen.add(k)) unique.add(k);
		}
		return unique;
	}
}


