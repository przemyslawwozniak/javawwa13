package pl.sda.javawwa13.hibernatefun.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ISBN implements Serializable {
    @Column(name="group_number")    //"group" jest słowem kluczowym SQL, stąd zmieniam domyślną nazwę kolumny
    int group;
    int publisher;
    int title;
    int checkdigit;
}
