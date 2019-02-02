package pl.sda.javawwa13.hibernatefun.service.impl;

import org.testng.annotations.Test;
import pl.sda.javawwa13.hibernatefun.model.MovieInfo;
import pl.sda.javawwa13.hibernatefun.service.MovieInfoServiceRefactored;

import static org.testng.Assert.*;

public class DefaultMovieInfoServiceRefactoredTest {

    MovieInfoServiceRefactored movieInfoService = new DefaultMovieInfoServiceRefactored();

    @Test
    public void shouldRemoveMovieInfoFromDb() {
        //mamy zaufanie ze findOrCreate rzeczywiscie dziala (testowalismy wczesniej)
        MovieInfo mi = movieInfoService.findOrCreateMovieInfo("Ogniem i mieczem");
        assertNotNull(mi.getId());
        movieInfoService.deleteMovieInfo("Ogniem i mieczem");
        mi = movieInfoService.findMovieInfo("Ogniem i mieczem");
        assertNull(mi, "This movie should already be removed from DB!");
    }

}