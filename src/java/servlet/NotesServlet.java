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
import javax.persistence.TypedQuery;
import model.Annotation;
import model.UserAccount;
import model.Vocabulary;
import util.JPAUtil;

@WebServlet(name = "NotesServlet", urlPatterns = {"/notes"})
public class NotesServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UserAccount currentUser = session != null ? (UserAccount) session.getAttribute("currentUser") : null;
		
		if (currentUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		EntityManager em = JPAUtil.getEntityManager();
		try {
			// Fetch all annotations for the current user
			TypedQuery<Annotation> annotationsQuery = em.createQuery(
				"SELECT a FROM Annotation a WHERE a.userId.id = :userId ORDER BY a.createdAt DESC", 
				Annotation.class);
			annotationsQuery.setParameter("userId", currentUser.getId());
			List<Annotation> annotations = annotationsQuery.getResultList();
			
			// Fetch all vocabulary for the current user
			TypedQuery<Vocabulary> vocabularyQuery = em.createQuery(
				"SELECT v FROM Vocabulary v WHERE v.userId.id = :userId ORDER BY v.createdAt DESC", 
				Vocabulary.class);
			vocabularyQuery.setParameter("userId", currentUser.getId());
			List<Vocabulary> vocabularies = vocabularyQuery.getResultList();
			
			// Set attributes for JSP
			request.setAttribute("annotations", annotations);
			request.setAttribute("vocabularies", vocabularies);
			
			// Forward to notes page
			request.getRequestDispatcher("/function/notes.jsp").forward(request, response);
			
		} finally {
			em.close();
		}
	}
}
