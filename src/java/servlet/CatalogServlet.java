package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Course;
import util.JPAUtil;

@WebServlet(name = "CatalogServlet", urlPatterns = {"/catalog"})
public class CatalogServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String department = trimOrNull(request.getParameter("department"));
		String author = trimOrNull(request.getParameter("author"));
		String yearParam = trimOrNull(request.getParameter("year"));
		Integer year = null;
		if (yearParam != null) {
			try { year = Integer.valueOf(yearParam); } catch (NumberFormatException ignored) {}
		}
		EntityManager em = JPAUtil.getEntityManager();
		try {
			// Query courses that have at least one document using EXISTS
			StringBuilder jpql = new StringBuilder(
				"SELECT c FROM Course c " +
				"WHERE EXISTS (SELECT 1 FROM Document d WHERE d.courseId = c)");
			Map<String, Object> params = new HashMap<>();
			if (department != null) { jpql.append(" AND c.department = :dept"); params.put("dept", department); }
			if (author != null) { jpql.append(" AND c.author = :auth"); params.put("auth", author); }
			if (year != null) { jpql.append(" AND c.publicationYear = :yr"); params.put("yr", year); }
			jpql.append(" ORDER BY c.createdAt DESC");
			
			TypedQuery<Course> q = em.createQuery(jpql.toString(), Course.class);
			for (Map.Entry<String, Object> e : params.entrySet()) q.setParameter(e.getKey(), e.getValue());
			List<Course> courses = q.getResultList();

			// For filter options: only show departments/authors/years from courses that have documents
			List<String> departments = em.createQuery(
				"SELECT DISTINCT c.department FROM Course c " +
				"WHERE c.department IS NOT NULL " +
				"AND EXISTS (SELECT 1 FROM Document d WHERE d.courseId = c) " +
				"ORDER BY c.department", String.class).getResultList();
				
			List<String> authors = em.createQuery(
				"SELECT DISTINCT c.author FROM Course c " +
				"WHERE c.author IS NOT NULL " +
				"AND EXISTS (SELECT 1 FROM Document d WHERE d.courseId = c) " +
				"ORDER BY c.author", String.class).getResultList();
				
			List<Integer> years = em.createQuery(
				"SELECT DISTINCT c.publicationYear FROM Course c " +
				"WHERE c.publicationYear IS NOT NULL " +
				"AND EXISTS (SELECT 1 FROM Document d WHERE d.courseId = c) " +
				"ORDER BY c.publicationYear DESC", Integer.class).getResultList();

			request.setAttribute("courses", courses);
			request.setAttribute("departments", departments);
			request.setAttribute("authors", authors);
			request.setAttribute("years", years);
			request.getRequestDispatcher("/function/catalog.jsp").forward(request, response);
		} finally {
			em.close();
		}
	}

	private static String trimOrNull(String s) {
		if (s == null) return null;
		s = s.trim();
		return s.isEmpty() ? null : s;
	}
}


