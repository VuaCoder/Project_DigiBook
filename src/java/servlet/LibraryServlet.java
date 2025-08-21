package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.Document;
import model.UserAccount;
import util.JPAUtil;
import model.Course;

@WebServlet(name = "LibraryServlet", urlPatterns = {"/library"})
public class LibraryServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UserAccount currentUser = session != null ? (UserAccount) session.getAttribute("currentUser") : null;
		if (currentUser == null) { response.sendRedirect(request.getContextPath()+"/login"); return; }
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<Document> q = em.createQuery("SELECT d FROM Document d WHERE d.ownerId.id = :uid ORDER BY d.uploadedAt DESC", Document.class);
			q.setParameter("uid", currentUser.getId());
			List<Document> docs = q.getResultList();
			request.setAttribute("documents", docs);
			request.getRequestDispatcher("/function/library.jsp").forward(request, response);
		} finally { em.close(); }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Delete document and its associated course
		String deleteId = request.getParameter("deleteId");
		if (deleteId == null) { response.sendError(HttpServletResponse.SC_BAD_REQUEST); return; }
		
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = null; // Initialize tx to null
		try {
			tx = em.getTransaction();
			tx.begin();
			
			Document document = em.find(Document.class, Integer.valueOf(deleteId));
			if (document != null) {
				// Get the associated course before deleting document
				Course course = document.getCourseId();
				
				// Delete the document first
				em.remove(document);
				
				// Check if this course has any other documents
				TypedQuery<Document> courseDocsQuery = em.createQuery(
					"SELECT d FROM Document d WHERE d.courseId.id = :courseId", Document.class);
				courseDocsQuery.setParameter("courseId", course.getId());
				List<Document> remainingDocs = courseDocsQuery.getResultList();
				
				// If no more documents exist for this course, delete the course too
				if (remainingDocs.isEmpty()) {
					em.remove(course);
				}
			}
			
			tx.commit();
			response.sendRedirect(request.getContextPath()+"/library");
		} catch (Exception e) {
			if (tx != null && tx.isActive()) tx.rollback();
			request.setAttribute("error", "Lỗi khi xóa tài liệu: " + e.getMessage());
			doGet(request, response);
		} finally { 
			em.close(); 
		}
	}
}


