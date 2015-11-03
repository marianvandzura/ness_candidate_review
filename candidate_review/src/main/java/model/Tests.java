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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer testId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users userId;

    @Column(name = "visible")
    private Boolean visible;
    @Column(name = "position")
    private String position;
    @Column(name = "info")
    private String info;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private Collection<Questions> questions;

    public Tests(){
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
        Tests test = (Tests) obj;
        return (this.testId == test.getTestId())
                && (this.info.equals(test.getInfo()))
                && (this.position.equals(test.getPosition()));
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + testId;
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
}
