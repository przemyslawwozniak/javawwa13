package pl.sda.javawwa13.hibernatefun.model;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;
import pl.sda.javawwa13.hibernatefun.util.SessionUtil;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class DomainRelationsTest {

    @Test
    public void testRelationBetweenMovieCopyAndMovieInfo() {
        Long copyId, infoId;
        MovieCopy mc;
        MovieInfo mi;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            mc = new MovieCopy();
            mi = new MovieInfo();

            mc.setMovieInfo(mi);

            session.save(mc);
            session.save(mi);

            copyId = mc.getId();
            infoId = mi.getId();

            tx.commit();
        }

        assertNull(mi.getCopies());
        assertNotNull(mc.getMovieInfo());

        try(Session session = SessionUtil.getSession()) {
            mc = session.get(MovieCopy.class, copyId);
            mi = session.get(MovieInfo.class, infoId);
        }

        assertNotNull(mi.getCopies());
        assertNotNull(mc.getMovieInfo());
    }

}
