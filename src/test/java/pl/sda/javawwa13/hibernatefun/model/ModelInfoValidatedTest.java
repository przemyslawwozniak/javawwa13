package pl.sda.javawwa13.hibernatefun.model;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;
import pl.sda.javawwa13.hibernatefun.util.SessionUtil;

import javax.validation.ConstraintViolationException;

import java.time.LocalDate;

import static org.testng.Assert.fail;

public class ModelInfoValidatedTest {

    @Test
    public void shouldAllowPersistingValidBean() {
        MovieInfoValidated miValidated = MovieInfoValidated.builder()
                .avgScore(8.5)
                .title("Ogniem i mieczem")
                .description("Polski film historyczny z 1999 roku, w reżyserii Jerzego Hoffmana, na podstawie powieści Henryka Sienkiewicza pod tym samym tytułem.")
                .build();

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(miValidated);
            tx.commit();
        }
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void shouldFailOnPersisitingNonValidBean() {
        MovieInfoValidated miNonValidated = MovieInfoValidated.builder()
                .avgScore(8.5)
                //.title("Ogniem i mieczem")
                .description("Polski film historyczny z 1999 roku, w reżyserii Jerzego Hoffmana, na podstawie powieści Henryka Sienkiewicza pod tym samym tytułem.")
                .build();

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(miNonValidated);    //walidacja jest odpalana tutaj
            tx.commit();
        }

        fail("Bean validation wasn't properly exectued!");
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void shouldFailOnPersistingMovieReleasedBeforeDVDPremiere() {
        MovieInfoValidated miBeforeDVDPremiere = MovieInfoValidated.builder()
                .avgScore(8.5)
                .title("Ogniem i mieczem")
                .description("Polski film historyczny z 1999 roku, w reżyserii Jerzego Hoffmana, na podstawie powieści Henryka Sienkiewicza pod tym samym tytułem.")
                .releaseDate(LocalDate.of(1991, 1, 1))
                .build();


        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(miBeforeDVDPremiere);    //walidacja jest odpalana tutaj
            tx.commit();
        }

        fail("Bean validation wasn't properly exectued!");
    }

}
