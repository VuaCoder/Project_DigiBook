package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Document;
import model.UserAccount;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-20T19:20:05", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Annotation.class)
public class Annotation_ { 

    public static volatile SingularAttribute<Annotation, String> noteText;
    public static volatile SingularAttribute<Annotation, Date> createdAt;
    public static volatile SingularAttribute<Annotation, Integer> pageNumber;
    public static volatile SingularAttribute<Annotation, Document> documentId;
    public static volatile SingularAttribute<Annotation, Integer> id;
    public static volatile SingularAttribute<Annotation, UserAccount> userId;
    public static volatile SingularAttribute<Annotation, String> highlightText;

}