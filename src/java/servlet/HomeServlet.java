package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Document;
import util.JPAUtil;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<Document> q = em.createQuery(
				"SELECT DISTINCT d FROM Document d " +
				"LEFT JOIN FETCH d.courseId " +
				"ORDER BY d.uploadedAt DESC", Document.class);
			q.setMaxResults(8);
			List<Document> recent = q.getResultList();
			
			// Debug logging
			System.out.println("=== HomeServlet Debug ===");
			System.out.println("Total documents found: " + recent.size());
			for (Document doc : recent) {
				System.out.println("Document ID: " + doc.getId());
				System.out.println("  Title: " + doc.getTitle());
				if (doc.getCourseId() != null) {
					System.out.println("  Course Department: " + doc.getCourseId().getDepartment());
					System.out.println("  Cover Image: " + doc.getCourseId().getCoverImage());
				} else {
					System.out.println("  No Course associated");
				}
			}
			System.out.println("========================");
			
			request.setAttribute("recentDocuments", recent);
			request.getRequestDispatcher("/common/home.jsp").forward(request, response);
		} finally {
			em.close();
		}
	}
}


