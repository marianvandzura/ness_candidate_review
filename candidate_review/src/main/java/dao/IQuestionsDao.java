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
     * Update question
     * @param question
     * @return updated question
     */
    Questions updateQuestion(Questions question);

    /**
     * delete question
     * @param questionId
     */
    void deleteQuestion(int questionId);

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
