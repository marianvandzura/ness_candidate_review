package controllers;

import dao.IQuestionsDao;
import dto.QuestionDto;
import dto.TestDto;
import model.Tests;
import org.bouncycastle.util.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
              //TODO put options to questions, option controller
        }
        return testDto;
    }


    /**
     * Should save test
     * @param Dto of new test thats to be saved
     * @return ResponseEntity
     */
    @RequestMapping(value = "/savetest", method = RequestMethod.POST)
    public ResponseEntity<TestDto> save(@RequestBody TestDto Dto) {
        TestDto testDto  = testService.saveTest(Dto);
        return new ResponseEntity<TestDto>(testDto, HttpStatus.OK);
    }

    /**
     * Update existing test
     * @param newTestDto updates Dto of test
     * @return ResponseEntity
     */
    @RequestMapping(value = "/edittest", method = RequestMethod.PUT)
    public ResponseEntity<TestDto> edit(@RequestBody TestDto newTestDto) {
        TestDto updatedTest = testService.editTest(newTestDto);
        return new ResponseEntity<TestDto>(updatedTest, HttpStatus.OK);
    }


    /**
     * Method will delete test in database, if exists.
     * @param testDto
     * @return
     */
    @RequestMapping(value = "/deletetest", method = RequestMethod.DELETE)
    public ResponseEntity<TestDto> deleteTets(@RequestBody TestDto testDto) {
        TestDto deletedTest = testService.deleteTest(testDto);
        return new ResponseEntity<TestDto>(deletedTest, HttpStatus.OK);
    }
}




