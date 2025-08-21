package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Annotation;
import model.Course;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-20T19:20:05", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Document.class)
public class Document_ { 

    public static volatile ListAttribute<Document, Annotation> annotationList;
    public static volatile SingularAttribute<Document, String> filePath;
    public static volatile SingularAttribute<Document, Date> uploadedAt;
    public static volatile SingularAttribute<Document, Integer> id;
    public static volatile SingularAttribute<Document, Course> courseId;

}