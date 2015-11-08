package dao;

import model.Categories;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ICategoriesDao {

    /**
     * add category
     * @param categories
     * @return added category
     */
    Categories addCategory(Categories categories);

    /**
     * delete category
     * @param category
     */
    void deleteCategory(Categories category);

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
