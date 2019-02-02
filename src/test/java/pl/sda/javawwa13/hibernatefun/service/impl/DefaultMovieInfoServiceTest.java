package pl.sda.javawwa13.hibernatefun.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pl.sda.javawwa13.hibernatefun.model.MovieInfo;
import pl.sda.javawwa13.hibernatefun.service.MovieInfoService;

import static org.testng.Assert.*;

public class DefaultMovieInfoServiceTest {

    SessionFactory sessionFactory;
    MovieInfoService movieInfoService = new DefaultMovieInfoService();

    @BeforeSuite
    public void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        insertMoviesIntoDb();
    }

    //run this after every method execution
    @AfterTest
    public void cleanUpDB() {}

    @Test
    public void shouldPersistGivenMovies() {
        //insertMoviesIntoDb();
        //tutaj powinny byc juz zapisane 3 filmy w bazie
        try(Session session = sessionFactory.openSession()) {
            MovieInfo mi = movieInfoService.findMovieInfo(session, "Ogniem i mieczem");
            assertNotNull(mi.getId(), "Hibernate should set ID for this instance already!");
            assertEquals(mi.getTitle(), "Ogniem i mieczem");
            assertEquals(mi.getAvgScore(), 8.5, "Score for this movie should be 8.5!");
        }
    }

    //oba testy maja sprawdzac dzialanie metod findOrCreate
    @Test
    public void shouldFindCreatedMovie() {
        //Tworzymy filmy - insertMoviesIntoDb()
        //insertMoviesIntoDb();
        //otwieramy sesje
        try(Session session = sessionFactory.openSession()) {
            //movieInfoService.findOrCreate("Ogniem i mieczem")
            MovieInfo mi = movieInfoService.findOrCreateMovieInfo(session, "Ogniem i mieczem");
            assertNotNull(mi.getAvgScore(), "Score should not be null");
        }
    }

    @Test
    public void shouldCreateNotFoundMovie() {
        //avgScore is null
        try(Session session = sessionFactory.openSession()) {
            MovieInfo mi = movieInfoService.findOrCreateMovieInfo(session, "Dzieci z dworca ZOO");
            assertNull(mi.getAvgScore(), "Score should be null");
            assertEquals(mi.getTitle(), "Dzieci z dworca ZOO");
            assertNotNull(mi.getId(), "ID should already exist");
        }
    }

    @Test
    public void shouldUpdateAvgScoreForMovies() {
        //insertMoviesIntoDb();
        try(Session session = sessionFactory.openSession()) {
            MovieInfo mi = movieInfoService.findOrCreateMovieInfo(session, "Ogniem i mieczem");
            assertEquals(mi.getAvgScore(), 8.5);
            movieInfoService.updateMovieInfo(session, "Ogniem i mieczem", 8.23);
        }
        try(Session session = sessionFactory.openSession()) {
            MovieInfo mi = movieInfoService.findOrCreateMovieInfo(session, "Ogniem i mieczem");
            assertEquals(mi.getAvgScore(), 8.23);
        }
    }

    private MovieInfo createMovieInfo(final String title, final Double avgScore) {
        return MovieInfo.builder()
                .title(title)
                .avgScore(avgScore)
                .build();
    }

    private void insertMoviesIntoDb() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(createMovieInfo("Ogniem i mieczem", 8.5));
            session.persist(createMovieInfo("Alien vs Predator", 7.5));
            session.persist(createMovieInfo("Seks w wielkim miescie", 6.0));
            tx.commit();
        }
    }

}