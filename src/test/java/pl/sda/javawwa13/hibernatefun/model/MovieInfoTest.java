package pl.sda.javawwa13.hibernatefun.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class MovieInfoTest {

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
    public void shouldPersistGivenMovieInfos() {
        MovieInfo mi1 = new MovieInfo.MovieInfoBuilder()
                .title("Ogniem i mieczem")
                .avgScore(8.5)
                .build();
        MovieInfo mi2 = new MovieInfo.MovieInfoBuilder()
                .title("Alien vs Predator")
                .avgScore(7.5)
                .build();

        try(Session session = sessionFactory.openSession()) {   //tworze obiekt Session
            Transaction tx = session.beginTransaction();    //tworzy transakcje powiazana z tym session
            session.persist(mi1);    //1 akcja w transakcji: zapisz do bazy
            session.persist(mi2);    //2 akcja, jw
            tx.commit();    //COMMIT transakcji, za ta linijka mamy utrwalenie w bazie danych
        }
    }

}
