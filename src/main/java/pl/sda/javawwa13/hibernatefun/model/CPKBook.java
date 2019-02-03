package pl.sda.javawwa13.hibernatefun.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class CPKBook {
    @Id
    ISBN id;
    @Column
    String name;
}
