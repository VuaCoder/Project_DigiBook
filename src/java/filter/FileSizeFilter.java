package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "FileSizeFilter", urlPatterns = {"/documents/add"})
public class FileSizeFilter implements Filter {
    
    private static final long MAX_FILE_SIZE = 52428800L; // 50MB
    private static final long MAX_REQUEST_SIZE = 52428800L; // 50MB
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Only check POST requests with multipart content (upload mode)
        if ("POST".equalsIgnoreCase(httpRequest.getMethod()) && 
            httpRequest.getContentType() != null && 
            httpRequest.getContentType().startsWith("multipart/form-data")) {
            
            // Check content length
            int contentLength = httpRequest.getContentLength();
            if (contentLength > MAX_REQUEST_SIZE) {
                httpResponse.setStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
                httpResponse.setContentType("text/html;charset=UTF-8");
                httpResponse.getWriter().write(
                    "<html><body><h1>File quá lớn</h1>" +
                    "<p>Kích thước file vượt quá giới hạn 50MB.</p>" +
                    "<p>Vui lòng chọn file nhỏ hơn hoặc sử dụng đường dẫn URL.</p>" +
                    "<a href='javascript:history.back()'>Quay lại</a></body></html>"
                );
                return;
            }
        }
        
        // For non-multipart requests (URL mode), just continue
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Filter cleanup
    }
}
