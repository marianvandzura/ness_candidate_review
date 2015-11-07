package controllers;

import assemblers.QuestionAssembler;
import model.Options;
import model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.OptionService;
import service.QuestionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 4.11.2015.
 */
@Controller
public class OptionController {

    public OptionController(){
        //default
    }

    @Autowired
    OptionService optionService;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionAssembler questionAssembler;
    //TODO

//    @RequestMapping(value = "/option/{questId}" , method = RequestMethod.GET)
//    public @ResponseBody
//    List<Options> createQuestionWithOptions( @PathVariable(value = "questId") Integer questId) {
//
//        List<Options> options = new ArrayList<Options>();
//        Options option = new Options();
//        Options option2 = new Options();
//        Questions question = new Questions();
//        question.setQuestionId(questId);
//        option.setQuestion(question);
//        option.setOption("lebo som dobry>" + questId);
//        option.setTruth(true);
//        question.setQuestionId(questId);
//        option2.setQuestion(question);
//        option2.setOption("lebo som zly>" + questId);
//        option2.setTruth(false);
//
//        options.add(optionService.addOption(option));
//        options.add(optionService.addOption(option2));
//
//        return options;
//    }
//
//    @RequestMapping(value = "/option/find/{questId}" , method = RequestMethod.GET)
//    public @ResponseBody
//    List<Options> findByQuestion (@PathVariable(value = "questId") Integer questId) {
//
//        return optionService.findByQuestion(questId);
//    }
}
