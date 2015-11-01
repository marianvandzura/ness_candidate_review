package dao;

import model.Questions;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface IQuestionsDao {

    void addQuestion(Questions question);

    List<Questions> getAllQuestions();
}
