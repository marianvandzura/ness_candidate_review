package service;

import assemblers.OptionAssembler;
import assemblers.QuestionAssembler;
import dao.IQuestionsDao;
import dto.OptionDto;
import dto.QuestionDto;
import model.Options;
import model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Question service.
 * <p>
 * Created by Marian_Vandzura on 7.11.2015.
 */
@Service
public class QuestionService {

    @Autowired
    IQuestionsDao questionsDao;

    @Autowired
    QuestionAssembler questionAssembler;

    @Autowired
    OptionService optionService;

    @Autowired
    OptionAssembler optionAssembler;

    public QuestionService() {
        //default
    }

    /**
     * get question with ID
     *
     * @param id
     * @return QuestionDto object
     */
    public QuestionDto getQuestionById(final Integer id) {
        Questions questions = null;
        if ((questions = questionsDao.findQuestionById(id)) != null) {
            return questionAssembler.extractDtoFromDomain(questions);
        }
        return null;
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
     * update question
     *
     * @param questionDto
     * @return updated question
     */
    public QuestionDto updateQuestion(final QuestionDto questionDto) {
        List<OptionDto> questionOptions = questionDto.getOptions();
        //new added question has id null
        int questionId = questionDto.getId() != null ? questionDto.getId() : -1;
        Questions question = questionAssembler.populateDomainFromDto(questionDto);
        if(questionId != -1) {
            question.setQuestionId(questionId);
        }
        Collection<Options> options = question.getOptions();
        int i = 0;
        for (Options singleOption : options) {
            OptionDto optionDto = questionOptions.get(i++);
            //new added option has id null
            int optionId = (optionDto == null || optionDto.getId() == null) ? -1 : optionDto.getId();

            if (optionId != -1) {
                singleOption.setOptionId(optionId);
            }
        }
        return questionAssembler.extractDtoFromDomain(questionsDao.addQuestion(question));
    }

    /**
     * delete question
     *
     * @param questionDto
     */
    public void deleteQuestion(final QuestionDto questionDto) {
        questionsDao.deleteQuestion(questionDto.getId());
    }

    /**
     * update questionDto
     *
     * @param question
     * @param newQuestion
     * @return
     */
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
        List<Questions> questions = questionsDao.findQuestionsByCategory(categoryId);
        if (questions != null) {
            return questionAssembler.extractDtoListFromDomain(questions);
        } else {
            return null;
        }
    }
}
