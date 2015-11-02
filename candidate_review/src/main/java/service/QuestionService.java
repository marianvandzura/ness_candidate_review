package service;

import assemblers.QuestionAssembler;
import dao.IQuestionsDao;
import dto.QuestionDto;
import model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public QuestionDto getQuestionById(final Integer id) {
        return questionAssembler.extractDtoFromDomain(questionsDao.findQuestionById(id));
    }

    public Questions addQuestion(final QuestionDto questionDto) {
        return questionsDao.addQuestion(questionAssembler.populateDomainFromDto(questionDto));
    }

    public List<QuestionDto> findQuesionsByCategory (final Integer categoryId) {
        return questionAssembler.extractDtosListFromDomain(questionsDao.findQuestionsByCategory(categoryId));
    }
}
