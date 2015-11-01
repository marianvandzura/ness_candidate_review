package service;

import assemblers.QuestionAssembler;
import dao.IQuestionsDao;
import dao.impl.QuestionsDao;
import dto.QuestionDto;
import model.Categories;
import model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Question service.
 *
 * Created by Peter.
 */
@Service
public class QuestionService {

    @Autowired
    IQuestionsDao questionsDao;

    @Autowired
    QuestionAssembler questionAssembler;

    public QuestionDto getById(final Integer id) {
        return questionAssembler.extractDtoFromDomain(questionsDao.findById(id));
    }

    public Questions addQuestion(final QuestionDto quest) {
        return questionsDao.addQuestions(questionAssembler.populateDomainFromDto(quest));
    }
}
