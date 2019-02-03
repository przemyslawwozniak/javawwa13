package pl.sda.javawwa13.hibernatefun.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MovieInfoValidated {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @Min(0)
    @Max(10)
    private Double avgScore;

    @OneToMany(mappedBy = "movieInfo", orphanRemoval = true, cascade = {CascadeType.PERSIST})
    private List<MovieCopy> copies;

    @OneToMany(mappedBy = "movieInfo", orphanRemoval = true, cascade = {CascadeType.PERSIST})
    private List<Rank> ranks;

    private LocalDate releaseDate;

    @Transient
    private Long daysSinceRelease;

    @Length(min = 100)  //wielkosc kolumny w tabeli na bazie nadal 255!
    //@Column(length = 2000)
    private String description;

    @PostLoad
    public void calculateDaysSinceRelease() {
        this.daysSinceRelease = ChronoUnit.DAYS.between(releaseDate, LocalDate.now());
    }

}
