package controllers;

import dto.CategoryDto;
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
    @RequestMapping(value = "/questions/category/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDto> getQuestionsByCategoryId(@PathVariable(value = "categoryId") int categoryId) {
        List<QuestionDto> questionsByCategory = questionService.getQuestionsByCategoryId(categoryId);
        //init arrayList size because of performance
//        List<QuestionDto> result = new ArrayList<QuestionDto>(questionsByCategory.size());
//
//        for (QuestionDto questionDto : questionsByCategory) {
//            //get all options related to question based on question ID
//            List<OptionDto> questionOptions = optionService.findOptionsByQuestionId(questionDto.getId());
//            //add options to response
//            questionDto.setOptions(questionOptions);
//            result.add(questionDto);
//        }
        return questionsByCategory;
    }

    /**
     * save question and question options
     *
     * @param question
     * @return HTTP response
     */
    @RequestMapping(value = "/question/", method = RequestMethod.POST)
    public ResponseEntity saveQuestion(@RequestBody final QuestionDto question) {
        QuestionDto savedQuestion = questionService.addQuestion(question);
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
        QuestionDto questionToUpdate = questionService.getQuestionById(question.getId());
        //category changed
        CategoryDto category;
        int questionCategoryId = question.getCategoryId();
        int questionToUpdateCategoryId = questionToUpdate.getCategoryId();
        if (questionCategoryId != questionToUpdateCategoryId) {
            category = categoryService.getCategoryById(questionCategoryId);
            questionToUpdate.setCategoryId(question.getCategoryId());
        } else {
            category = categoryService.getCategoryById(questionToUpdateCategoryId);
        }
        questionToUpdate.setCategory(category);
        questionToUpdate = questionService.updateQuestionDto(questionToUpdate, question);
        questionService.updateQuestion(questionToUpdate);
        //get question options
        List<OptionDto> questionOptions = question.getOptions();
        if (questionOptions != null && !questionOptions.isEmpty()) {
            //if options exist for question update all
            for (OptionDto option : questionOptions) {
                if (optionService.findOptionById(option.getId()) == null) {
                    //new option added
                    option.setQuestion(questionToUpdate);
                    optionService.addOption(option);
                } else {
                    OptionDto optionToUpdate = optionService.findOptionById(option.getId());
                    option.setQuestion(questionToUpdate);
                    option = optionService.updateOptionDto(optionToUpdate, option);
                    optionService.updateOption(option);
                }
            }
        }
        return new ResponseEntity<QuestionDto>(questionToUpdate, HttpStatus.OK);
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
            //TODO options not found
            List<OptionDto> questionOptions = optionService.findOptionsByQuestionId(questionId);
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
