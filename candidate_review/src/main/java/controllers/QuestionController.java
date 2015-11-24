package controllers;

import dto.OptionDto;
import dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CategoryService;
import service.OptionService;
import service.QuestionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle REST requests
 * <p>
 * Created by Marian_Vandzura on 6.11.2015.
 */

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    OptionService optionService;

    @Autowired
    CategoryService categoryService;

    public QuestionController() {
        //default
    }

    /**
     * get questions with array of ids passed as param
     *
     * @param questionIds
     * @return List of requested questions
     */
    @RequestMapping(value = "/questions/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDto> getQuestionsWithIds(@PathVariable(value = "id") List<Integer> questionIds) {
        //init arrayList because of performance
        List<QuestionDto> result = new ArrayList<QuestionDto>(questionIds.size());
        //get all questions
        for (int questionId : questionIds) {
            QuestionDto question = questionService.getQuestionById(questionId);
            if (question != null) {
                result.add(question);
            }
        }
        return result;
    }

    /**
     * get questions with array of ids passed as param
     *
     * @param questionIds
     * @return List of requested questions
     */
    @RequestMapping(value = "/user/questions/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDto> getQuestionsForUserWithIds(@PathVariable(value = "id") List<Integer> questionIds) {
        //init arrayList because of performance
        List<QuestionDto> result = new ArrayList<QuestionDto>(questionIds.size());
        //get all questions
        for (int questionId : questionIds) {
            QuestionDto question = questionService.getQuestionById(questionId);
            if (question != null) {
                result.add(question);
                List<OptionDto> questionOptions = question.getOptions();
                for (OptionDto option : questionOptions) {
                    option.setTruth(null);
                }
            }
        }
        return result;
    }

    /**
     * get questions by category
     *
     * @param categoryId
     * @return List of questions related to category
     */
    @RequestMapping(value = "/questions/category/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDto> getQuestionsByCategoryId(@PathVariable(value = "categoryId") int categoryId) {
        List<QuestionDto> questionsByCategory = questionService.getQuestionsByCategoryId(categoryId);
        return questionsByCategory;
    }

    /**
     * get questions by category
     *
     * @param categoryId
     * @return List of questions related to category
     */
    @RequestMapping(value = "/user/questions/category/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDto> getQuestionsForUserByCategoryId(@PathVariable(value = "categoryId") int categoryId) {
        List<QuestionDto> questionsByCategory = questionService.getQuestionsByCategoryId(categoryId);
        for (QuestionDto question : questionsByCategory) {
            List<OptionDto> questionOptions = question.getOptions();
            for (OptionDto option : questionOptions) {
                option.setTruth(null);
            }
        }
        return questionsByCategory;
    }

    /**
     * save question and question options
     *
     * @param question
     * @return saved question
     */
    @RequestMapping(value = "/question/", method = RequestMethod.POST)
    public ResponseEntity saveQuestion(@RequestBody final QuestionDto question) {
        QuestionDto savedQuestion = questionService.addQuestion(question);
        return new ResponseEntity<>(savedQuestion, HttpStatus.OK);
    }

    /**
     * update question and related options
     *
     * @param question
     * @return HTTP response
     */
    @RequestMapping(value = "/question/", method = RequestMethod.PUT)
    public ResponseEntity updateQuestion(@RequestBody QuestionDto question) {
        //update question
        QuestionDto questionToUpdate = questionService.getQuestionById(question.getId());
        questionToUpdate = questionService.updateQuestionDto(questionToUpdate, question);
        QuestionDto updatedQuestion = questionService.updateQuestion(questionToUpdate);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }

    /**
     * delete question with passed id and related options
     *
     * @param questionId
     * @return HTTP response
     */
    @RequestMapping(value = "/question/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteQuestion(@PathVariable(value = "id") int questionId) {
        //get existing question
        QuestionDto questionToDelete = questionService.getQuestionById(questionId);
        if (questionToDelete != null) {
            //delete question
            questionService.deleteQuestion(questionToDelete);
            return new ResponseEntity("Question deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity("Question NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

}
