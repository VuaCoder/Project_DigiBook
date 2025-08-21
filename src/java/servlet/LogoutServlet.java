package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performLogout(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performLogout(request, response);
    }
    
    private void performLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Invalidate session
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Remove all session attributes
            session.removeAttribute("currentUser");
            session.invalidate();
        }
        
        // Set a logout flag cookie to prevent RememberMeFilter from recreating session
        Cookie logoutFlag = new Cookie("logoutFlag", "true");
        logoutFlag.setMaxAge(60); // Expire in 1 minute
        logoutFlag.setPath("/");
        response.addCookie(logoutFlag);
        
        // Remove rememberMe cookie completely
        Cookie rememberMeCookie = new Cookie("rememberMe", "");
        rememberMeCookie.setMaxAge(0);
        rememberMeCookie.setPath("/");
        rememberMeCookie.setDomain(request.getServerName());
        response.addCookie(rememberMeCookie);
        
        // Also try to remove with different path variations
        Cookie rememberMeCookie2 = new Cookie("rememberMe", "");
        rememberMeCookie2.setMaxAge(0);
        rememberMeCookie2.setPath(request.getContextPath());
        response.addCookie(rememberMeCookie2);
        
        // Clear any other authentication cookies if they exist
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("auth_") || cookie.getName().equals("JSESSIONID")) {
                    Cookie clearCookie = new Cookie(cookie.getName(), "");
                    clearCookie.setMaxAge(0);
                    clearCookie.setPath("/");
                    response.addCookie(clearCookie);
                }
            }
        }
        
        // Redirect to home page
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
