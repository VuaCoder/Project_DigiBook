<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Course" %>
<%
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    List<String> departments = (List<String>) request.getAttribute("departments");
    List<String> authors = (List<String>) request.getAttribute("authors");
    List<Integer> years = (List<Integer>) request.getAttribute("years");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>DigiBook - Tra cứu giáo trình</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/digibook.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/catalog.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap"
              rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">
        <script src="${pageContext.request.contextPath}/js/header.js"></script>
    
 </head>
 <body>
    <jsp:include page="/common/header.jsp"/>
    <div class="catalog-header">
        <h2>Tổng hợp giáo trình</h2>
    </div>
    <div class="catalog-page">
        <div class="filters">
            <form method="get" action="<%=request.getContextPath()%>/catalog">
                <select name="department">
                    <option value="">Khoa/Ngành</option>
                    <% for (String d : departments) { %>
                        <option value="<%=d%>" <%= d.equals(request.getParameter("department"))?"selected":"" %>><%=d%></option>
                    <% } %>
                </select>
                <select name="author">
                    <option value="">Tác giả</option>
                    <% for (String a : authors) { %>
                        <option value="<%=a%>" <%= a.equals(request.getParameter("author"))?"selected":"" %>><%=a%></option>
                    <% } %>
                </select>
                <select name="year">
                    <option value="">Năm xuất bản</option>
                    <% for (Integer y : years) { %>
                        <option value="<%=y%>" <%= String.valueOf(y).equals(request.getParameter("year"))?"selected":"" %>><%=y%></option>
                    <% } %>
                </select>
                <button type="submit" class="btn-footer">Lọc</button>
            </form>
        </div>
        <div class="catalog-grid">
            <% for (Course c : courses) { %>
            <div class="catalog-card">
                <img src="<%= (c.getCoverImage()!=null && !c.getCoverImage().isEmpty())? c.getCoverImage() : (request.getContextPath()+"/img/LOGO.png") %>" alt="cover"/>
                <div class="meta">
                    <div class="title"><%=c.getTitle()%></div>
                    <div class="sub">Khoa: <%=c.getDepartment()==null?"-":c.getDepartment()%></div>
                    <div class="sub">Tác giả: <%=c.getAuthor()==null?"-":c.getAuthor()%></div>
                    <div class="sub">Năm: <%=c.getPublicationYear()==null?"-":c.getPublicationYear()%></div>
                    <div><a class="btn-footer" href="<%=request.getContextPath()%>/course?id=<%=c.getId()%>">Xem chi tiết</a></div>
                </div>
            </div>
            <% } %>
        </div>
    </div>
    <jsp:include page="/common/footer.jsp"/>
 </body>
 </html>


