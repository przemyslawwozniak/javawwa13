package pl.sda.javawwa13.hibernatefun.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    @OneToMany(mappedBy = "customer")
    private List<Rank> ranks;

    @OneToMany(mappedBy = "customer")
    private List<Rent> rents;

}
