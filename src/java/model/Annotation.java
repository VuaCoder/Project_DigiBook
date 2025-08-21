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

/**
 *
 * @author HOANG PHUC
 */
@Entity
@Table(name = "Annotation")
@NamedQueries({
    @NamedQuery(name = "Annotation.findAll", query = "SELECT a FROM Annotation a"),
    @NamedQuery(name = "Annotation.findById", query = "SELECT a FROM Annotation a WHERE a.id = :id"),
    @NamedQuery(name = "Annotation.findByPageNumber", query = "SELECT a FROM Annotation a WHERE a.pageNumber = :pageNumber"),
    @NamedQuery(name = "Annotation.findByHighlightText", query = "SELECT a FROM Annotation a WHERE a.highlightText = :highlightText"),
    @NamedQuery(name = "Annotation.findByNoteText", query = "SELECT a FROM Annotation a WHERE a.noteText = :noteText"),
    @NamedQuery(name = "Annotation.findByCreatedAt", query = "SELECT a FROM Annotation a WHERE a.createdAt = :createdAt")})
public class Annotation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "page_number")
    private Integer pageNumber;
    @Column(name = "highlight_text")
    private String highlightText;
    @Column(name = "note_text")
    private String noteText;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    @ManyToOne
    private Document documentId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private UserAccount userId;

    public Annotation() {
    }

    public Annotation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getHighlightText() {
        return highlightText;
    }

    public void setHighlightText(String highlightText) {
        this.highlightText = highlightText;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Document getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Document documentId) {
        this.documentId = documentId;
    }

    public UserAccount getUserId() {
        return userId;
    }

    public void setUserId(UserAccount userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annotation)) {
            return false;
        }
        Annotation other = (Annotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Annotation[ id=" + id + " ]";
    }
    
}
