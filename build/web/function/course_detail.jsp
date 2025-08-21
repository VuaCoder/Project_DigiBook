<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Course, java.util.List, model.Document" %>
<%
    Course course = (Course) request.getAttribute("course");
    List<Document> documents = (List<Document>) request.getAttribute("documents");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%=course != null ? (course.getDepartment()==null?"Giáo trình":course.getDepartment()) : "Giáo trình"%></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/digibook.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/course-detail.css">
    <link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/img/favi.png">
    <script src="<%=request.getContextPath()%>/js/header.js"></script>
</head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="course-hero">
    <div class="cover">
        <img src="<%= (course.getCoverImage()!=null && !course.getCoverImage().isEmpty())? course.getCoverImage(): (request.getContextPath()+"/img/LOGO.png") %>" alt="cover"/>
    </div>
    <div class="meta">
        <div class="label">Chương trình đào tạo</div>
        <h1 class="title"><%=course.getDepartment()==null?"Giáo trình":course.getDepartment()%></h1>
        <div class="sub">Trường/Khoa: <%=course.getDepartment()==null?"-":course.getDepartment()%></div>
        <div class="sub">Tác giả: <%=course.getAuthor()==null?"-":course.getAuthor()%></div>
        <a class="btn-primary" href="<%=request.getContextPath()%>/documents/view?id=<%=(documents!=null && !documents.isEmpty())? documents.get(0).getId() : 0 %>" >Đọc trên DigiBook</a>
    </div>
 </div>

 <div class="course-content">
     <h2>Giới thiệu chung</h2>
     <p><%=course.getDescription()==null?"":course.getDescription()%></p>
     <h2>Tài liệu thuộc giáo trình</h2>
     <ul class="doc-list">
         <% for (Document d : documents) { %>
             <li>
                 <span><%= d.getCourseId()!=null?d.getTitle() : ("Tài liệu #"+d.getId()) %></span>
                 <a class="btn-secondary" href="<%=request.getContextPath()%>/documents/view?id=<%=d.getId()%>">Đọc</a>
             </li>
         <% } %>
     </ul>
 </div>

<jsp:include page="/common/footer.jsp"/>
</body>
</html>


