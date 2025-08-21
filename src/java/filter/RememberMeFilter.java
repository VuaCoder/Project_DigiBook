package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import javax.persistence.EntityManager;
import model.UserAccount;
import util.AuthTokenUtil;
import util.JPAUtil;

public class RememberMeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        
        // Check if user just logged out - if so, don't recreate session
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("logoutFlag".equals(c.getName())) {
                    // User just logged out, don't recreate session
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        
        // Only check for rememberMe cookie if there's no active session
        if (session == null || session.getAttribute("currentUser") == null) {
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("rememberMe".equals(c.getName()) && c.getValue() != null && !c.getValue().isEmpty()) {
                        Integer userId = AuthTokenUtil.verifyAndExtractUserId(c.getValue());
                        if (userId != null) {
                            EntityManager em = JPAUtil.getEntityManager();
                            try {
                                UserAccount user = em.find(UserAccount.class, userId);
                                if (user != null) {
                                    // Create new session and set user
                                    HttpSession newSession = httpRequest.getSession(true);
                                    newSession.setAttribute("currentUser", user);
                                    // Set session timeout to 30 minutes
                                    newSession.setMaxInactiveInterval(1800);
                                }
                            } finally {
                                em.close();
                            }
                        }
                        break;
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
