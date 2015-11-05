package controllers;

import dto.CategoryDto;
import dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.QuestionService;

import java.util.List;

/**
 * Created by Peter on 31.10.2015.
 */

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    public QuestionController() {
        //default
    }

    //TODO GET? to save question?
    @RequestMapping(value = "/{txt}/{lng}/{ctg}", method = RequestMethod.GET)
    @ResponseBody
    public QuestionDto saveQuestion(@PathVariable(value = "txt") String paramQuestionText,
                                    @PathVariable(value = "lng") String paramLanguage,
                                    @PathVariable(value = "ctg") Integer paramCategoryId) {

        //TODO ?
        QuestionDto question = new QuestionDto();
        CategoryDto category = new CategoryDto();
        category.setId(paramCategoryId);
        question.setCategory(category);
        question.setCode("code");
        question.setLevel(2);
        question.setLanguage(paramLanguage);
        question.setQuestion(paramQuestionText);
        question.setType("checkbox");

        return questionService.addQuestion(question);
    }

    @RequestMapping(value = "/{ctg}", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDto> findByCategory(@PathVariable(value = "ctg") Integer categoryId) {
        return questionService.getQuestionsByCategoryId(categoryId);
    }
}
