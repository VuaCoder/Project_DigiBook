<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.Document" %>
<%
    List<Document> documents = (List<Document>) request.getAttribute("documents");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kho lưu trữ của tôi</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/digibook.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/catalog.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap"
              rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">
    <script src="<%=request.getContextPath()%>/js/header.js"></script>
</head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="catalog-page library-page">
    <h2>Kho lưu trữ</h2>
    <div class="library-actions">
        <a class="btn-footer add-doc-btn" href="<%=request.getContextPath()%>/documents/add">Thêm giáo trình</a>
    </div>
    <div class="catalog-grid">
        <% for (Document d : documents) { %>
        <div class="catalog-card">
            <img src="<%= (d.getCourseId()!=null && d.getCourseId().getCoverImage()!=null)? d.getCourseId().getCoverImage() : (request.getContextPath()+"/img/LOGO.png") %>" alt="cover"/>
            <div class="meta">
                <div class="title"><%= d.getCourseId()!=null? d.getCourseId().getTitle() : ("Tài liệu #"+d.getId()) %></div>
                <div class="sub">ID: <%=d.getId()%></div>
                <div style="margin-top:8px;display:flex;gap:8px">
                    <a class="btn-footer" href="<%=request.getContextPath()%>/documents/view?id=<%=d.getId()%>">Đọc</a>
                    <form method="post" onsubmit="return confirm('Xoá tài liệu này?')">
                        <input type="hidden" name="deleteId" value="<%=d.getId()%>">
                        <button class="btn-footer" type="submit">Xoá</button>
                    </form>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>


