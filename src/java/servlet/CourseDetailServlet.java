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
import model.Course;
import model.Document;
import util.JPAUtil;

@WebServlet(name = "CourseDetailServlet", urlPatterns = {"/course"})
public class CourseDetailServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id");
		if (idParam == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Course course = em.find(Course.class, Integer.valueOf(idParam));
			if (course == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			TypedQuery<Document> q = em.createQuery("SELECT d FROM Document d WHERE d.courseId.id = :cid ORDER BY d.uploadedAt DESC", Document.class);
			q.setParameter("cid", course.getId());
			List<Document> docs = q.getResultList();
			request.setAttribute("course", course);
			request.setAttribute("documents", docs);
			request.getRequestDispatcher("/function/course_detail.jsp").forward(request, response);
		} finally { em.close(); }
	}
}


