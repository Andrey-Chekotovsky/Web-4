package org.example.Dao;


import org.example.Models.Animal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AnotherAnimalService {
    private final SessionFactory factory;
    @Autowired
    public AnotherAnimalService() {
        Configuration configuration = new Configuration()
//                .configure("application.properties")
                .addAnnotatedClass(Animal.class);
        factory = configuration.buildSessionFactory();
    }

    public Animal getAnimal(int id)
    {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Animal animal =  session.get(Animal.class, id);
            session.getTransaction().commit();
            return animal;
        }
    }
    public List<Animal> getAnimals() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Animal> animal =  session.createQuery("from Animal", Animal.class)
                    .list();
            session.getTransaction().commit();
            return animal;
        }
    }

    @Transactional
    public int create(Animal animal)
    {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(animal);
            session.getTransaction().commit();
            return animal.getId();
        }
    }
    @Transactional
    public void update( Animal animal)
    {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.merge(animal);
            session.getTransaction().commit();
        }
    }
    @Transactional
    public void delete(int id)
    {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Animal animal = session.get(Animal.class, id);
            session.remove(animal);
            session.getTransaction().commit();
        }
    }
}
