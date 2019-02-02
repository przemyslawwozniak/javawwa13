package pl.sda.javawwa13.hibernatefun.service;

import pl.sda.javawwa13.hibernatefun.model.MovieInfo;

//internally reponsible for Session handling
public interface MovieInfoServiceRefactored {

    MovieInfo findMovieInfo(String title);
    MovieInfo findOrCreateMovieInfo(String title);
    MovieInfo updateMovieInfo(String title, Double score);
    void deleteMovieInfo(String title);

}
