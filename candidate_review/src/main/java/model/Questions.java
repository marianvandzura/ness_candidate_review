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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer questionId;

    @Column(name = "type")
    private Integer type;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "categories_questions", joinColumns = {
            @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "category_id",
                    nullable = false)})
    private Collection<Categories> categories;

    @ManyToMany(mappedBy = "questions", fetch = FetchType.LAZY)
    private Collection<Tests> tests;

    public Questions() {
        //default
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass() || !(obj instanceof Questions)) {
            return false;
        }
        Questions question = (Questions) obj;
        return (this.questionId == question.getQuestionId())
                && (this.type == question.getType())
                && (this.question.equals(question.getQuestion()))
                && (this.level == question.getLevel())
                && (this.code.equals(question.code));
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + questionId;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
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

    public Collection<Tests> getTests() {
        return tests;
    }

    public void setTests(Collection<Tests> tests) {
        this.tests = tests;
    }
}
