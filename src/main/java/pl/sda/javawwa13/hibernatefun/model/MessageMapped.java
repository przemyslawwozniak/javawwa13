package pl.sda.javawwa13.hibernatefun.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class MessageMapped {
    @Id
    @GeneratedValue
    Long id;
    String content;
    @OneToOne
    EmailMapped email;
}