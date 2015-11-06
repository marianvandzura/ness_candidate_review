package controllers;

import dto.CategoryDto;
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


    //get questions with ids
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

    //save question
    @RequestMapping(value = "/question/", method = RequestMethod.POST)
    public ResponseEntity saveQuestion(@RequestBody QuestionDto question) {
        QuestionDto savedQuestion = questionService.addQuestion(question);
        //get question options
        List<OptionDto> questionOptions = question.getOptions();
        if (questionOptions != null && !questionOptions.isEmpty()) {
            //if options exist for question add all
            for (OptionDto option : questionOptions) {
                option.setId(question.getId());
                optionService.addOption(option);
            }
        }
        return new ResponseEntity<QuestionDto>(savedQuestion, HttpStatus.OK);
    }

    //update questions with id
    @RequestMapping(value = "/question/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateQuestion(@PathVariable(value = "id") int questionId, @RequestBody QuestionDto question) {
        //TODO copy values from new question
        //get existing question
        QuestionDto updatedQuestion = questionService.addQuestion(question);
        //get question options
        List<OptionDto> questionOptions = question.getOptions();
        if (questionOptions != null && !questionOptions.isEmpty()) {
            //if options exist for question add all
            for (OptionDto option : questionOptions) {
                optionService.addOption(option);
            }
        }
        return new ResponseEntity<QuestionDto>(updatedQuestion, HttpStatus.OK);
    }

    //delete question with id
    @RequestMapping(value = "/question/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteQuestion(@PathVariable(value = "id") int questionId) {
        //get existing question
        QuestionDto questionToDelete = questionService.getQuestionById(questionId);
        if(questionToDelete != null) {
            //get question options
            List<OptionDto> questionOptions = questionToDelete.getOptions();
            if (questionOptions != null && !questionOptions.isEmpty()) {
                //if options exist for question add all
                for (OptionDto option : questionOptions) {
                    //TODO delete options
                    optionService.addOption(option);
                }
            }
            questionService.deleteQuestion(questionToDelete);
        }
        return new ResponseEntity<QuestionDto>(questionToDelete, HttpStatus.OK);
    }

}
