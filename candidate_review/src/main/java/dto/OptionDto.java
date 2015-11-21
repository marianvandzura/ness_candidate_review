package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.Transient;

/**
 * Created by Peter on 4.11.2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("option")
    private String option;

    @JsonProperty("truly")
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

}
