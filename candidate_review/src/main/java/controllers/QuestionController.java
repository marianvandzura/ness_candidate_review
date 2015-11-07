package controllers;

import dto.OptionDto;
import dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

        for (int questionId : questionIds) {
            //get all options related to question based on question ID
            List<OptionDto> questionOptions = optionService.findOptionsByQuestionId(questionId);
            //get question
            QuestionDto question = questionService.getQuestionById(questionId);
            //add options to response
            question.setOptions(questionOptions);
            result.add(question);
        }
        return result;
    }

    /**
     * get questions by category
     *
     * @param categoryId
     * @return List of questions related to category
     */
    @RequestMapping(value = "/questions/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDto> getQuestionsByCategoryId(@PathVariable(value = "categoryId") int categoryId) {
        List<QuestionDto> questionsByCategory = questionService.getQuestionsByCategoryId(categoryId);
        //init arrayList size because of performance
        List<QuestionDto> result = new ArrayList<QuestionDto>(questionsByCategory.size());

        for (QuestionDto questionDto : questionsByCategory) {
            //get all options related to question based on question ID
            List<OptionDto> questionOptions = optionService.findOptionsByQuestionId(questionDto.getId());
            //add options to response
            questionDto.setOptions(questionOptions);
            result.add(questionDto);
        }
        return result;
    }

    /**
     * save question and question options
     *
     * @param question
     * @return HTTP response
     */
    @RequestMapping(value = "/question/", method = RequestMethod.POST)
    public ResponseEntity saveQuestion(@RequestBody QuestionDto question) {
        QuestionDto savedQuestion = questionService.addQuestion(question);
        //get question options
        List<OptionDto> questionOptions = question.getOptions();
        if (questionOptions != null && !questionOptions.isEmpty()) {
            //if options exist for question, add all
            for (OptionDto option : questionOptions) {
                option.setQuestion(question);
                optionService.addOption(option);
            }
        }
        return new ResponseEntity<QuestionDto>(savedQuestion, HttpStatus.OK);
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
        QuestionDto updatedQuestion = questionService.addQuestion(question);
        //get question options
        List<OptionDto> questionOptions = question.getOptions();
        if (questionOptions != null && !questionOptions.isEmpty()) {
            //if options exist for question update all
            for (OptionDto option : questionOptions) {
                optionService.addOption(option);
            }
        }
        return new ResponseEntity<QuestionDto>(updatedQuestion, HttpStatus.OK);
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
            //get question options
            List<OptionDto> questionOptions = questionToDelete.getOptions();
            if (questionOptions != null && !questionOptions.isEmpty()) {
                //if options exist for question delete all
                for (OptionDto option : questionOptions) {
                    optionService.deleteOption(option);
                }
            }
            //delete question
            questionService.deleteQuestion(questionToDelete);
        }
        return new ResponseEntity<QuestionDto>(questionToDelete, HttpStatus.OK);
    }

}
