package model;

import javax.persistence.*;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Entity
@Table(name = "question_results")
public class QuestionResults {

    @Id
    @Column(name = "question_result_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionResultId;

    @Column(name = "time")
    private Integer time;
    @Column(name = "google_time")
    private Integer googleTime;

//    @ManyToOne
//    private CandidatesReports candidatesId;

    public QuestionResults() {
        //default
    }

    public Integer getQuestionResultId() {
        return questionResultId;
    }

    public void setQuestionResultId(Integer questionResultId) {
        this.questionResultId = questionResultId;
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
}
