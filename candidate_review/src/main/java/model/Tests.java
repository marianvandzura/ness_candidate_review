package model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Entity
@Table(name = "tests")
public class Tests {
    @Id
    @Column(name = "test_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer testId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "name")
    private String name;
    @Column(name = "visible")
    private Boolean visible;
    @Column(name = "position")
    private String position;
    @Column(name = "info")
    private String info;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "test_questions",
            joinColumns = @JoinColumn(name = "test_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "question_id", nullable = false))
    private Collection<Questions> questions;

    public Tests() {
        //default
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users userId) {
        this.user = userId;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Collection<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Questions> questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
