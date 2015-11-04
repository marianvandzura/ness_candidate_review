package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
<<<<<<< HEAD
 * Option Data Transfer Object
 * <p>
 * Created by Marian_Vandzura on 3.11.2015.
=======
 * Created by Peter on 4.11.2015.
>>>>>>> refs/remotes/origin/master
 */
public class OptionDto {

    @JsonProperty("id")
    private Integer id;

<<<<<<< HEAD
=======
    @JsonProperty("question")
    private QuestionDto question;

>>>>>>> refs/remotes/origin/master
    @JsonProperty("option")
    private String option;

    @JsonProperty("truth")
    private Boolean truth;

<<<<<<< HEAD
    @JsonProperty("questionId")
    private Integer questionId;

    public OptionDto() {
        //default
    }

=======
>>>>>>> refs/remotes/origin/master
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

<<<<<<< HEAD
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
=======
    public QuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDto question) {
        this.question = question;
>>>>>>> refs/remotes/origin/master
    }
}
