package pl.sda.javawwa13.hibernatefun.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
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

}
