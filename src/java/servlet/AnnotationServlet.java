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
import jakarta.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.Annotation;
import model.Document;
import model.UserAccount;
import util.JPAUtil;

@WebServlet(name = "AnnotationServlet", urlPatterns = {"/api/annotations"})
public class AnnotationServlet extends HttpServlet {

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
			TypedQuery<Annotation> q = em.createQuery("SELECT a FROM Annotation a WHERE a.userId.id = :uid AND a.documentId.id = :did ORDER BY a.createdAt DESC", Annotation.class);
			q.setParameter("uid", currentUser.getId());
			q.setParameter("did", Integer.valueOf(documentIdParam));
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (Annotation a : q.getResultList()) {
				JsonObjectBuilder obj = Json.createObjectBuilder()
					.add("id", a.getId())
					.add("pageNumber", a.getPageNumber() == null ? -1 : a.getPageNumber())
					.add("highlightText", a.getHighlightText() == null ? "" : a.getHighlightText())
					.add("noteText", a.getNoteText() == null ? "" : a.getNoteText());
				arr.add(obj);
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
		String highlight = request.getParameter("highlightText");
		String note = request.getParameter("noteText");
		EntityManager em = JPAUtil.getEntityManager();
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Document doc = em.find(Document.class, Integer.valueOf(documentIdParam));
			Annotation a = new Annotation();
			a.setUserId(currentUser);
			a.setDocumentId(doc);
			a.setPageNumber(pageParam == null ? null : Integer.valueOf(pageParam));
			a.setHighlightText(highlight);
			a.setNoteText(note);
			a.setCreatedAt(new Date());
			em.persist(a);
			tx.commit();
			response.setContentType("application/json;charset=UTF-8");
			JsonObject obj = Json.createObjectBuilder().add("status", "ok").add("id", a.getId()).build();
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
			
			Annotation a = em.find(Annotation.class, Integer.valueOf(idParam));
			if (a == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			// Check if the current user owns this annotation
			if (!a.getUserId().getId().equals(currentUser.getId())) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			em.remove(a);
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


