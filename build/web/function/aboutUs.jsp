<%-- 
    Document   : aboutUs
    Created on : Aug 19, 2025, 9:52:55 PM
    Author     : HOANG PHUC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>DigiBook</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aboutUs.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap"
              rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">
        <script src="${pageContext.request.contextPath}/js/header.js"></script>
    </head>

    <body>
        <jsp:include page="/common/header.jsp"/>
        <div class="aboutus-container">
            <h1 class="aboutus-title">ĐỘI NGŨ SÁNG LẬP</h1>
            <div class="founder-team">
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/vantai.png" alt="Nguyễn Văn Tài" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">Founder & CEO</h3>
                        <div class="founder-name-big">Nguyễn Văn Tài</div>
                        <div class="founder-detail-big">
                            Giải Nhì toàn Quốc Viet Future Awards 2024<br>
                            Giải Nhất cuộc thi Đấu trường Kinh doanh - NEXTRADE 2025<br>
                            Top 05 Cuộc thi Khởi nghiệp Công nghệ trong sinh viên lần thứ 5
                        </div>
                    </div>
                </div>
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/maianh.png" alt="Nguyễn Ngọc Mai Anh" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">CFO</h3>
                        <div class="founder-name-big">Nguyễn Ngọc Mai Anh</div>
                        <div class="founder-detail-big">
                            Đồng tác giả tham luận tại Hội thảo Quốc tế COMBELT 2025<br>
                            Học bổng khuyến khích học tập ĐH Kinh tế - ĐHĐN liên tiếp 3 học kì 2022-2025<br>
                            Giải Ba Hội thi sinh viên Nghiên cứu khoa học cấp Khoa Quản trị Kinh doanh năm học 2024-2025
                        </div>
                    </div>
                </div>
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/dieule.png" alt="Lê Thị Mỹ Diệu" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">Marketing & Sales</h3>
                        <div class="founder-name-big">Lê Thị Mỹ Diệu</div>
                        <div class="founder-detail-big">
                            Đồng tác giả tham luận tại Hội thảo Quốc tế COMBELT 2025<br>
                            Học bổng khuyến khích học tập ĐH Kinh tế - ĐHĐN học kỳ II năm 2022-2023, học kỳ I năm
                            2023-2024<br>
                            Giải Ba Hội thi sinh viên Nghiên cứu khoa học cấp Khoa Quản trị Kinh doanh năm học 2024-2025
                        </div>
                    </div>
                </div>
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/hongthanh.png" alt="Nguyễn Thị Hồng Thanh" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">Nhân sự</h3>
                        <div class="founder-name-big">Nguyễn Thị Hồng Thanh</div>
                        <div class="founder-detail-big">
                            Danh hiệu Thanh niên tiên tiến làm theo lời Bác cấp trường năm 2025<br>
                            “Sinh viên 5 tốt” cấp Trường năm học 2022-2023
                        </div>
                    </div>
                </div>
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/ngocdung.png" alt="Nguyễn Thị Ngọc Dung" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">Content Development</h3>
                        <div class="founder-name-big">Nguyễn Thị Ngọc Dung</div>
                        <div class="founder-detail-big">
                            Danh hiệu Thanh niên tiên tiến làm theo lời Bác cấp trường năm 2025<br>
                            “Sinh viên 5 tốt” cấp Trường năm học 2022-2023
                        </div>
                    </div>
                </div>

            </div>
            <h1 class="aboutus-title">TẦM NHÌN</h1>
            <p class="aboutus-desc">
                “Trở thành nền tảng chuyển đổi số hàng đầu dành cho các Trường Đại học và Cao đẳng tại Việt Nam, kiến tạo hệ
                sinh thái học tập thông minh, hiện đại và bền vững, góp phần đưa giáo dục Việt Nam tiệm cận chuẩn mực quốc
                tế.”
            </p>

            <h1 class="aboutus-title">SỨ MỆNH</h1>
            <p class="aboutus-desc">
                “Chúng tôi mang đến giải pháp số hóa toàn diện cho giáo dục đại học, giúp sinh viên dễ dàng tiếp cận giáo
                trình và tài liệu học tập mọi lúc, mọi nơi thông qua nền tảng ứng dụng hiện đại. Bằng việc tích hợp công
                nghệ AI để hỗ trợ tra cứu, ghi chú, ôn tập và cá nhân hóa hành trình học tập, chúng tôi không chỉ tối ưu chi
                phí và nguồn lực cho nhà trường, giảm thiểu gánh nặng in ấn, mà còn góp phần xây dựng một môi trường học tập
                xanh, hiệu quả và bền vững, đồng hành cùng các Trường Đại học, Cao đẳng trên con đường chuyển đổi số giáo
                dục.”
            </p>

            <h1 class="aboutus-title">GIÁ TRỊ CỐT LÕI</h1>
            <div class="founder-team">
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/maianh.png" alt="Nguyễn Ngọc Mai Anh" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">Đổi mới</h3>
                        <div class="founder-detail-big">
                            Không ngừng ứng dụng công nghệ tiên tiến để nâng cao trải nghiệm
                            học tập.
                        </div>
                    </div>
                </div>
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/maianh.png" alt="Nguyễn Ngọc Mai Anh" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">Cá nhân hóa</h3>
                        <div class="founder-detail-big">
                            Đặt sinh viên làm trung tâm, tạo ra lộ trình học phù hợp
                            cho từng người.
                        </div>
                    </div>
                </div>
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/maianh.png" alt="Nguyễn Ngọc Mai Anh" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">Hiệu quả</h3>
                        <div class="founder-detail-big">
                            Tối ưu hóa chi phí, thời gian và nguồn lực cho cả nhà trường và
                            sinh viên.
                        </div>
                    </div>
                </div>
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/maianh.png" alt="Nguyễn Ngọc Mai Anh" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">Bền vững</h3>
                        <div class="founder-detail-big">
                            Góp phần giảm thiểu in ấn, xây dựng môi trường giáo dục
                            xanh.
                        </div>
                    </div>
                </div>
                <div class="founder-card big">
                    <img src="${pageContext.request.contextPath}/img/maianh.png" alt="Nguyễn Ngọc Mai Anh" class="founder-img-big">
                    <div class="founder-info-big">
                        <h3 class="founder-role-big">Đồng hành</h3>
                        <div class="founder-detail-big">
                            Hợp tác chặt chẽ với các Trường Đại học, Cao đẳng để tạo ra
                            giá trị thực tiễn.
                        </div>
                    </div>
                </div>
            </div>


        </div>
                    <jsp:include page="/common/footer.jsp"/>
    </body>

</html>
