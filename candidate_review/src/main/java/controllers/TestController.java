package controllers;

import dao.IQuestionsDao;
import dto.TestDto;
import model.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.TestService;

import java.util.List;

/**
 * Created by Peter on 4.11.2015.
 */
@Controller
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    IQuestionsDao questionsDao;

    @RequestMapping(value = "/test" , method = RequestMethod.GET)
    public @ResponseBody
    Tests saveTest() {
        Tests test = new Tests();
        test.setQuestions(questionsDao.getAllQuestions());
        test.setInfo("more jak fajny test !");
        test.setPosition("Misionary position");
        return testService.save(test);
    }

    @RequestMapping(value = "/getTests" , method = RequestMethod.GET)
    public @ResponseBody
    List<TestDto> getTests() {
        return testService.getTests();
    }

    @RequestMapping(value = "/getMyTests" , method = RequestMethod.GET)
    public @ResponseBody
    List<TestDto> getMyTests() {
        return testService.getMyTests();
    }

    @RequestMapping(value = "/getTest/{id}" , method = RequestMethod.GET)
    public @ResponseBody
    TestDto getTest(@PathVariable(value = "id") Integer id) {
        return testService.getTest(id);
    }
}






