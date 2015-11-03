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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass() || !(obj instanceof Questions)) {
            return false;
        }
        QuestionResults questionResult = (QuestionResults) obj;
        return (this.questionResultId == questionResult.getQuestionResultId())
                && (this.time == questionResult.getTime())
                && (this.googleTime == questionResult.getGoogleTime());
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + questionResultId;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (googleTime != null ? googleTime.hashCode() : 0);
        return result;
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
