/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Vocabulary")
@NamedQueries({
	@NamedQuery(name = "Vocabulary.findAll", query = "SELECT v FROM Vocabulary v"),
	@NamedQuery(name = "Vocabulary.findByDocumentAndUser", query = "SELECT v FROM Vocabulary v WHERE v.documentId.id = :documentId AND v.userId.id = :userId ORDER BY v.createdAt DESC")
})
public class Vocabulary implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne
	private UserAccount userId;

	@JoinColumn(name = "document_id", referencedColumnName = "id")
	@ManyToOne
	private Document documentId;

	@Column(name = "page_number")
	private Integer pageNumber;

	@Basic(optional = false)
	@Column(name = "word")
	private String word;

	@Column(name = "meaning")
	private String meaning;

	@Column(name = "context_text")
	private String contextText;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	public Vocabulary() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserAccount getUserId() {
		return userId;
	}

	public void setUserId(UserAccount userId) {
		this.userId = userId;
	}

	public Document getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Document documentId) {
		this.documentId = documentId;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getContextText() {
		return contextText;
	}

	public void setContextText(String contextText) {
		this.contextText = contextText;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}


