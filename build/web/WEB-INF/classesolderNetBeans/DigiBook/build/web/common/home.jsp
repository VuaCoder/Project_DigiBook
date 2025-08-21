<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">   
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>DigiBook</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/digibook.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap"
              rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">
    </head>

    <body>
        <jsp:include page="/common/header.jsp"></jsp:include>
            <div class="background-blurred">
                <img src="${pageContext.request.contextPath}/img/background1.jpg" alt="background1" class="active">
            <img src="${pageContext.request.contextPath}/img/background2.jpg" alt="background2">
            <img src="${pageContext.request.contextPath}/img/background3.jpg" alt="background3">
            <img src="${pageContext.request.contextPath}/img/background4.jpg" alt="background4">
            <img src="${pageContext.request.contextPath}/img/background5.jpg" alt="background5">
        </div>
        <div class="digi-section">
            <div class="digi-headline">
                DIGIBOOK
                <span class="digi-badge">Mới</span>
            </div>
            <div class="digi-slogan">
                Công nghệ dẫn lối - Tri thức mở đường
            </div>
            <a href="#library" class="digi-button">Tìm hiểu tủ sách</a>
        </div>
        <div class="bookshelf-container" id="library">
            <div id="bookshelf" class="bookshelf-section">
                <h2 class="bookshelf-title">Tủ sách</h2>
                <p class="bookshelf-desc">Có <strong>${fn:length(recentDocuments)}</strong> tài liệu mới cập nhật.</p>

            </div>
        </div>

        <div class="digi-frames">
            <c:choose>
                <c:when test="${not empty recentDocuments}">
                    <c:forEach var="doc" items="${recentDocuments}">
                        <div class="digi-frame blue">
                            <div class="digi-frame-cover">
                                <c:choose>
                                    <c:when test="${doc.courseId != null && doc.courseId.coverImage != null && fn:length(doc.courseId.coverImage) > 0}">
                                        <img src="${doc.courseId.coverImage}" alt="cover" class="frame-cover-img"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/img/LOGO.png" alt="default" class="frame-cover-img"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="digi-frame-label">Tài liệu mới</div>
                            <div class="digi-frame-title">${doc.courseId != null ? doc.courseId.title : 'Tài liệu'}</div>
                            <div class="digi-frame-desc">
                                Tác giả: ${doc.courseId != null && doc.courseId.author != null ? doc.courseId.author : '-'}<br>
                                Năm xuất bản: ${doc.courseId != null && doc.courseId.publicationYear != null ? doc.courseId.publicationYear : '-'}
                            </div>
                            <div style="margin-top:8px"><a class="btn-footer" href="${pageContext.request.contextPath}/documents/view?id=${doc.id}">Đọc ngay</a></div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="digi-frame teal">
                        <div class="digi-frame-cover">
                            <img src="${pageContext.request.contextPath}/img/LOGO.png" alt="default" class="frame-cover-img"/>
                        </div>
                        <div class="digi-frame-label">Chưa có tài liệu</div>
                        <div class="digi-frame-title">Hãy bắt đầu</div>
                        <div class="digi-frame-desc">Bạn có thể thêm tài liệu mới từ trang Tra Cứu → Thêm giáo trình.</div>
                        <div style="margin-top:8px"><a class="btn-footer" href="${pageContext.request.contextPath}/documents/add">Thêm giáo trình</a></div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
                <div><a class="btn-footer see-more" href="${pageContext.request.contextPath}/catalog">Xem thêm</a></div>
        <!-- Expert Reviews -->
        <div class="expert-section">
            <h2 class="expert-title">ĐÁNH GIÁ TỪ CHUYÊN GIA</h2>
            <p class="expert-subtitle">Những chia sẻ và nhận xét khách quan từ các chuyên gia trong ngành</p>

            <!-- Slider Container -->
            <div class="expert-slider" style="top: -70px;">
                <div class="expert-card active">
                    <div class="avatar-wrapper"><img src="${pageContext.request.contextPath}/img/danhgia1.png" alt="Expert 1" class="expert-avatar"></div>
                    <div class="expert-info">
                        <p class="expert-review">
                            “Dự án <strong>giáo trình điện tử thông minh</strong> có tiềm năng tạo ra <strong>bước chuyển
                                mới</strong>
                            trong cách tiếp cận <strong>tri thức</strong> của <strong>sinh viên</strong> các <strong>ngành
                                kinh tế</strong>. Nếu trước
                            đây sinh viên chủ yếu tiếp cận giáo trình truyền thống nặng tính lý thuyết, thì mô hình mới này
                            có thể tích hợp dữ liệu thời gian thực, <strong>tình huống kinh tế điển hình</strong>, và các
                            <strong>case study</strong> cập nhật từ thị trường. Điều đó không chỉ giúp người học nắm vững
                            kiến thức cơ bản mà còn phát triển <strong>tư duy phân tích, phản biện</strong> và <strong>ra
                                quyết
                                định</strong> - những kỹ năng quan trọng của một nhà quản trị."
                        </p>
                        <p class="expert-name"><strong>- Thầy Hoàng Hà -</strong></p>
                        <p class="expert-role"><strong>Giảng viên:</strong> Khoa Quản trị Kinh doanh, Đại học Kinh tế Đà
                            Nẵng <br>(Trích từ buổi chia sẻ tại vòng triển lãm học phần Khởi nghiệp số)
                        </p>
                    </div>
                </div>
                <div class="expert-card">
                    <div class="avatar-wrapper"><img src="${pageContext.request.contextPath}/img/danhgia2.png" alt="Expert 1" class="expert-avatar"></div>
                    <div class="expert-info">
                        <p class="expert-review">
                            "Điểm mạnh của <strong>dự án</strong> nằm ở khả năng
                            <strong>số hóa học liệu</strong>, nhưng điều quan trọng hơn là xây dựng được một
                            <strong>hệ sinh thái học tập</strong> thông minh. Với sinh viên kinh tế,
                            <strong>giáo trình điện tử</strong> không chỉ là nơi <strong>đọc</strong> – mà phải là
                            <strong>môi trường tương tác</strong>: <strong>thảo luận nhóm, đặt câu hỏi cho giảng
                                viên</strong>, và
                            <strong>tham gia tình huống mô phỏng</strong>. Nếu phát triển theo hướng này, <strong>sản
                                phẩm</strong> sẽ góp phần
                            <strong>rút ngắn khoảng cách</strong> giữa <strong>kiến thức học thuật</strong> và
                            <strong>thực tiễn thị trường lao động</strong>."
                        </p>
                        <p class="expert-name"><strong>- Thầy Nguyễn Bảo Phương -</strong></p>
                        <p class="expert-role"><strong>Giảng viên:</strong> Khoa Quản trị Kinh doanh, Đại học Kinh tế Đà
                            Nẵng <br> (Trích từ buổi chia sẻ tại vòng triển lãm học phần Khởi nghiệp số)</p>
                    </div>
                </div>



                <div class="expert-card">
                    <div class="avatar-wrapper"><img src="${pageContext.request.contextPath}/img/danhgia3.png" alt="Expert 1" class="expert-avatar"></div>
                    <div class="expert-info">
                        <p class="expert-review">
                            "Điểm nổi bật của <strong>dự án giáo trình điện tử thông minh</strong> chính là khả năng
                            <strong>ứng dụng công nghệ để
                                tối ưu hóa quá trình dạy và học</strong>. Từ <strong>góc độ kỹ thuật</strong>, việc xây dựng
                            một <strong>nền tảng giáo trình số</strong>
                            không chỉ dừng lại ở việc <strong>số hóa tài liệu</strong>, mà còn cần chú trọng đến
                            <strong>kiến trúc hệ thống, khả năng
                                mở rộng</strong>và <strong>tính bảo mật dữ liệu</strong>. Nếu được phát triển với
                            <strong>nền tảng hạ tầng vững chắc, dự án</strong> hoàn
                            toàn có thể trở thành <strong>công cụ mang tính đột phá</strong> trong việc <strong>cung cấp tri
                                thức</strong> và <strong>thúc đẩy chuyển
                                đổi số trong giáo dục</strong>."
                        </p>
                        <p class="expert-name"><strong>- Cô Đỗ Hoàng Ngân Mi -</strong></p>
                        <p class="expert-role"><strong>Giảng viên:</strong> Khoa Công nghệ Kỹ thuật Điều khiển và Tự động
                            hóa, Đại học Sư phạm Kỹ thuật Đà Nẵng <br> (Trích từ buổi chia sẻ định hướng từ Founder và Cô)
                        </p>
                    </div>
                </div>
                <div class="expert-card">
                    <div class="avatar-wrapper"><img src="${pageContext.request.contextPath}/img/danhgia4.png" alt="Expert 1" class="expert-avatar"></div>
                    <div class="expert-info">
                        <p class="expert-review">
                            "Điều tôi đánh giá cao ở <strong>dự án giáo trình điện tử thông minh</strong> là <strong>tinh thần đổi mới</strong> -
                            không chỉ dừng lại ở việc <strong>đưa tài liệu lên nền tảng số</strong>, mà còn hướng đến xây
                            dựng một <strong>hệ sinh thái học tập tương tác</strong> và <strong>bền vững</strong>. Từ
                            <strong>góc nhìn khởi nghiệp đổi mới sáng tạo</strong>, một <strong>sản phẩm</strong> có giá trị
                            thật sự khi nó vừa giải quyết được <strong>“pain point” của người học</strong>, vừa tạo ra
                            <strong>mô hình kinh doanh khả thi</strong> để <strong>mở rộng quy mô</strong>. Với <strong>tốc
                                độ chuyển đổi số trong giáo dục</strong> hiện nay, <strong>dự án</strong> hoàn toàn có cơ
                            hội trở thành một <strong>nền tảng EdTech</strong> có <strong>sức cạnh tranh quốc tế</strong>."
                        </p>
                        <p class="expert-name"><strong>- Anh Phạm Đức Linh -</strong></p>
                        <p class="expert-role"><strong></strong>Founder & CEO Ecomdy <br> (Trích từ buổi chia sẻ tại vòng
                            triển lãm học phần Khởi nghiệp số)
                        </p>
                    </div>
                </div>

                <div class="expert-card">
                    <div class="avatar-wrapper"><img src="${pageContext.request.contextPath}/img/danhgia5.png" alt="Expert 1" class="expert-avatar"></div>
                    <div class="expert-info">
                        <p class="expert-review">
                            <strong>Giáo trình điện tử thông minh</strong> không chỉ là một <strong>sản phẩm
                                EdTech</strong>, mà còn là <strong>nền tảng mang tính chiến lược</strong> trong lộ trình
                            <strong>chuyển đổi số giáo dục</strong>. Về mặt <strong>công nghệ</strong>, <strong>dự
                                án</strong> cần định hình theo hướng <strong>modular platform</strong> – tức là <strong>nền
                                tảng</strong> có khả năng <strong>tích hợp</strong> và <strong>mở rộng</strong> dễ dàng với
                            các công cụ khác như <strong>AI</strong>, <strong>dữ liệu lớn (Big Data)</strong>, hay
                            <strong>blockchain</strong> trong <strong>quản lý học liệu</strong>. Ở <strong>góc nhìn kinh
                                tế</strong>, <strong>sản phẩm</strong> này nếu được triển khai đúng hướng sẽ góp phần
                            <strong>nâng cao năng suất học tập</strong>, <strong>giảm chi phí in ấn – phân phối truyền
                                thống</strong>, đồng thời tạo ra <strong>mô hình kinh tế tri thức mới</strong>, nơi
                            <strong>tri thức</strong> được <strong>lan tỏa rộng rãi</strong> và có thể <strong>thương mại
                                hóa bền vững</strong>.
                        </p>
                        <p class="expert-name"><strong>- Anh Danny Ngô -</strong></p>
                        <p class="expert-role"><strong>CTO - Digital Transformation Innovation Economy Advisor</strong> <br>
                            (Trích từ buổi chia sẻ định hướng trong khuôn khổ Dự án tham gia VietFuture Awards 2025)
                        </p>
                    </div>
                </div>
                <div class="expert-dots"></div>
            </div>
        </div>
        <div class="contact-cta"><a href="${pageContext.request.contextPath}/customer" class="btn-footer">KẾT NỐI VỚI CHÚNG TÔI</a></div>

        <jsp:include page="/common/footer.jsp"></jsp:include>
        <script src="${pageContext.request.contextPath}/js/digibook_home.js"></script>
    </body>

</html>