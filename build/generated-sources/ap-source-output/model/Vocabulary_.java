package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Document;
import model.UserAccount;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-20T19:20:05", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Vocabulary.class)
public class Vocabulary_ { 

    public static volatile SingularAttribute<Vocabulary, Date> createdAt;
    public static volatile SingularAttribute<Vocabulary, String> contextText;
    public static volatile SingularAttribute<Vocabulary, Integer> pageNumber;
    public static volatile SingularAttribute<Vocabulary, String> meaning;
    public static volatile SingularAttribute<Vocabulary, Document> documentId;
    public static volatile SingularAttribute<Vocabulary, Integer> id;
    public static volatile SingularAttribute<Vocabulary, UserAccount> userId;
    public static volatile SingularAttribute<Vocabulary, String> word;

}