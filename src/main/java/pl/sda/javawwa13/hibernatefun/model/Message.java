package pl.sda.javawwa13.hibernatefun.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue
    Long id;
    String content;
    @OneToOne
    Email email;
}
