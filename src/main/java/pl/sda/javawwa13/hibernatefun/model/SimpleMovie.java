package pl.sda.javawwa13.hibernatefun.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SimpleMovie {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    public SimpleMovie() {
    }

    public SimpleMovie(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
