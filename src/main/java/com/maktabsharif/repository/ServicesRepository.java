package com.maktabsharif.repository;

import com.maktabsharif.entity.Services;
import com.maktabsharif.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class ServicesRepository extends BaseRepository<Services> {

    public ServicesRepository() {
        super("Services", Services.class);
    }


    public Services findByServiceTitle(String serviceTitle) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select s from Services s where s.serviceTitle=:serviceTitle")
                .setParameter("serviceTitle", serviceTitle);
        List<Services> list = query.list();
        session.close();
        if(list.isEmpty())
            return null;
        return list.get(0);
    }
}
