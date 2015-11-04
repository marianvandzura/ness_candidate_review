package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Question Data Transfer Object.
 *
 * Created by Peter.
 */
public class QuestionDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("question")
    private String question;

    @JsonProperty("level")
    private Integer level;

    @JsonProperty("code")
    private String code;

    @JsonProperty("url")
    private String imageUrl;

    @JsonProperty("language")
    private String language;

    @JsonProperty("categories")
    private CategoryDto category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
