package org.upgrad.common;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ManagedSessionContext;
import org.upgrad.models.Category;
import org.upgrad.models.Question;
import org.upgrad.models.User;
import org.upgrad.models.UserProfile;

public class SessionManager {


    public Session openSession() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(UserProfile.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(Question.class);
       /* configuration.addAnnotatedClass(Post.class);
        configuration.addAnnotatedClass(ProfilePhoto.class);

        configuration.addAnnotatedClass(Likes.class);
        configuration.addAnnotatedClass(Comments.class);*/
        configuration.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.setFlushMode(FlushMode.MANUAL);
        ManagedSessionContext.bind(session);
        session.beginTransaction();

        return session;
    }

    public void commitSession(final Session session){

        session.flush();
        session.getTransaction().commit();
        session.close();
    }
}
