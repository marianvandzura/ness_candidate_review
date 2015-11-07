package controllers;

import dao.IQuestionsDao;
import dto.TestDto;
import model.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value = "/gettests", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TestDto> getTests() {
        return testService.getTests();
    }

    @RequestMapping(value = "/getmytests/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TestDto> getMyTests(@PathVariable(value = "id") Integer userId) {
        return testService.getMyTests(userId);
    }

    @RequestMapping(value = "/gettest/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    TestDto getTest(@PathVariable(value = "id") Integer id) {
        return testService.getTestById(id);
    }

    @RequestMapping(value ="/edittest", method = RequestMethod.PUT)
    public Tests update(@RequestBody TestDto testDto) {
        Tests domain = testService.saveTest(testDto);
        return domain;
    }

    @RequestMapping(value ="/deletetest", method = RequestMethod.DELETE)
    public Tests deleteTets(@RequestBody TestDto testDto) {
        Tests domain = testService.deleteTest(testDto);
        return domain;

    }


}




//        editTest
//        deleteTest
//        getTest



