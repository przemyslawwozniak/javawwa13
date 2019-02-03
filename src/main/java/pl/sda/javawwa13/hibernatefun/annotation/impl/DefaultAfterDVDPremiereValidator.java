package pl.sda.javawwa13.hibernatefun.annotation.impl;

import pl.sda.javawwa13.hibernatefun.annotation.AfterDVDPremiere;
import pl.sda.javawwa13.hibernatefun.model.MovieInfoValidated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DefaultAfterDVDPremiereValidator implements ConstraintValidator<AfterDVDPremiere, MovieInfoValidated> {

    @Override
    public boolean isValid(MovieInfoValidated movieInfo, ConstraintValidatorContext constraintValidatorContext) {
        return movieInfo.getReleaseDate().isAfter(LocalDate.of(1996, 12, 20));
    }

    @Override
    public void initialize(AfterDVDPremiere constraintAnnotation) {

    }
}
