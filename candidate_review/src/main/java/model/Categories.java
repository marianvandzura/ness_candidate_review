package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */

@Entity
@Table(name = "categories")
public class Categories {

    @Id
    @Column(name = "category_id")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private Collection<Questions> questions;

    public Categories() {
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
        Categories category = (Categories) obj;
        return (this.categoryId == category.getCategoryId())
                && (this.categoryName.equals(category.getCategoryName()));
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + categoryId;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        return result;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Collection<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Questions> questions) {
        this.questions = questions;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
