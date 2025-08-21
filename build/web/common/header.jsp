
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">
<script src="${pageContext.request.contextPath}/js/auth.js"></script>
<script src="${pageContext.request.contextPath}/js/header.js"></script>

<div class="header">
	<div class="logo">
		<a href="${pageContext.request.contextPath}/home">
			<img src="${pageContext.request.contextPath}/img/LOGO.png" alt="logo-digibook">
		</a>
	</div>
	<button class="hamburger" id="hamburger-btn">
		<span></span>
		<span></span>
		<span></span>
	</button>
	<div class="nav-menu" id="nav-menu">
		<ul>
			<li><a href="${pageContext.request.contextPath}/aboutUs">Chúng Tôi</a></li>
			<li><a href="${pageContext.request.contextPath}/catalog">Tra Cứu</a></li>
			<li><a href="${pageContext.request.contextPath}/notes">Ghi Chú</a></li>
			<li><a href="#">Ôn Tập</a></li>
			<li><a href="${pageContext.request.contextPath}/library">Lưu Trữ</a></li>
			<%
				model.UserAccount currentUser = (model.UserAccount) session.getAttribute("currentUser");
				if (currentUser == null) {
			%>
				<li><a href="${pageContext.request.contextPath}/login">Đăng Nhập</a></li>
			<%
				} else {
			%>
				<li class="greeting" style="margin-right:8px; font-size: 20px;">Xin chào, <%= currentUser.getUsername() %></li>
				<li>
					<a class="logout-link" href="${pageContext.request.contextPath}/logout" onclick="clearRememberMeCookie(); return confirm('Bạn có chắc muốn đăng xuất?')" style="text-decoration:none; font-size: 20px;">Đăng xuất</a>
				</li>
			<%
				}
			%>
		</ul>
	</div>
</div>
