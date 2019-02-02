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
public class MovieCopy {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private MovieInfo movieInfo;

    @OneToMany(mappedBy = "movieCopy")
    private List<Rent> rents;

}
