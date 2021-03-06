package pl.sda.javawwa13.hibernatefun.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQueries({
        @NamedQuery(name="movieinfo.byFilmCompany",
        query="from MovieInfo mi where mi.filmCompany=:company"),
        @NamedQuery(name="movieinfo.byAvgScore",
        query="from MovieInfo mi where mi.avgScore>=:score")
})
public class MovieInfo {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Double avgScore;

    @OneToMany(mappedBy = "movieInfo", orphanRemoval = true, cascade = {CascadeType.PERSIST})
    private List<MovieCopy> copies;

    @OneToMany(mappedBy = "movieInfo", orphanRemoval = true, cascade = {CascadeType.PERSIST})
    private List<Rank> ranks;

    private LocalDate releaseDate;

    private String filmCompany;

    @Transient
    private Long daysSinceRelease;

    @PostLoad
    public void calculateDaysSinceRelease() {
        this.daysSinceRelease = ChronoUnit.DAYS.between(releaseDate, LocalDate.now());
    }

}
