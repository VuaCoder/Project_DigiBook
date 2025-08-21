<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Course" %>
<%
    List<Course> courses = (List<Course>) request.getAttribute("courses");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm tài liệu</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/digibook.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/document-add.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">
    <script src="<%=request.getContextPath()%>/js/header.js"></script>
    
 </head>
 <body>
    <jsp:include page="/common/header.jsp"/>
    <div class="docadd-page">
    <h2>Thêm tài liệu</h2>
    <div class="card form-card">
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%=request.getAttribute("error")%></div>
        <% } %>
        <form id="documentForm" action="<%=request.getContextPath()%>/documents/add" method="post">
            <div class="form-grid">
                <div class="form-group">
                    <label>Chọn khoá học (nếu có)</label>
                    <select name="courseId">
                        <option value="">-- Tạo mới khoá học --</option>
                        <% for (Course c: courses) { %>
                            <option value="<%=c.getId()%>"><%=c.getTitle()%></option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label>Tiêu đề <span class="asterisk">*</span></label>
                    <input type="text" name="title" placeholder="Tên giáo trình" required/>
                </div>
                <div class="form-group">
                    <label>Khoa/Ngành <span class="asterisk">*</span></label>
                    <input type="text" name="department" required/>
                </div>
                <div class="form-group">
                    <label>Tác giả <span class="asterisk">*</span></label>
                    <input type="text" name="author" required/>
                </div>
                <div class="form-group">
                    <label>Năm xuất bản <span class="asterisk">*</span></label>
                    <input type="number" name="publicationYear" min="0" required/>
                </div>
                <div class="form-group">
                    <label>Ảnh bìa</label>
                    <input type="file" name="coverImageFile" accept="image/*" id="coverImageFile"/>
                    <small class="file-info">Chỉ cần thiết khi upload file PDF</small>
                </div>
            </div>

            <div class="form-group full">
                <label>Mô tả</label>
                <textarea name="description" rows="3"></textarea>
            </div>

            <hr/>

            <div class="form-group full">
                <label>Chọn cách thêm file PDF <span class="asterisk">*</span></label>
                <div class="radio-group">
                    <label><input type="radio" name="mode" value="upload" checked/> Tải từ máy</label>
                    <label><input type="radio" name="mode" value="url"/> Từ đường dẫn tuyệt đối/URL</label>
                </div>
            </div>

            <div id="uploadPanel" class="form-group full">
                <label>Chọn file PDF <span class="asterisk">*</span></label>
                <input type="file" name="file" accept="application/pdf" id="pdfFile"/>
                <small class="file-info">Giới hạn: Tối đa 50MB</small>
            </div>

            <div id="urlPanel" class="form-group full" style="display:none">
                <label>Đường dẫn file <span class="asterisk">*</span></label>
                <input type="text" name="fileUrl" placeholder="D:\\pdfs\\book.pdf hoặc https://site/book.pdf"/>
                <small class="file-info">Hỗ trợ đường dẫn tuyệt đối hoặc URL</small>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn">Lưu</button>
            </div>
        </form>
        <div class="muted">Sau khi lưu sẽ chuyển sang trang đọc PDF.</div>
    </div>
</div>

    <jsp:include page="/common/footer.jsp"/>
    <script>
        const uploadPanel = document.getElementById('uploadPanel');
        const urlPanel = document.getElementById('urlPanel');
        const maxFileSize = 52428800; // 50MB in bytes
        const form = document.getElementById('documentForm');
        
        // File size validation
        function validateFileSize(input) {
            const file = input.files[0];
            if (file && file.size > maxFileSize) {
                const sizeMB = (file.size / (1024 * 1024)).toFixed(1);
                alert(`File quá lớn! Kích thước hiện tại: ${sizeMB}MB. Giới hạn tối đa: 50MB.`);
                input.value = '';
                return false;
            }
            return true;
        }
        
        // Add file size validation to file inputs
        document.getElementById('pdfFile').addEventListener('change', function() {
            validateFileSize(this);
        });
        
        document.getElementById('coverImageFile').addEventListener('change', function() {
            validateFileSize(this);
        });
        
        // Radio button toggle
        document.querySelectorAll('input[name="mode"]').forEach(r => {
            r.addEventListener('change', () => {
                if (r.value === 'upload') { 
                    uploadPanel.style.display = ''; 
                    urlPanel.style.display = 'none';
                    // Set multipart encoding for file uploads
                    form.setAttribute('enctype', 'multipart/form-data');
                }
                else { 
                    uploadPanel.style.display = 'none'; 
                    urlPanel.style.display = '';
                    // Remove multipart encoding for URL mode
                    form.removeAttribute('enctype');
                }
            });
        });
        
        // Form validation and submission
        form.addEventListener('submit', function(e) {
            const fileInput = document.getElementById('pdfFile');
            const urlInput = document.querySelector('input[name="fileUrl"]');
            const mode = document.querySelector('input[name="mode"]:checked').value;
            
            if (mode === 'upload') {
                if (!fileInput.files[0] || fileInput.files[0].size === 0) {
                    e.preventDefault();
                    alert('Vui lòng chọn file PDF để upload!');
                    return false;
                }
                // Ensure multipart encoding for file uploads
                form.setAttribute('enctype', 'multipart/form-data');
            } else {
                if (!urlInput.value || urlInput.value.trim() === '') {
                    e.preventDefault();
                    alert('Vui lòng nhập đường dẫn file PDF!');
                    return false;
                }
                // Remove multipart encoding for URL mode
                form.removeAttribute('enctype');
            }
        });
        
        // Initialize form encoding on page load
        document.addEventListener('DOMContentLoaded', function() {
            const mode = document.querySelector('input[name="mode"]:checked').value;
            if (mode === 'upload') {
                form.setAttribute('enctype', 'multipart/form-data');
            } else {
                form.removeAttribute('enctype');
            }
        });
    </script>
 </body>
 </html>


