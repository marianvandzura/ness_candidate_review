package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.Questions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lubomir on 11/3/2015.
 */
public class TestDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("user")
    private UserDto user;

    @JsonProperty("info")
    private String info;

    @JsonProperty("position")
    private String position;

    @JsonProperty("visible")
    private Boolean visible;

    @JsonProperty("questions")
    private List<QuestionDto> questions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto userid) {
        this.user = userid;
    }
}