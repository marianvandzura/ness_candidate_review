package dao;

import model.Questions;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IQuestionsDao {

    /**
     * Add question to DB
     * @param question
     * @return added question
     */
    Questions addQuestion(Questions question);

    /**
     * delete question
     * @param question
     */
    void deleteQuestion(Questions question);

    /**
     * Add multiple questions to DB
     * @return List of added questions
     */
    List<Questions> getAllQuestions();

    /**
     * Find question in DB based on questionId
     * @param id ID of question
     * @return Questions object
     */
    Questions findQuestionById(Integer id);

    /**
     * Find all questions in DB based on question category
     * @param categoryId ID of question category
     * @return List of questions with matched category
     */
    List<Questions> findQuestionsByCategory(Integer categoryId);
}
