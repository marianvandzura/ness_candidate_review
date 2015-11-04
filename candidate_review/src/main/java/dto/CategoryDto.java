package dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import model.Questions;

import java.io.Serializable;
import java.util.Set;

/**
 * Category Data Transfer Object.
 *
 * Created by Peter.
 */
public class CategoryDto implements Serializable{

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("category_name")
    private String categoryName;

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

}
