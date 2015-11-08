package service;

import assemblers.QuestionAssembler;
import dao.IQuestionsDao;
import dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Question service.
 * <p>
 * Created by Peter.
 */
@Service
public class QuestionService {

    @Autowired
    IQuestionsDao questionsDao;

    @Autowired
    QuestionAssembler questionAssembler;

    public QuestionService() {
        //default
    }

//    /**
//     * get all available questions
//     * @return List of questions
//     */
//    public List<QuestionDto> getAllQuestions(){
//        return questionAssembler.extractDtoListFromDomain(questionsDao.getAllQuestions());
//    }

    /**
     * get question with ID
     *
     * @param id
     * @return QuestionDto object
     */
    public QuestionDto getQuestionById(final Integer id) {
        return questionAssembler.extractDtoFromDomain(questionsDao.findQuestionById(id));
    }

    /**
     * add question to DB
     *
     * @param questionDto
     * @return added QuestionDto object
     */
    public QuestionDto addQuestion(final QuestionDto questionDto) {
        return questionAssembler.extractDtoFromDomain(questionsDao.addQuestion(questionAssembler.populateDomainFromDto(questionDto)));
    }

    /**
     * delete question
     *
     * @param questionDto
     */
    public void deleteQuestion(final QuestionDto questionDto) {
        questionsDao.deleteQuestion(questionAssembler.populateDomainFromDto(questionDto));
    }

    public QuestionDto updateQuestionDto(final QuestionDto question, final QuestionDto newQuestion) {
        return questionAssembler.updateDto(question, newQuestion);
    }

    /**
     * get List of questions based on category
     *
     * @param categoryId
     * @return List of questions
     */
    public List<QuestionDto> getQuestionsByCategoryId(final Integer categoryId) {
        return questionAssembler.extractDtoListFromDomain(questionsDao.findQuestionsByCategory(categoryId));
    }
}
