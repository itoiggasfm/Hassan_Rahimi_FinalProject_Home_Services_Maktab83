package com.maktabsharif.repository;

import com.maktabsharif.entity.BaseEntity;
import com.maktabsharif.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
        super("User", User.class);
    }

    public User findByUsername(String username) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select u from User u where u.username=:username")
                .setParameter("username", username);
        List<User> list = query.list();
        session.close();
        if(list.isEmpty())
            return null;
        return list.get(0);
    }

    public User findByEmail(String email) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select u from User u where u.email=:email")
                .setParameter("email", email);
        List<User> list = query.list();
        session.close();
        if(list.isEmpty())
            return null;
        return list.get(0);
    }
}
