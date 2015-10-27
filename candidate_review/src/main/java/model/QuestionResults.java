package model;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Entity
public class QuestionResults {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private Integer time;
    private Integer googleTime;

//    @ManyToOne
//    private Candidates candidatesId;

    public QuestionResults(){
        //default
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getGoogleTime() {
        return googleTime;
    }

    public void setGoogleTime(Integer googleTime) {
        this.googleTime = googleTime;
    }

//    public Candidates getCandidatesId() {
//        return candidatesId;
//    }
//
//    public void setCandidatesId(Candidates candidatesId) {
//        this.candidatesId = candidatesId;
//    }
}