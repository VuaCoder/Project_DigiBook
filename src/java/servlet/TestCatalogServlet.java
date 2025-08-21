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
import util.JPAUtil;

@WebServlet(name = "TestCatalogServlet", urlPatterns = {"/test/catalog"})
public class TestCatalogServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("<h1>Test Catalog Query</h1>");
			
			// Test 1: Simple course query
			response.getWriter().write("<h2>Test 1: Simple Course Query</h2>");
			try {
				TypedQuery<Course> simpleQuery = em.createQuery("SELECT c FROM Course c", Course.class);
				List<Course> simpleCourses = simpleQuery.getResultList();
				response.getWriter().write("<p>Found " + simpleCourses.size() + " courses</p>");
			} catch (Exception e) {
				response.getWriter().write("<p style='color:red'>Error in simple query: " + e.getMessage() + "</p>");
			}
			
			// Test 2: Course with documents query
			response.getWriter().write("<h2>Test 2: Course with Documents Query</h2>");
			try {
				TypedQuery<Course> existsQuery = em.createQuery(
					"SELECT c FROM Course c WHERE EXISTS (SELECT 1 FROM Document d WHERE d.courseId = c)", 
					Course.class);
				List<Course> coursesWithDocs = existsQuery.getResultList();
				response.getWriter().write("<p>Found " + coursesWithDocs.size() + " courses with documents</p>");
			} catch (Exception e) {
				response.getWriter().write("<p style='color:red'>Error in exists query: " + e.getMessage() + "</p>");
			}
			
			// Test 3: Document count query
			response.getWriter().write("<h2>Test 3: Document Count Query</h2>");
			try {
				TypedQuery<Long> countQuery = em.createQuery(
					"SELECT COUNT(d) FROM Document d", Long.class);
				Long docCount = countQuery.getSingleResult();
				response.getWriter().write("<p>Total documents: " + docCount + "</p>");
			} catch (Exception e) {
				response.getWriter().write("<p style='color:red'>Error in count query: " + e.getMessage() + "</p>");
			}
			
		} catch (Exception e) {
			response.getWriter().write("<p style='color:red'>General error: " + e.getMessage() + "</p>");
		} finally {
			em.close();
		}
	}
}
