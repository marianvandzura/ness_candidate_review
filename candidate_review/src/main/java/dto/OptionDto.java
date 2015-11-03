package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Option Data Transfer Object
 * <p>
 * Created by Marian_Vandzura on 3.11.2015.
 */
public class OptionDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("option")
    private String option;

    @JsonProperty("truth")
    private Boolean truth;

    @JsonProperty("questionId")
    private Integer questionId;

    public OptionDto() {
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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}
