package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Peter on 4.11.2015.
 */
public class OptionDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("question")
    @JsonIgnore
    private QuestionDto question;

    @JsonProperty("option")
    private String option;

    @JsonProperty("truth")
    private Boolean truth;

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

    public QuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDto question) {
        this.question = question;
    }
}
