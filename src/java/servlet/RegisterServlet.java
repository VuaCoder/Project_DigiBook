package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.UserAccount;
import util.JPAUtil;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/function/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        if (username == null || username.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            request.getRequestDispatcher("/function/register.jsp").forward(request, response);
            return;
        }
        if (!password.equals(confirm)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            request.getRequestDispatcher("/function/register.jsp").forward(request, response);
            return;
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Check uniqueness
            TypedQuery<Long> q1 = em.createQuery("SELECT COUNT(u) FROM UserAccount u WHERE u.username = :u", Long.class);
            q1.setParameter("u", username);
            TypedQuery<Long> q2 = em.createQuery("SELECT COUNT(u) FROM UserAccount u WHERE u.email = :e", Long.class);
            q2.setParameter("e", email);
            if (q1.getSingleResult() > 0) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại.");
                request.getRequestDispatcher("/function/register.jsp").forward(request, response);
                return;
            }
            if (q2.getSingleResult() > 0) {
                request.setAttribute("error", "Email đã được sử dụng.");
                request.getRequestDispatcher("/function/register.jsp").forward(request, response);
                return;
            }

            EntityTransaction tx = em.getTransaction();
            tx.begin();
            UserAccount user = new UserAccount();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setCreatedAt(new Date());
            em.persist(user);
            tx.commit();

            request.setAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
            request.getRequestDispatcher("/function/login.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}
