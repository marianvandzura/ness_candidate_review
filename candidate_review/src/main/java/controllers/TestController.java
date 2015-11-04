package controllers;

import dao.IQuestionsDao;
import model.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.TestService;

/**
 * Created by Peter on 4.11.2015.
 */
@Controller
public class TestController {

    @Autowired
    TestService testServie;

    @Autowired
    IQuestionsDao questionsDao;

    @RequestMapping(value = "/test" , method = RequestMethod.GET)
    public @ResponseBody
    Tests saveTest() {
        Tests test = new Tests();
        test.setQuestions(questionsDao.getAllQuestions());
        test.setInfo("more jak fajny test !");
        test.setPosition("Misionary position");
        return testServie.save(test);
    }


}
