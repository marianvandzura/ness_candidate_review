package dao;

import model.QuestionResults;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IQuestionResultsDao {

    void addQuestionResult(QuestionResults questionResult);

    List<QuestionResults> getAllQuestionResults();
}
