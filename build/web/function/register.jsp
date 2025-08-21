<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng ký - DigiBook</title>
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">
        
    </head>
    <body>
        <div class="auth-wrapper">
            <div class="auth-card">
                <div class="brand">
                    <img src="${pageContext.request.contextPath}/img/LOGO.png" alt="DigiBook"/>
                    <h1>Tạo tài khoản</h1>
                    <p class="subtitle">Tham gia cộng đồng học liệu số</p>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/register" novalidate>
                    <div class="form-group">
                        <label for="username">Tên đăng nhập <span class="required-asterisk">*</span></label>
                        <input type="text" id="username" name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email <span class="required-asterisk">*</span></label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Mật khẩu <span class="required-asterisk">*</span></label>
                        <div class="password-input-wrapper">
                            <input type="password" id="password" name="password" required>
                            <button type="button" class="password-toggle" onclick="togglePassword('password')">
                                <svg class="eye-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                                    <circle cx="12" cy="12" r="3"/>
                                </svg>
                                <svg class="eye-off-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="display: none;">
                                    <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/>
                                    <line x1="1" y1="1" x2="23" y2="23"/>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="confirm">Xác nhận mật khẩu <span class="required-asterisk">*</span></label>
                        <div class="password-input-wrapper">
                            <input type="password" id="confirm" name="confirm" required>
                            <button type="button" class="password-toggle" onclick="togglePassword('confirm')">
                                <svg class="eye-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                                    <circle cx="12" cy="12" r="3"/>
                                </svg>
                                <svg class="eye-off-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="display: none;">
                                    <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/>
                                    <line x1="1" y1="1" x2="23" y2="23"/>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div class="form-row">
                        <span></span>
                        <a class="link" href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập</a>
                    </div>
                    <div class="error">${requestScope.error}</div>
                    <button type="submit" class="btn-primary">Tạo tài khoản</button>
                    <div class="goback">
                        <a class="btn-secondary btn-block" href="${pageContext.request.contextPath}/home">
                            ← Về trang chủ
                        </a>
                    </div>
                </form>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/js/auth.js"></script>
    </body>
</html>

