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

    @RequestMapping(value = "/{txt}/{lng}/{ctg}" , method = RequestMethod.GET)
    public @ResponseBody
    QuestionDto saveQuestion(@PathVariable(value = "txt") String txt,@PathVariable(value = "lng") String lng,
                             @PathVariable(value = "ctg") Integer ctg) {
        QuestionDto question = new QuestionDto();
        CategoryDto category = new CategoryDto();
        category.setId(ctg);

        question.setCategory(category);
        question.setCode("code");
        question.setLevel(2);
        question.setLanguage(lng);
        question.setQuestion(txt);
        question.setType("checkbox");

        return questionService.addQuestion(question);
    }

    @RequestMapping(value = "/{ctg}" , method = RequestMethod.GET)
    public @ResponseBody
    List<QuestionDto> findByCategory (@PathVariable(value = "ctg") Integer ctg) {

        return questionService.findQuesionsByCategory(ctg);
    }
}
