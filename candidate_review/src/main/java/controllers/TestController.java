package controllers;

import dao.IQuestionsDao;
import dto.OptionDto;
import dto.QuestionDto;
import dto.TestDto;
import model.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import service.OptionService;
import service.TestService;

import java.util.ArrayList;
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

    @Autowired
    OptionService optionService;

    /**
     * get all visible tests
     *
     * @return
     */

    @RequestMapping(value = "/gettests", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TestDto> getTests() {
        List<TestDto> testDtos = new ArrayList<TestDto>();
        for (TestDto testDto : testService.getTests()) {
            if (testDto.getVisible()) {
                testDto.setQuestions(null);
                testDto.setUserId(null);
                testDtos.add(testDto);
            }
        }
        return testService.getTests();
    }

    @RequestMapping(value = "/getmytests/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TestDto> getMyTests(@PathVariable(value = "id") Integer userId) {
        List<TestDto> testDtos = new ArrayList<TestDto>();
        for (TestDto testDto : testService.getTests()) {
            if (testDto.getVisible() && (testDto.getUserId() == userId)) {
                testDto.setQuestions(null);
                testDto.setUserId(null);
                testDtos.add(testDto);
            }
        }
        return testService.getTests();
    }

    /**
     * Return test by id
     * @param id id of a test
     * @return
     */
    @RequestMapping(value = "/gettest/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    TestDto getTest(@PathVariable(value = "id") Integer id) {
        TestDto testDto = testService.getTestById(id);
        List<QuestionDto> ListOfQuestDto = new ArrayList<QuestionDto>();
        for(QuestionDto questDto : testDto.getQuestions())
        {
              //TODO   put options to questions
        }
        return testDto;
    }

    @RequestMapping(value = "/edittest", method = RequestMethod.PUT)
    public ResponseStatus update(@RequestBody TestDto testDto) {
        Tests domain = testService.saveTest(testDto);
        return domain;
    }

    @RequestMapping(value = "/deletetest", method = RequestMethod.DELETE)
    public Tests deleteTets(@RequestBody TestDto testDto) {
        Tests domain = testService.deleteTest(testDto);
        return domain;

    }


}


//        editTest
//        deleteTest
//        getTest



