package dao;

import model.Categories;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ICategoriesDao {

    void addCategory(Categories categories);

    List<Categories> getAllCategories();
}
