package model;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */

@Entity
@Table(name = "Options")
public class Options {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="option")
    private String option;
    @Column(name="truth")
    private Boolean truth;

    @ManyToOne
    @JoinColumn(name = "id")
    private Questions questionsId;

    public Options(){
        //default
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Boolean getTruth() {
        return truth;
    }

    public void setTruth(Boolean truth) {
        this.truth = truth;
    }

    public Questions getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(Questions questionsId) {
        this.questionsId = questionsId;
    }
}
