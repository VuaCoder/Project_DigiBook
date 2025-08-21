package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.UserAccount;
import util.JPAUtil;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/function/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identifier = request.getParameter("identifier");
        String password = request.getParameter("password");

        if (identifier == null || identifier.trim().isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin.");
            request.getRequestDispatcher("/function/login.jsp").forward(request, response);
            return;
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<UserAccount> query;
            if (identifier.contains("@")) {
                query = em.createNamedQuery("UserAccount.findByEmail", UserAccount.class);
                query.setParameter("email", identifier);
            } else {
                query = em.createNamedQuery("UserAccount.findByUsername", UserAccount.class);
                query.setParameter("username", identifier);
            }

            UserAccount user = query.getSingleResult();
            if (user != null && user.getPassword() != null && user.getPassword().equals(password)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("currentUser", user);
                String remember = request.getParameter("remember");
                if ("on".equals(remember)) {
                    jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("rememberMe", util.AuthTokenUtil.generateToken(user.getId()));
                    cookie.setHttpOnly(true);
                    cookie.setPath(request.getContextPath());
                    cookie.setMaxAge(60 * 60 * 24 * 30);
                    response.addCookie(cookie);
                }
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            request.setAttribute("error", "Thông tin đăng nhập không đúng.");
            request.getRequestDispatcher("/function/login.jsp").forward(request, response);
        } catch (NoResultException ex) {
            request.setAttribute("error", "Thông tin đăng nhập không đúng.");
            request.getRequestDispatcher("/function/login.jsp").forward(request, response);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
