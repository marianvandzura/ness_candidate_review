package dao;

import model.Categories;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ICategoriesDao {

    Categories addCategory(Categories categories);

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
