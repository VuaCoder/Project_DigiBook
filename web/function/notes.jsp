<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Annotation" %>
<%@ page import="model.Vocabulary" %>
<%
    List<Annotation> annotations = (List<Annotation>) request.getAttribute("annotations");
    List<Vocabulary> vocabularies = (List<Vocabulary>) request.getAttribute("vocabularies");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Digibook - Ghi chú</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/digibook.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/notes.css">
    <link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/img/favi.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/common/header.jsp"/>

    <div class="notes-page">
        <div class="container">
            <h1><i class="fas fa-sticky-note"></i> Ghi chú & Từ vựng của tôi</h1>

            <!-- Tabs -->
            <div class="tabs">
                <button class="tab-btn active" data-tab="annotations">
                    <i class="fas fa-edit"></i> Ghi chú (<span id="annotationCount"><%=annotations != null ? annotations.size() : 0%></span>)
                </button>
                <button class="tab-btn" data-tab="vocabulary">
                    <i class="fas fa-book"></i> Từ vựng (<span id="vocabCount"><%=vocabularies != null ? vocabularies.size() : 0%></span>)
                </button>
            </div>

            <!-- Annotations Tab -->
            <div id="annotations" class="tab-content active">
                <div class="section-header">
                    <h2><i class="fas fa-edit"></i> Ghi chú đã lưu</h2>
                    <p class="muted">Các ghi chú và highlight từ tài liệu của bạn</p>
                </div>

                <div class="items-container" id="annotationsContainer">
                    <% if (annotations != null && !annotations.isEmpty()) { %>
                        <% for (Annotation ann : annotations) { %>
                            <div class="item-card annotation-item" data-id="<%=ann.getId()%>">
                                <div class="item-content">
                                    <% if (ann.getHighlightText() != null && !ann.getHighlightText().isEmpty()) { %>
                                        <div class="highlight-text">
                                            <i class="fas fa-highlighter"></i>
                                            "<%=ann.getHighlightText()%>"
                                        </div>
                                    <% } %>
                                    <div class="note-text">
                                        <i class="fas fa-sticky-note"></i>
                                        <%=ann.getNoteText()%>
                                    </div>
                                    <div class="item-meta">
                                        <span class="document-name">
                                            <i class="fas fa-file-pdf"></i>
                                            <%=ann.getDocumentId().getCourseId().getTitle()%>
                                        </span>
                                        <span class="date">
                                            <i class="fas fa-calendar"></i>
                                            <%=ann.getCreatedAt()%>
                                        </span>
                                    </div>
                                </div>
                                <button class="delete-btn" onclick="deleteAnnotation(<%=ann.getId()%>)">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        <% } %>
                    <% } else { %>
                        <div class="empty-state">
                            <i class="fas fa-edit"></i>
                            <h3>Chưa có ghi chú nào</h3>
                            <p>Bạn chưa lưu ghi chú nào. Hãy mở tài liệu và tạo ghi chú đầu tiên!</p>
                        </div>
                    <% } %>
                </div>
            </div>

            <!-- Vocabulary Tab -->
            <div id="vocabulary" class="tab-content">
                <div class="section-header">
                    <h2><i class="fas fa-book"></i> Từ vựng yêu thích</h2>
                    <p class="muted">Các từ vựng bạn đã lưu từ tài liệu</p>
                </div>

                <div class="items-container" id="vocabularyContainer">
                    <% if (vocabularies != null && !vocabularies.isEmpty()) { %>
                        <% for (Vocabulary vocab : vocabularies) { %>
                            <div class="item-card vocab-item" data-id="<%=vocab.getId()%>">
                                <div class="item-content">
                                    <div class="word">
                                        <i class="fas fa-font"></i>
                                        <strong><%=vocab.getWord()%></strong>
                                    </div>
                                    <% if (vocab.getMeaning() != null && !vocab.getMeaning().isEmpty()) { %>
                                        <div class="meaning">
                                            <i class="fas fa-language"></i>
                                            <%=vocab.getMeaning()%>
                                        </div>
                                    <% } %>
                                    <% if (vocab.getContextText() != null && !vocab.getContextText().isEmpty()) { %>
                                        <div class="context">
                                            <i class="fas fa-quote-left"></i>
                                            "<%=vocab.getContextText()%>"
                                        </div>
                                    <% } %>
                                    <div class="item-meta">
                                        <span class="document-name">
                                            <i class="fas fa-file-pdf"></i>
                                            <%=vocab.getDocumentId().getCourseId().getTitle()%>
                                        </span>
                                        <span class="date">
                                            <i class="fas fa-calendar"></i>
                                            <%=vocab.getCreatedAt()%>
                                        </span>
                                    </div>
                                </div>
                                <button class="delete-btn" onclick="deleteVocabulary(<%=vocab.getId()%>)">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        <% } %>
                    <% } else { %>
                        <div class="empty-state">
                            <i class="fas fa-book"></i>
                            <h3>Chưa có từ vựng nào</h3>
                            <p>Bạn chưa lưu từ vựng nào. Hãy mở tài liệu và lưu từ vựng đầu tiên!</p>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/common/footer.jsp"/>

    <script>
        // Tab switching
        document.querySelectorAll('.tab-btn').forEach(function(btn) {
            btn.addEventListener('click', function() {
                document.querySelectorAll('.tab-btn').forEach(function(b) { 
                    b.classList.remove('active'); 
                });
                document.querySelectorAll('.tab-content').forEach(function(c) { 
                    c.classList.remove('active'); 
                });
                btn.classList.add('active');
                document.getElementById(btn.dataset.tab).classList.add('active');
            });
        });

        // Delete annotation
        function deleteAnnotation(id) {
            if (!confirm('Bạn có chắc muốn xóa ghi chú này?')) return;

            // Disable delete button to prevent double-click
            var deleteBtn = document.querySelector('[data-id="' + id + '"] .delete-btn');
            if (deleteBtn) {
                deleteBtn.disabled = true;
                deleteBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';
            }

            fetch('<%=request.getContextPath()%>/api/annotations?id=' + id, { 
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(function(response) {
                console.log('Delete annotation response:', response.status, response.statusText);
                
                if (response.ok) {
                    // Show success message
                    showMessage('Đã xóa ghi chú thành công!', 'success');
                    
                    // Remove from DOM with animation
                    var item = document.querySelector('[data-id="' + id + '"]');
                    if (item) {
                        item.style.opacity = '0';
                        item.style.transform = 'translateX(20px)';
                        setTimeout(function() {
                            item.remove();
                            updateAnnotationCount();
                            checkEmptyState('annotationsContainer');
                        }, 300);
                    }
                } else {
                    throw new Error('HTTP ' + response.status + ': ' + response.statusText);
                }
            })
            .catch(function(error) {
                console.error('Error deleting annotation:', error);
                showMessage('Lỗi khi xóa ghi chú: ' + error.message, 'error');
                
                // Re-enable delete button
                if (deleteBtn) {
                    deleteBtn.disabled = false;
                    deleteBtn.innerHTML = '<i class="fas fa-trash"></i>';
                }
            });
        }

        // Delete vocabulary
        function deleteVocabulary(id) {
            if (!confirm('Bạn có chắc muốn xóa từ vựng này?')) return;

            // Disable delete button to prevent double-click
            var deleteBtn = document.querySelector('[data-id="' + id + '"] .delete-btn');
            if (deleteBtn) {
                deleteBtn.disabled = true;
                deleteBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';
            }

            fetch('<%=request.getContextPath()%>/api/vocabulary?id=' + id, { 
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(function(response) {
                console.log('Delete vocabulary response:', response.status, response.statusText);
                
                if (response.ok) {
                    // Show success message
                    showMessage('Đã xóa từ vựng thành công!', 'success');
                    
                    // Remove from DOM with animation
                    var item = document.querySelector('[data-id="' + id + '"]');
                    if (item) {
                        item.style.opacity = '0';
                        item.style.transform = 'translateX(20px)';
                        setTimeout(function() {
                            item.remove();
                            updateVocabCount();
                            checkEmptyState('vocabularyContainer');
                        }, 300);
                    }
                } else {
                    throw new Error('HTTP ' + response.status + ': ' + response.statusText);
                }
            })
            .catch(function(error) {
                console.error('Error deleting vocabulary:', error);
                showMessage('Lỗi khi xóa từ vựng: ' + error.message, 'error');
                
                // Re-enable delete button
                if (deleteBtn) {
                    deleteBtn.disabled = false;
                    deleteBtn.innerHTML = '<i class="fas fa-trash"></i>';
                }
            });
        }

        // Update counts
        function updateAnnotationCount() {
            var count = document.querySelectorAll('.annotation-item').length;
            document.getElementById('annotationCount').textContent = count;
        }

        function updateVocabCount() {
            var count = document.querySelectorAll('.vocab-item').length;
            document.getElementById('vocabCount').textContent = count;
        }

        // Check empty state
        function checkEmptyState(containerId) {
            var container = document.getElementById(containerId);
            if (container.children.length === 0) {
                var emptyState = document.createElement('div');
                emptyState.className = 'empty-state';
                if (containerId === 'annotationsContainer') {
                    emptyState.innerHTML = 
                        '<i class="fas fa-edit"></i>' +
                        '<h3>Chưa có ghi chú nào</h3>' +
                        '<p>Bạn chưa lưu ghi chú nào. Hãy mở tài liệu và tạo ghi chú đầu tiên!</p>';
                } else {
                    emptyState.innerHTML = 
                        '<i class="fas fa-book"></i>' +
                        '<h3>Chưa có từ vựng nào</h3>' +
                        '<p>Bạn chưa lưu từ vựng nào. Hãy mở tài liệu và lưu từ vựng đầu tiên!</p>';
                }
                container.appendChild(emptyState);
            }
        }

        // Show message function
        function showMessage(message, type) {
            // Remove existing messages
            var existingMessages = document.querySelectorAll('.message');
            existingMessages.forEach(function(msg) { 
                msg.remove(); 
            });

            // Create new message
            var messageDiv = document.createElement('div');
            messageDiv.className = 'message message-' + type;
            
            var iconClass = type === 'success' ? 'check-circle' : 'exclamation-circle';
            messageDiv.innerHTML = 
                '<i class="fas fa-' + iconClass + '"></i>' +
                '<span>' + message + '</span>' +
                '<button onclick="this.parentElement.remove()" class="close-btn">' +
                '<i class="fas fa-times"></i>' +
                '</button>';

            // Add to page
            document.body.appendChild(messageDiv);

            // Auto remove after 5 seconds
            setTimeout(function() {
                if (messageDiv.parentElement) {
                    messageDiv.remove();
                }
            }, 5000);
        }
    </script>
</body>
</html>
