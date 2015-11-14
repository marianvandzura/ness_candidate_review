package service;

import assemblers.QuestionAssembler;
import assemblers.TestAssembler;
import dao.IQuestionsDao;
import dao.ITestsDao;
import dto.ListTestDto;
import dto.QuestionDto;
import dto.TestDto;
import model.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 4.11.2015.
 */

@Service
public class TestService {
    @Autowired
    ITestsDao testsDao;

    @Autowired
    TestAssembler testsAssembler;

    @Autowired
    QuestionAssembler questionAssembler;

    @Autowired
    IQuestionsDao questionsDao;

    @Autowired
    QuestionService questionService;


    /**
     * Return list of all tests
     *
     * @return list of dtos
     */
    public List<ListTestDto> getTests() {
        return this.testsAssembler.extractListTestDtoFromDomain(testsDao.getAllTests());
    }

    /**
     * Return list of all tests
     *
     * @return list of dtos
     */
    public List<ListTestDto> getMyTests(Integer id) {
        return this.testsAssembler.extractListTestDtoFromDomain(testsDao.getTestsByUserId(id));
    }

    /**
     * Returns test by id
     *
     * @param id of test
     * @return
     */
    public TestDto getTestById(Integer id) {
        return testsAssembler.extractDtoFromDomain(testsDao.findById(id));
    }

    /**
     * allegendly it save test
     *
     * @param testDto test to be saved
     * @return saved test or null
     */
    public TestDto saveTest(TestDto testDto) {
        TestDto testToSave;
        List<QuestionDto> listOfQuestions = new ArrayList<QuestionDto>();
        for (QuestionDto questDto : testDto.getQuestions()) {
            if (questDto.getId() != null) {
                listOfQuestions.add(questionService.updateQuestion(questDto));
            } else {
                listOfQuestions.add(questionService.addQuestion(questDto));
            }
        }
        testDto.setQuestions(listOfQuestions);
        testToSave = testsAssembler.extractDtoFromDomain(testsDao.updateTest(testsAssembler.populateDtoFromDomain(testDto)));
        testToSave.setQuestions(listOfQuestions);
        return testToSave;
    }

    /**
     * Edit and update an existing test
     *
     * @param testDto updated test
     * @return updated test or not
     */
    public TestDto updateTest(TestDto testDto) {
        List<QuestionDto> listOfQuestions = new ArrayList<QuestionDto>();
        for (QuestionDto questDto : testDto.getQuestions()) {
            if (questDto.getId() != null) {
                listOfQuestions.add(questionService.updateQuestion(questDto));
            } else {
                listOfQuestions.add(questionService.addQuestion(questDto));
            }
        }
        return testsAssembler.extractDtoFromDomain(testsDao.updateTest(testsAssembler.populateDtoFromDomain(testDto)));
    }

    /**
     * Deletes test
     *
     * @param id id of test
     * @return updated test or not
     */
    public TestDto deleteTest(Integer id) {
        return testsAssembler.extractDtoFromDomain(testsDao.deleteTest(testsAssembler.populateDtoFromDomain(getTestById(id))));
    }
}
