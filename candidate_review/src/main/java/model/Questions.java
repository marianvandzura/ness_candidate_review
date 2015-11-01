package model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Entity
@Table(name = "questions")
public class Questions {
    @Id
    @Column(name="question_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="type")
    private Integer type;
    @Column(name="question")
    private String question;
    @Column(name="level")
    private Integer level;
    @Column(name="code")
    private String code;
    @Column(name="image_url")
    private String imageUrl;
    @Column(name="language")
    private String language;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "categories_questions", joinColumns = {
            @JoinColumn(name = "question_id", referencedColumnName="question_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "category_id",referencedColumnName="category_id",
                    nullable = false) })
    private Collection<Categories> categories;

    public Questions() {
        //default
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Collection<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Categories> categories) {
        this.categories = categories;
    }
}
