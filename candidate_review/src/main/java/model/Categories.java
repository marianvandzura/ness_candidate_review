package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */

@Entity
@Table(name = "categories")
public class Categories {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private Set<Questions> questions = new HashSet<Questions>(0);

    public Categories() {
        //default
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Questions> questions) {
        this.questions = questions;
    }

}
