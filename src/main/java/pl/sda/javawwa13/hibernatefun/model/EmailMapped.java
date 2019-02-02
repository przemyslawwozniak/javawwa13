package pl.sda.javawwa13.hibernatefun.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class EmailMapped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String subject;
    @OneToOne(mappedBy = "email") //the only change we introduced
    MessageMapped message;
}