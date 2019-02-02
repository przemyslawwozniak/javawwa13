package pl.sda.javawwa13.hibernatefun.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class SimpleMovieTest {

    SessionFactory sessionFactory;

    @BeforeSuite
    public void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    @Test
    public void shouldSaveGivenMovies() {
        SimpleMovie m1 = new SimpleMovie("Ogniem i mieczem");
        SimpleMovie m2 = new SimpleMovie("Planet Earth II");

        try(Session session = sessionFactory.openSession()) {   //tworze obiekt Session
            Transaction tx = session.beginTransaction();    //tworzy transakcje powiazana z tym session
            session.persist(m1);    //1 akcja w transakcji: zapisz do bazy
            session.persist(m2);    //2 akcja, jw
            tx.commit();    //COMMIT transakcji, za ta linijka mamy utrwalenie w bazie danych
        }
    }

}
