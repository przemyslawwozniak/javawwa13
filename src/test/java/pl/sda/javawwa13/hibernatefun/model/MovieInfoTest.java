package pl.sda.javawwa13.hibernatefun.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pl.sda.javawwa13.hibernatefun.util.SessionUtil;

import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

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

    @Test
    public void shouldRemoveOrphans() {
        Long mi1Id, mc1Id, mc2Id;
        MovieInfo mi1 = new MovieInfo.MovieInfoBuilder()
                .title("Ogniem i mieczem")
                .avgScore(8.5)
                .build();
        MovieCopy mc1 = MovieCopy.builder()
                .movieInfo(mi1)
                .build();
        MovieCopy mc2 = MovieCopy.builder()
                .movieInfo(mi1)
                .build();
        mi1.setCopies(Arrays.asList(mc1, mc2));

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            //cascade persist is on, should save related entities with this one operation
            session.persist(mi1);
            tx.commit();
            //IDs should be set for persisted entities
            mi1Id = mi1.getId();
            assertNotNull(mi1Id);
            mc1Id = mc1.getId();
            assertNotNull(mc1Id);
            mc2Id = mc2.getId();
            assertNotNull(mc2Id);
        }

        //new session
        try(Session session = sessionFactory.openSession()) {
            //load entities from DB by IDs
            mi1 = session.get(MovieInfo.class, mi1Id);
            assertNotNull(mi1);
            mc1 = session.get(MovieCopy.class, mc1Id);
            assertNotNull(mc1);
            mc2 = session.get(MovieCopy.class, mc2Id);
            assertNotNull(mc2);
            //open transaction to modify DB
            Transaction tx = session.beginTransaction();
            session.delete(mi1);    //orphan removal is on, should remove orphans
            tx.commit();
        }

        //start new session to check if entites were removed from DB
        try(Session session = sessionFactory.openSession()) {
            mi1 = session.get(MovieInfo.class, mi1Id);
            assertNull(mi1);
            mc1 = session.get(MovieCopy.class, mc1Id);
            assertNull(mc1);
            mc2 = session.get(MovieCopy.class, mc2Id);
            assertNull(mc2);
        }
    }

    @Test
    public void daysSinceReleaseShouldBeCalculatedPostLoad() {
        Long mId;
        MovieInfo mi1 = new MovieInfo.MovieInfoBuilder()
                .title("Ogniem i mieczem")
                .avgScore(8.5)
                .releaseDate(LocalDate.of(1999, 2, 8))
                .build();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(mi1);
            tx.commit();
            //IDs should be set for persisted entities
            mId = mi1.getId();
            assertNotNull(mId);
            assertNull(mi1.getDaysSinceRelease());
        }
        try(Session session = sessionFactory.openSession()) {
            mi1 = session.get(MovieInfo.class, mId);
            assertNotNull(mi1.getDaysSinceRelease());
            System.out.println("Dni od premiery: " + mi1.getDaysSinceRelease());
        }
    }

    @Test
    public void testNamedQueryByFilmCompany() {
        MovieInfo mi1 = MovieInfo.builder()
                .title("The Hobbit: An Unexpected Journey")
                .releaseDate(LocalDate.of(2012, 12, 25))
                .filmCompany("Warner Bros")
                .build();
        MovieInfo mi2 = MovieInfo.builder()
                .title("The Dark Knight")
                .releaseDate(LocalDate.of(2019, 2, 3))
                .filmCompany("Warner Bros")
                .build();
        MovieInfo mi3 = MovieInfo.builder()
                .title("Spider-Man 3")
                .releaseDate(LocalDate.of(2018, 1, 1))
                .filmCompany("Sony Pictures")
                .build();
        MovieInfo mi4 = MovieInfo.builder()
                .title("Pirates of the Caribbean: Dead Man's Chest")
                .releaseDate(LocalDate.of(2017, 1, 1))
                .filmCompany("Walt Disney Studios")
                .build();

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(mi1);
            session.persist(mi2);
            session.persist(mi3);
            session.persist(mi4);
            tx.commit();
        }

        try(Session session = SessionUtil.getSession()) {
            Query query = session.getNamedQuery("movieinfo.byFilmCompany");
            query.setParameter("company", "Warner Bros");
            List<MovieInfo> wbMovies = query.list();
            assertEquals(wbMovies.size(), 2);

            query.setParameter("company", "Sony Pictures");
            MovieInfo sonyMovie = (MovieInfo) query.uniqueResult();
            assertEquals(sonyMovie.getTitle(), "Spider-Man 3");
        }
    }

}
