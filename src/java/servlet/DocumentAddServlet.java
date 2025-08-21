package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.Course;
import model.Document;
import util.JPAUtil;
import util.saveImageUtil;
import jakarta.servlet.http.HttpSession;
import model.UserAccount;

@WebServlet(name = "DocumentAddServlet", urlPatterns = {"/documents/add"})
@MultipartConfig(
    maxFileSize = 52428800,      // 50MB
    maxRequestSize = 52428800,   // 50MB
    fileSizeThreshold = 0
)
public class DocumentAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			List<Course> courses = em.createNamedQuery("Course.findAll", Course.class).getResultList();
			request.setAttribute("courses", courses);
			request.getRequestDispatcher("/function/document_add.jsp").forward(request, response);
		} finally { em.close(); }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode"); // upload | url
		String courseIdParam = request.getParameter("courseId");
		String title = request.getParameter("title");
		String department = request.getParameter("department");
		String author = request.getParameter("author");
		String yearParam = request.getParameter("publicationYear");
		String description = request.getParameter("description");
		
		// File size validation
		final long MAX_FILE_SIZE = 52428800L; // 50MB
		
		// Handle URL mode differently to avoid multipart limits
		if ("url".equalsIgnoreCase(mode)) {
			// For URL mode, we don't need multipart parsing
			handleUrlMode(request, response, courseIdParam, title, department, author, yearParam, description);
			return;
		}
		
		// For upload mode, continue with multipart handling
		Part coverImageFile = request.getPart("coverImageFile");
		
		if (coverImageFile != null && coverImageFile.getSize() > MAX_FILE_SIZE) {
			request.setAttribute("error", "Ảnh bìa quá lớn! Giới hạn tối đa: 50MB");
			doGet(request, response);
			return;
		}

		// Basic validation
		if ((courseIdParam == null || courseIdParam.isBlank()) && (title == null || title.isBlank())) {
			request.setAttribute("error", "Vui lòng nhập tiêu đề khi tạo mới khoá học.");
			doGet(request, response);
			return;
		}
		
		Part filePart = request.getPart("file");
		if (filePart == null || filePart.getSize() == 0) {
			request.setAttribute("error", "Vui lòng chọn file PDF để tải lên.");
			doGet(request, response);
			return;
		}
		
		// Check PDF file size
		if (filePart.getSize() > MAX_FILE_SIZE) {
			request.setAttribute("error", "File PDF quá lớn! Giới hạn tối đa: 50MB");
			doGet(request, response);
			return;
		}
		
		Integer publicationYear = null;
		if (yearParam != null && !yearParam.isBlank()) {
			try { publicationYear = Integer.valueOf(yearParam); } catch (NumberFormatException ignored) {}
		}

		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Course course = null;
			if (courseIdParam != null && !courseIdParam.isBlank()) {
				course = em.find(Course.class, Integer.valueOf(courseIdParam));
			}
			if (course == null) {
				course = new Course();
				course.setTitle(title);
				course.setDepartment(department);
				course.setAuthor(author);
				course.setPublicationYear(publicationYear);
				course.setDescription(description);
				if (coverImageFile != null && coverImageFile.getSize() > 0) {
					String coverUrl = new saveImageUtil().upload(coverImageFile);
					course.setCoverImage(coverUrl);
				}
				course.setCreatedAt(new Date());
				em.persist(course);
			}
			else {
				if (coverImageFile != null && coverImageFile.getSize() > 0) {
					String coverUrl = new saveImageUtil().upload(coverImageFile);
					course.setCoverImage(coverUrl);
					em.merge(course);
				}
			}

			String filePath;
			String uploadsDir = getServletContext().getRealPath("/uploads");
			File dir = new File(uploadsDir);
			if (!dir.exists()) dir.mkdirs();
			String submitted = Path.of(filePart.getSubmittedFileName()).getFileName().toString();
			Path target = Path.of(uploadsDir, System.currentTimeMillis() + "_" + submitted);
			try (var in = filePart.getInputStream()) {
				Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
			}
			filePath = target.toAbsolutePath().toString();

			Document document = new Document();
			document.setCourseId(course);
			document.setFilePath(filePath);
			document.setUploadedAt(new Date());
			
			// Set owner if user is logged in
			HttpSession session = request.getSession();
			UserAccount currentUser = (UserAccount) session.getAttribute("currentUser");
			if (currentUser != null) {
				document.setOwnerId(currentUser);
			}
			
			em.persist(document);
			tx.commit();
			
			request.setAttribute("success", "Tài liệu đã được thêm thành công!");
			response.sendRedirect(request.getContextPath() + "/catalog");
			
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			request.setAttribute("error", "Lỗi khi thêm tài liệu: " + e.getMessage());
			doGet(request, response);
		} finally {
			em.close();
		}
	}
	
	private void handleUrlMode(HttpServletRequest request, HttpServletResponse response, 
			String courseIdParam, String title, String department, String author, 
			String yearParam, String description) throws ServletException, IOException {
		
		String fileUrl = request.getParameter("fileUrl");
		if (fileUrl == null || fileUrl.isBlank()) {
			request.setAttribute("error", "Vui lòng nhập đường dẫn tuyệt đối hoặc URL của PDF.");
			doGet(request, response);
			return;
		}
		
		// Basic validation
		if ((courseIdParam == null || courseIdParam.isBlank()) && (title == null || title.isBlank())) {
			request.setAttribute("error", "Vui lòng nhập tiêu đề khi tạo mới khoá học.");
			doGet(request, response);
			return;
		}
		
		Integer publicationYear = null;
		if (yearParam != null && !yearParam.isBlank()) {
			try { publicationYear = Integer.valueOf(yearParam); } catch (NumberFormatException ignored) {}
		}

		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Course course = null;
			if (courseIdParam != null && !courseIdParam.isBlank()) {
				course = em.find(Course.class, Integer.valueOf(courseIdParam));
			}
			if (course == null) {
				course = new Course();
				course.setTitle(title);
				course.setDepartment(department);
				course.setAuthor(author);
				course.setPublicationYear(publicationYear);
				course.setDescription(description);
				course.setCreatedAt(new Date());
				em.persist(course);
			}

			Document document = new Document();
			document.setCourseId(course);
			document.setFilePath(fileUrl); // Store the URL directly
			document.setUploadedAt(new Date());
			
			// Set owner if user is logged in
			HttpSession session = request.getSession();
			UserAccount currentUser = (UserAccount) session.getAttribute("currentUser");
			if (currentUser != null) {
				document.setOwnerId(currentUser);
			}
			
			em.persist(document);
			tx.commit();
			
			request.setAttribute("success", "Tài liệu đã được thêm thành công!");
			response.sendRedirect(request.getContextPath() + "/catalog");
			
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			request.setAttribute("error", "Lỗi khi thêm tài liệu: " + e.getMessage());
			doGet(request, response);
		} finally {
			em.close();
		}
	}
}


