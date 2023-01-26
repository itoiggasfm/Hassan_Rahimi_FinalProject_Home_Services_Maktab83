package com.maktabsharif.configuration;

//import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

//import javax.imageio.spi.ServiceRegistry;
//import org.hibernate.*;

//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;

//import com.hibernate.data.Person;


public class DatabaseConfiguration {
    private static SessionFactory instance = null;

    private DatabaseConfiguration() {
    }

    public static SessionFactory buildSessionFactory() {
        if (instance == null) {
            synchronized (DatabaseConfiguration.class) {
                if (instance == null) {
                    Configuration configObj = new Configuration();
                    configObj.configure("hibernate.cfg.xml");

                    ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
                            .applySettings(configObj.getProperties()).build();

                    instance = configObj.buildSessionFactory(serviceRegistryObj);
                }
            }
        }
        return instance;
    }



}
