package com.maktabsharif.repository;

import com.maktabsharif.configuration.DatabaseConfiguration;
import com.maktabsharif.entity.BaseEntity;
import com.maktabsharif.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public abstract class BaseRepository<E extends BaseEntity> {

    SessionFactory sessionFactory = DatabaseConfiguration.buildSessionFactory();
    String entityName;
    private Class<E> type;

    public BaseRepository(String entityName, Class<E> type) {
        this.entityName = entityName;
        this.type = type;
    }

    public boolean create(E e) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Serializable createdId = session.save(e);
        session.getTransaction().commit();
        session.close();

        return createdId instanceof Long;
    }

    public void update(E e, String id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        E result = (E) session.load(type, id);
        result = e;
        result.setId(id);
        session.update(result);
        session.getTransaction().commit();
        session.close();
    }

    public E findById(String id) {
        Session session = sessionFactory.openSession();
        E result = (E) session.get(type, id);
        session.close();
        return result;
    }


//    public List<E> findAll() {
//        Session session = sessionFactory.openSession();
//        Query query = session.createQuery("select e from " + entityName + " e");
//        List list = query.list();
//        session.close();
//        return list;
//    }
//
//    public void delete(Long id) {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        E result = session.get(type, id);
//        User user = session.get(User.class, 1);
//
//        session.delete(result);
//        session.getTransaction().commit();
//        session.close();
//    }
}