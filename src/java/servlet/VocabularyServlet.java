package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.Document;
import model.UserAccount;
import model.Vocabulary;
import util.JPAUtil;

@WebServlet(name = "VocabularyServlet", urlPatterns = {"/api/vocabulary"})
public class VocabularyServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String documentIdParam = request.getParameter("documentId");
		HttpSession session = request.getSession(false);
		UserAccount currentUser = session != null ? (UserAccount) session.getAttribute("currentUser") : null;
		if (currentUser == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<Vocabulary> q = em.createNamedQuery("Vocabulary.findByDocumentAndUser", Vocabulary.class);
			q.setParameter("documentId", Integer.valueOf(documentIdParam));
			q.setParameter("userId", currentUser.getId());
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (Vocabulary v : q.getResultList()) {
				arr.add(Json.createObjectBuilder()
					.add("id", v.getId())
					.add("word", v.getWord())
					.add("meaning", v.getMeaning() == null ? "" : v.getMeaning())
					.add("pageNumber", v.getPageNumber() == null ? -1 : v.getPageNumber())
					.add("contextText", v.getContextText() == null ? "" : v.getContextText()));
			}
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(arr.build().toString());
		} finally {
			em.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		UserAccount currentUser = session != null ? (UserAccount) session.getAttribute("currentUser") : null;
		if (currentUser == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		String documentIdParam = request.getParameter("documentId");
		String pageParam = request.getParameter("pageNumber");
		String word = request.getParameter("word");
		String meaning = request.getParameter("meaning");
		String contextText = request.getParameter("contextText");
		if (word == null || word.isBlank()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing word");
			return;
		}
		EntityManager em = JPAUtil.getEntityManager();
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Document doc = em.find(Document.class, Integer.valueOf(documentIdParam));
			Vocabulary v = new Vocabulary();
			v.setUserId(currentUser);
			v.setDocumentId(doc);
			v.setPageNumber(pageParam == null ? null : Integer.valueOf(pageParam));
			v.setWord(word);
			v.setMeaning(meaning);
			v.setContextText(contextText);
			v.setCreatedAt(new Date());
			em.persist(v);
			tx.commit();
			response.setContentType("application/json;charset=UTF-8");
			JsonObject obj = Json.createObjectBuilder().add("status", "ok").add("id", v.getId()).build();
			response.getWriter().write(obj.toString());
		} finally {
			em.close();
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id");
		if (idParam == null) { 
			response.sendError(HttpServletResponse.SC_BAD_REQUEST); 
			return; 
		}
		
		HttpSession session = request.getSession(false);
		UserAccount currentUser = session != null ? (UserAccount) session.getAttribute("currentUser") : null;
		if (currentUser == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		EntityManager em = JPAUtil.getEntityManager();
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			Vocabulary v = em.find(Vocabulary.class, Integer.valueOf(idParam));
			if (v == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			// Check if the current user owns this vocabulary
			if (!v.getUserId().getId().equals(currentUser.getId())) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			em.remove(v);
			tx.commit();
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally { 
			em.close(); 
		}
	}
}


