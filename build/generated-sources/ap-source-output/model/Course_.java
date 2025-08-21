package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Document;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-20T19:20:05", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Course.class)
public class Course_ { 

    public static volatile SingularAttribute<Course, Date> createdAt;
    public static volatile ListAttribute<Course, Document> documentList;
    public static volatile SingularAttribute<Course, String> author;
    public static volatile SingularAttribute<Course, String> coverImage;
    public static volatile SingularAttribute<Course, Integer> publicationYear;
    public static volatile SingularAttribute<Course, String> description;
    public static volatile SingularAttribute<Course, Integer> id;
    public static volatile SingularAttribute<Course, String> title;
    public static volatile SingularAttribute<Course, String> department;

}