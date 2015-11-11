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
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;

    @Column(name = "type")
    private String type;
    @Column(name = "question")
    private String question;
    @Column(name = "level")
    private Integer level;
    @Column(name = "code")
    private String code;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "language")
    private String language;

    @ManyToOne//(cascade = CascadeType.ALL)//(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")//, nullable = false)
    private Categories category;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Options> options;


    public Questions() {
        //default
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Collection<Options> getOptions() {
        return options;
    }

    public void setOptions(Collection<Options> options) {
        this.options = options;
    }

}
