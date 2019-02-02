package pl.sda.javawwa13.hibernatefun.service.impl;

import pl.sda.javawwa13.hibernatefun.model.MovieInfo;
import pl.sda.javawwa13.hibernatefun.service.MovieInfoServiceRefactored;

//uses SessionUtil.getSession()
public class DefaultMovieInfoServiceRefactored implements MovieInfoServiceRefactored {

    @Override
    public MovieInfo findMovieInfo(String title) {
        return null;
    }

    @Override
    public MovieInfo findOrCreateMovieInfo(String title) {
        return null;
    }

    @Override
    public MovieInfo updateMovieInfo(String title, Double score) {
        return null;
    }

    @Override
    public MovieInfo deleteMovieInfo(String title) {
        return null;
    }
}
