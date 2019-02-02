package pl.sda.javawwa13.hibernatefun.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Email {
    @Id
    //this is how you can change annotation config - simple, isn't it?
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String subject;
    @OneToOne//(mappedBy = "email") //IMPORTANT!
    Message message;
}
