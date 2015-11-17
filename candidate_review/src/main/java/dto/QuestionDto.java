package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.util.List;

/**
 * Question Data Transfer Object.
 *
 * Created by Peter.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("type")
    private String type;

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



    @JsonProperty("options")
    private List<OptionDto> options;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }


}
