package dao;

import model.QuestionResults;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IQuestionResultsDao {

    void addQuestionResults(QuestionResults questionResults);

    List<QuestionResults> getAllQuestionResults();
}
