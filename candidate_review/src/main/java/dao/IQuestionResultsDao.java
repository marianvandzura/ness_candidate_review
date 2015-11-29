package dao;

import model.QuestionResults;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IQuestionResultsDao {

    /**
     * add question result
     * @param questionResult
     */
    void addQuestionResult(QuestionResults questionResult);

    /**
     * get all question results
     * @return list of QuestionResults objects
     */
    List<QuestionResults> getAllQuestionResults();
}
