<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Document" %>
<%
    Document doc = (Document) request.getAttribute("document");
    String docId = doc != null ? String.valueOf(doc.getId()) : "";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>PDF Reader</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/pdf-reader.css" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans:ital,wght@0,100..900;1,100..900&display=swap"
              rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favi.png">
    </head>
    <body>
        <jsp:include page="/common/header.jsp"/>
        <div class="pdf-reader" data-document-id="<%=docId%>">
            <div class="viewer" id="viewer">
                <iframe id="pdfFrame" src="<%=request.getContextPath()%>/documents/stream?id=<%=docId%>#zoom=page-width" frameborder="0"></iframe>
            </div>
            <div class="sidepanel">
                <h3>Công cụ</h3>
                <p class="muted">Bôi đen trên PDF, sau đó dùng các nút để lưu.</p>
                
                <h3>Ghi chú</h3>
                <div class="note-control">
                    <textarea id="noteInput" placeholder="Nhập ghi chú của bạn..." rows="3"></textarea>
                    <button type="button" id="saveNote">Lưu ghi chú</button>
                </div>
                <div id="annotations"></div>
                
                <h3>Từ vựng yêu thích</h3>
                <div class="vocab-control">
                    <input id="searchInput" type="text" placeholder="Từ cần lưu..." />
                    <button type="button" id="saveWord">Lưu từ</button>
                </div>
                <div id="vocabulary"></div>
            </div>
        </div>
        <script>
            window.API_ANNOTATIONS = "<%=request.getContextPath()%>/api/annotations";
            window.API_VOCAB = "<%=request.getContextPath()%>/api/vocabulary";
            window.CONTEXT_PATH = "<%=request.getContextPath()%>";
            window.DOCUMENT_ID = "<%=docId%>";
        </script>
        <script src="<%=request.getContextPath()%>/js/pdf-reader.js"></script>
        <jsp:include page="/common/footer.jsp"/>
    </body>
</html>


