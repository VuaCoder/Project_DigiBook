package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import javax.persistence.EntityManager;
import model.Document;
import model.UserAccount;
import util.JPAUtil;

@WebServlet(name = "PdfViewServlet", urlPatterns = {"/documents/view"})
public class PdfViewServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id");
		if (idParam == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing document id");
			return;
		}
		HttpSession session = request.getSession(false);
		UserAccount currentUser = session != null ? (UserAccount) session.getAttribute("currentUser") : null;
		if (currentUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		EntityManager em = JPAUtil.getEntityManager();
		try {
			Document doc = em.find(Document.class, Integer.valueOf(idParam));
			if (doc == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			request.setAttribute("document", doc);
			request.getRequestDispatcher("/function/pdf_reader.jsp").forward(request, response);
		} finally {
			em.close();
		}
	}
}


