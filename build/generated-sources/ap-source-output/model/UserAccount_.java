package model;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Annotation;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-08-20T19:20:05", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(UserAccount.class)
public class UserAccount_ { 

    public static volatile ListAttribute<UserAccount, Annotation> annotationList;
    public static volatile SingularAttribute<UserAccount, Date> createdAt;
    public static volatile SingularAttribute<UserAccount, String> password;
    public static volatile SingularAttribute<UserAccount, Integer> id;
    public static volatile SingularAttribute<UserAccount, String> email;
    public static volatile SingularAttribute<UserAccount, String> username;

}