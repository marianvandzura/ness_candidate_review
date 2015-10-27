package model;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Entity
@Table(name = "Settings")
public class Settings {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    public Settings(){
        //default
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
