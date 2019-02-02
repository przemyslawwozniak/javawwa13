package pl.sda.javawwa13.hibernatefun.service;

import org.hibernate.Session;
import pl.sda.javawwa13.hibernatefun.model.MovieInfo;

public interface MovieInfoService {

    MovieInfo findMovieInfo(Session session, String title);
    MovieInfo findOrCreateMovieInfo(Session session, String title);

}
