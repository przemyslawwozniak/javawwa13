package pl.sda.javawwa13.hibernatefun.util;

import org.hibernate.Session;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class SessionUtilTest {

    @Test
    public void testSessionFactory() {
        try(Session session = SessionUtil.getSession()) {
            assertNotNull(session);
        }
    }

}
