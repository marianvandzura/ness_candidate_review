package dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import model.Questions;

import java.util.List;
import java.util.Set;

/**
 * Category Data Transfer Object.
 *
 * Created by Peter.
 */
public class CategoryDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("questions")
    private List<QuestionDto> questions;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
