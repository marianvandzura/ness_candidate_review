package model;

import javax.persistence.*;

/**
 * Person entity
 * Created by Marian_Vandzura on 26.10.2015.
 */

//@SequenceGenerator(name = "person_id", sequenceName = "person_id")
@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    public Person() {
        //default consctructor
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
