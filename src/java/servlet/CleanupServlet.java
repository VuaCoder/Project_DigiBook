package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.Course;
import util.JPAUtil;

@WebServlet(name = "CleanupServlet", urlPatterns = {"/admin/cleanup"})
public class CleanupServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			// Find courses that have no documents using EXISTS
			TypedQuery<Course> orphanedCoursesQuery = em.createQuery(
				"SELECT c FROM Course c " +
				"WHERE NOT EXISTS (SELECT 1 FROM Document d WHERE d.courseId = c)", 
				Course.class);
			
			List<Course> orphanedCourses = orphanedCoursesQuery.getResultList();
			
			if (orphanedCourses.isEmpty()) {
				response.getWriter().write("Không có course nào bị orphaned.");
				return;
			}
			
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			int deletedCount = 0;
			for (Course course : orphanedCourses) {
				em.remove(course);
				deletedCount++;
			}
			
			tx.commit();
			
			response.getWriter().write("Đã xóa " + deletedCount + " course orphaned.");
			
		} catch (Exception e) {
			response.getWriter().write("Lỗi khi cleanup: " + e.getMessage());
		} finally {
			em.close();
		}
	}
}
