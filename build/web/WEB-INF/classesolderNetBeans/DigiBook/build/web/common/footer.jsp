<%-- 
    Document   : footer
    Created on : Aug 19, 2025, 3:47:29 PM
    Author     : HOANG PHUC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap"
              rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">

    </head>
    <body>
        <footer class="footer">
            <div class="footer__container">
                <!-- Thông tin doanh nghiệp -->
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3834.254101092309!2d108.2374396!3d16.052298599999997!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x314219d7e3ece045%3A0xe4722b6e57b4e230!2zMjIgTmfFqSBIw6BuaCBTxqFuLCBC4bqvYyBN4bu5IFBow7osIE5nxakgSMOgbmggU8ahbiwgxJDDoCBO4bq1bmcgNTUwMDAw!5e0!3m2!1svi!2s!4v1755664358580!5m2!1svi!2s" width="500" height="350" style="display:flex;border:0; align-content: center;  margin-right: 40px;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                <div class="footer__col">
                    <h3 class="footer__heading">CÔNG TY CỔ PHẦN TƯ VẤN VÀ CUNG CẤP GIẢI PHÁP CHUYỂN ĐỔI SỐ DIGIBOOK</h3>
                    <ul class="footer__list">
                        <li><i class="fa-solid fa-location-dot"></i><a href="https://maps.app.goo.gl/2qjqgrdRMEUdPdh56">22 Ngũ Hành Sơn, Đà Nẵng</a> </li>
                        <li>
                            <i class="fa-solid fa-phone"></i>
                            <a href="tel:+84932424706">(84+) 932 424 706</a>
                        </li>
                        <li>
                            <i class="fa-solid fa-envelope"></i>
                            <a href="mailto:digibook.dn22@gmail.com">digibook.dn22@gmail.com</a>
                        </li>
                    </ul>
                    
                </div>

                    
            </div>

            <div class="footer__bottom">
                <img src="${pageContext.request.contextPath}/img/due.jpg">DUE BOOK | BẢN QUYỀN NỘI DUNG GIÁO TRÌNH ĐIỆN TỬ THUỘC VỀ TRƯỜNG ĐẠI HỌC KINH TẾ - ĐẠI HỌC ĐÀ NẴNG
            </div>
        </footer>
    </body>
</html>
