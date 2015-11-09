package dao;

import model.Categories;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ICategoriesDao {

    /**
     * add category
     * @param category
     * @return added category
     */
    Categories addCategory(Categories category);

    /**
     * update category
     * @param category
     * @return updated category
     */
    Categories updateCategory(Categories category);

    /**
     * delete category
     * @param categoryId
     */
    void deleteCategory(int categoryId);

    /**
     * get all categories
     * @return List of categories
     */
    List<Categories> getAllCategories();

//    /**
//     * Find all categories releated to question.
//     *
//     * @param questionId
//     * @return List of categories.
//     */
//    List<Categories> findCategoriesByQuestion(Integer questionId);

    /**
     * Find Category by id.
     *
     * @param id
     * @return
     */
    Categories findById(Integer id);
}
