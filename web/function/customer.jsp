<%-- 
    Document   : customer
    Created on : Aug 20, 2025, 12:01:06 PM
    Author     : HOANG PHUC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">   
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Khách hàng - DigiBook</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap"
              rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">
    </head>
    <body>
        <h2>KHÁCH HÀNG CỦA CHÚNG TÔI</h2>

        <div class="customer-grid">
            <div class="customer-item" data-name="Đại học Kinh tế Huế" data-info="Thông tin chi tiết A">
                <img src="${pageContext.request.contextPath}/img/daihockthue.jpg" alt="Customer 1">
            </div>
            <div class="customer-item" data-name="Đại học Bách khoa Đà Nẵng" data-info="Thông tin chi tiết B">
                <img src="${pageContext.request.contextPath}/img/bkdn.jpg" alt="Customer 2">
            </div>
            <div class="customer-item" data-name="Đại học Phú Xuân" data-info="Thông tin chi tiết C">
                <img src="${pageContext.request.contextPath}/img/daihocphuxuan.jpg" alt="Customer 3" style="border-radius: 50%">
            </div>
            <div class="customer-item" data-name="Đại học Luật Huế" data-info="Thông tin chi tiết C">
                <img src="${pageContext.request.contextPath}/img/daihocluathue.png" alt="Customer 4">
            </div>
            <div class="customer-item" data-name="Đại học Ngoại ngữ Đà Nẵng" data-info="Thông tin chi tiết C">
                <img src="${pageContext.request.contextPath}/img/daihocnndn.jpg" alt="Customer 5">
            </div>
            <div class="customer-item" data-name="Đại học Phan Châu Trinh" data-info="Thông tin chi tiết C">
                <img src="${pageContext.request.contextPath}/img/daihocpct.png" alt="Customer 6" style="transform: scale(1.05);">
            </div>
            <div class="customer-item" data-name="Đại học Y Đà Nẵng" data-info="Thông tin chi tiết C">
                <img src="${pageContext.request.contextPath}/img/daihocydn.jpg" alt="Customer 7">
            </div>
            <div class="customer-item" data-name="Đại học Y Huế" data-info="Thông tin chi tiết C">
                <img src="${pageContext.request.contextPath}/img/daihocyhue.png" alt="Customer 8">
            </div>
            <div class="customer-item" data-name="Đại học Y thành phố Hồ Chí Minh" data-info="Thông tin chi tiết C">
                <img src="${pageContext.request.contextPath}/img/daihocyskpt.png" alt="Customer 9">
            </div>
            <div class="customer-item" data-name="Đại học Kinh tế Đà Nẵng" data-info="Thông tin chi tiết C">
                <img src="${pageContext.request.contextPath}/img/due.png" alt="Customer 10" style="transform: scale(1.5);border-radius: 50%">
            </div>

            <!-- Modal -->
            <div class="modal" id="customerModal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <h3 id="modalName"></h3>
                    <p id="modalInfo"></p>
                </div>
            </div>
        </div>
            <div class="goback">
                <a href="${pageContext.request.contextPath}/home">← Về trang chủ</a></div>
            <script src="js/customer.js"></script>
    </body>
</html>
