package controllers;

import dao.IQuestionsDao;
import dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.OptionService;
import service.TestService;

import java.util.List;

/**
 * Created by Lubomir on 11/3/2015.
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
     * Get all visible tests
     *
     * @return all tests
     */
    @RequestMapping(value = "/tests", method = RequestMethod.GET)
    @ResponseBody
    public List<TestDto> getTests() {
        return testService.getTests();
    }

    /**
     * Get test by userId, the one who owns them
     *
     * @param userId user id
     * @return my tests
     */
    @RequestMapping(value = "/admin/mytests/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<TestDto> getMyTests(@PathVariable(value = "id") Integer userId) {
        return testService.getMyTests(userId);
    }

    /**
     * Return test by id without options answers
     *
     * @param id id of a test
     * @return
     */
    @RequestMapping(value = "/user/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TestDto getTestForUser(@PathVariable(value = "id") Integer id) {
        TestDto testDto = testService.getTestByIdForUser(id);
        return testDto;
    }


    /**
     * Should save test
     *
     * @param Dto of new test thats to be saved
     * @return ResponseEntity
     */
    @RequestMapping(value = "/admin/test", method = RequestMethod.POST)
    public ResponseEntity<TestDto> save(@RequestBody TestDto Dto) {
        TestDto testDto = testService.saveOrUpdateTest(Dto);
        return new ResponseEntity<>(testDto, HttpStatus.OK);

    }

    /**
     * Update existing test
     *
     * @param newTestDto updates Dto of test
     * @return ResponseEntity
     */
    @RequestMapping(value = "/admin/test", method = RequestMethod.PUT)
    public ResponseEntity<TestDto> edit(@RequestBody TestDto newTestDto) {
        TestDto updatedTest = testService.saveOrUpdateTest(newTestDto);
        return new ResponseEntity<>(updatedTest, HttpStatus.OK);
    }


    /**
     * Method will delete test from database, if exists.
     *
     * @param id id of test
     * @return ResponseEntity
     */
    @RequestMapping(value = "/admin/test/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TestDto> deleteTest(@PathVariable(value = "id") Integer id) {
        TestDto deletedTest = testService.deleteTest(id);
        return new ResponseEntity<>(deletedTest, HttpStatus.OK);
    }
}




