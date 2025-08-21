(function(){
	const container=document.querySelector('.pdf-reader');
	if(!container) return;
	const documentId=window.DOCUMENT_ID;

	function getSelectedText(){
		const selection=window.getSelection();
		return selection?selection.toString().trim():'';
	}

	function showError(msg){
		let el=document.getElementById('pdfError');
		if(!el){
			el=document.createElement('div');
			el.id='pdfError';
			el.style.margin='12px';
			el.style.color='#c00';
			container.prepend(el);
		}
		el.textContent=msg;
	}

	function showSuccessMessage(msg) {
		let el = document.getElementById('successMessage');
		if (!el) {
			el = document.createElement('div');
			el.id = 'successMessage';
			el.style.cssText = `
				position: fixed;
				top: 20px;
				right: 20px;
				background: #52c41a;
				color: white;
				padding: 12px 20px;
				border-radius: 6px;
				box-shadow: 0 4px 12px rgba(0,0,0,0.15);
				z-index: 1000;
				font-size: 14px;
				transform: translateX(100%);
				transition: transform 0.3s ease;
			`;
			document.body.appendChild(el);
		}
		
		el.textContent = msg;
		el.style.transform = 'translateX(0)';
		
		setTimeout(() => {
			el.style.transform = 'translateX(100%)';
		}, 3000);
	}

	function loadAnnotations() {
		console.log('Loading annotations for document:', documentId);
		fetch(`${window.API_ANNOTATIONS}?documentId=${documentId}`)
			.then(r => {
				console.log('Annotations API response status:', r.status);
				if (!r.ok) {
					throw new Error(`HTTP ${r.status}: ${r.statusText}`);
				}
				return r.json();
			})
			.then(list => {
				console.log('Annotations loaded:', list);
				const wrap = document.getElementById('annotations');
				if (!wrap) {
					console.error('Annotations container not found!');
					return;
				}
				
				wrap.innerHTML = '';
				
				if (list.length === 0) {
					wrap.innerHTML = '<p class="muted">Chưa có ghi chú nào.</p>';
					return;
				}
				
				list.forEach(a => {
					const row = document.createElement('div');
					row.className = 'annotation-item';
					row.setAttribute('data-annotation-id', a.id);
					
					const text = document.createElement('span');
					text.className = 'annotation-text';
					text.textContent = (a.highlightText ? `"${a.highlightText}" - ` : '') + a.noteText;
					
					const del = document.createElement('button');
					del.className = 'annotation-delete-btn';
					del.innerHTML = '<i class="fas fa-trash"></i>';
					del.title = 'Xóa ghi chú này';
					del.onclick = (e) => {
						e.preventDefault();
						e.stopPropagation();
						deleteAnnotation(a.id, row);
					};
					
					row.appendChild(text);
					row.appendChild(del);
					wrap.appendChild(row);
				});
			})
			.catch(error => {
				console.error('Error loading annotations:', error);
				const wrap = document.getElementById('annotations');
				if (wrap) {
					wrap.innerHTML = `<p class="error">Lỗi khi tải ghi chú: ${error.message}</p>`;
				}
			});
	}
	
	function deleteAnnotation(annotationId, rowElement) {
		if (!confirm('Bạn có chắc muốn xóa ghi chú này?')) {
			return;
		}
		
		console.log('Deleting annotation:', annotationId);
		
		fetch(`${window.API_ANNOTATIONS}?id=${annotationId}`, {
			method: 'DELETE'
		})
		.then(r => {
			console.log('Delete annotation response status:', r.status);
			if (!r.ok) {
				throw new Error(`HTTP ${r.status}: ${r.statusText}`);
			}
			return r;
		})
		.then(() => {
			console.log('Annotation deleted successfully');
			// Remove the row with animation
			rowElement.style.opacity = '0';
			rowElement.style.transform = 'translateX(20px)';
			setTimeout(() => {
				rowElement.remove();
				// Check if no more annotation items
				const wrap = document.getElementById('annotations');
				if (wrap.children.length === 0) {
					wrap.innerHTML = '<p class="muted">Chưa có ghi chú nào.</p>';
				}
			}, 300);
		})
		.catch(error => {
			console.error('Error deleting annotation:', error);
			alert('Lỗi khi xóa ghi chú: ' + error.message);
		});
	}

	function loadVocabulary(){
		console.log('Loading vocabulary for document:', documentId);
		fetch(`${window.API_VOCAB}?documentId=${documentId}`)
			.then(r => {
				console.log('Vocabulary API response status:', r.status);
				if (!r.ok) {
					throw new Error(`HTTP ${r.status}: ${r.statusText}`);
				}
				return r.json();
			})
			.then(list => {
				console.log('Vocabulary loaded:', list);
				const wrap = document.getElementById('vocabulary');
				if (!wrap) {
					console.error('Vocabulary container not found!');
					return;
				}
				
				wrap.innerHTML = '';
				
				if (list.length === 0) {
					wrap.innerHTML = '<p class="muted">Chưa có từ vựng nào được lưu.</p>';
					return;
				}
				
				list.forEach(v => {
					const row = document.createElement('div');
					row.className = 'vocab-item';
					row.setAttribute('data-vocab-id', v.id);
					
					const text = document.createElement('span');
					text.className = 'vocab-text';
					text.innerHTML = `<strong>${v.word}</strong>${v.meaning ? (' - ' + v.meaning) : ''}`;
					
					const del = document.createElement('button');
					del.className = 'vocab-delete-btn';
					del.innerHTML = '<i class="fas fa-trash"></i>';
					del.title = 'Xóa từ vựng này';
					del.onclick = (e) => {
						e.preventDefault();
						e.stopPropagation();
						deleteVocabulary(v.id, row);
					};
					
					row.appendChild(text);
					row.appendChild(del);
					wrap.appendChild(row);
				});
			})
			.catch(error => {
				console.error('Error loading vocabulary:', error);
				const wrap = document.getElementById('vocabulary');
				if (wrap) {
					wrap.innerHTML = `<p class="error">Lỗi khi tải từ vựng: ${error.message}</p>`;
				}
			});
	}
	
	function deleteVocabulary(vocabId, rowElement) {
		if (!confirm('Bạn có chắc muốn xóa từ vựng này?')) {
			return;
		}
		
		console.log('Deleting vocabulary:', vocabId);
		
		fetch(`${window.API_VOCAB}?id=${vocabId}`, {
			method: 'DELETE'
		})
		.then(r => {
			console.log('Delete response status:', r.status);
			if (!r.ok) {
				throw new Error(`HTTP ${r.status}: ${r.statusText}`);
			}
			return r;
		})
		.then(() => {
			console.log('Vocabulary deleted successfully');
			// Remove the row with animation
			rowElement.style.opacity = '0';
			rowElement.style.transform = 'translateX(20px)';
			setTimeout(() => {
				rowElement.remove();
				// Check if no more vocabulary items
				const wrap = document.getElementById('vocabulary');
				if (wrap.children.length === 0) {
					wrap.innerHTML = '<p class="muted">Chưa có từ vựng nào được lưu.</p>';
				}
			}, 300);
		})
		.catch(error => {
			console.error('Error deleting vocabulary:', error);
			alert('Lỗi khi xóa từ vựng: ' + error.message);
		});
	}

	document.getElementById('saveNote').addEventListener('click', function(e) {
		e.preventDefault();
		const noteText = document.getElementById('noteInput').value.trim();
		if (!noteText) {
			alert('Vui lòng nhập nội dung ghi chú!');
			return;
		}
		
		const highlightText = getSelectedText();
		console.log('Saving note:', { noteText, highlightText });
		
		// Show loading state
		const saveBtn = this;
		const originalText = saveBtn.textContent;
		saveBtn.textContent = 'Đang lưu...';
		saveBtn.disabled = true;
		
		const form = new URLSearchParams();
		form.set('documentId', documentId);
		form.set('highlightText', highlightText);
		form.set('noteText', noteText);
		
		fetch(window.API_ANNOTATIONS, {
			method: 'POST',
			headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			body: form
		})
		.then(r => {
			if (!r.ok) {
				throw new Error(`HTTP ${r.status}: ${r.statusText}`);
			}
			return r.json();
		})
		.then(result => {
			console.log('Note saved successfully:', result);
			// Clear input and reload annotations
			document.getElementById('noteInput').value = '';
			loadAnnotations();
			// Show success message
			showSuccessMessage('Ghi chú đã được lưu thành công!');
		})
		.catch(error => {
			console.error('Error saving note:', error);
			alert('Lỗi khi lưu ghi chú: ' + error.message);
		})
		.finally(() => {
			// Reset button state
			saveBtn.textContent = originalText;
			saveBtn.disabled = false;
		});
	});

	document.getElementById('saveWord').addEventListener('click', function(e) {
		e.preventDefault();
		let word = document.getElementById('searchInput').value.trim();
		if (!word) word = getSelectedText();
		if (!word) {
			alert('Vui lòng chọn từ hoặc nhập từ cần lưu!');
			return;
		}
		
		console.log('Saving word:', word);
		
		// Show loading state
		const saveBtn = this;
		const originalText = saveBtn.textContent;
		saveBtn.textContent = 'Đang lưu...';
		saveBtn.disabled = true;
		
		fetch(`${window.CONTEXT_PATH}/api/dictionary?word=` + encodeURIComponent(word))
			.then(r => r.ok ? r.json() : null)
			.then(data => {
				let meaning = '';
				try {
					if (Array.isArray(data) && data.length > 0 && data[0].meanings && data[0].meanings.length > 0) {
						const defs = data[0].meanings[0].definitions;
						if (defs && defs.length > 0) meaning = defs[0].definition;
					}
				} catch (err) {
					console.log('Dictionary API error:', err);
				}
				
				return fetch(`${window.CONTEXT_PATH}/api/translate?text=` + encodeURIComponent(meaning || word) + `&target=vi`)
					.then(r => r.ok ? r.json() : null)
					.then(tr => {
						let vn = '';
						try {
							vn = tr && tr.translatedText ? tr.translatedText : '';
						} catch (e) {
							console.log('Translation API error:', e);
						}
						
						if (!vn) {
							const typed = window.prompt('Nhập nghĩa tiếng Việt của từ "' + word + '":', '');
							if (typed === null) return null; // cancel
							vn = (typed || '').trim();
							if (!vn) return null;
						}
						
						const form = new URLSearchParams();
						form.set('documentId', documentId);
						form.set('word', word);
						form.set('meaning', vn);
						form.set('contextText', getSelectedText());
						
						return fetch(window.API_VOCAB, {
							method: 'POST',
							headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
							body: form
						});
					});
			})
			.then(r => {
				if (r) {
					return r.json();
				}
				return null;
			})
			.then(result => {
				if (result) {
					console.log('Word saved successfully:', result);
					// Clear input and reload vocabulary
					document.getElementById('searchInput').value = '';
					loadVocabulary();
					// Show success message
					showSuccessMessage('Từ vựng đã được lưu thành công!');
				}
			})
			.catch(error => {
				console.error('Error saving word:', error);
				alert('Lỗi khi lưu từ vựng: ' + error.message);
			})
			.finally(() => {
				// Reset button state
				saveBtn.textContent = originalText;
				saveBtn.disabled = false;
			});
	});

	loadAnnotations();
	loadVocabulary();
})();


