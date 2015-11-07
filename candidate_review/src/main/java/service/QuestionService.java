package service;

import assemblers.QuestionAssembler;
import dao.IQuestionsDao;
import dto.QuestionDto;
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

    public QuestionService(){
        //default
    }

    /**
     * get question with ID
     * @param id
     * @return QuestionDto object
     */
    public QuestionDto getQuestionById(final Integer id) {
        return questionAssembler.extractDtoFromDomain(questionsDao.findQuestionById(id));
    }

    /**
     * add question to DB
     * @param questionDto
     * @return added QuestionDto object
     */
    public QuestionDto addQuestion(final QuestionDto questionDto) {
        return questionAssembler.extractDtoFromDomain(questionsDao.addQuestion(questionAssembler.populateDomainFromDto(questionDto)));
    }

    /**
     * delete question
     * @param questionDto
     */
    public void deleteQuestion(QuestionDto questionDto){
        questionsDao.deleteQuestion(questionAssembler.populateDomainFromDto(questionDto));
    }

    public QuestionDto updateQuestionDto(QuestionDto question, QuestionDto newQuestion){
        return questionAssembler.updateDto(question, newQuestion);
    }

    /**
     * get List of questions based on category
     * @param categoryId
     * @return List of questions
     */
    public List<QuestionDto> getQuestionsByCategoryId(final Integer categoryId) {
        return questionAssembler.extractDtoListFromDomain(questionsDao.findQuestionsByCategory(categoryId));
    }
}
