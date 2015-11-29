package service;

import assemblers.QuestionAssembler;
import assemblers.TestAssembler;
import dao.IQuestionsDao;
import dao.ITestsDao;
import dto.OptionDto;
import dto.QuestionDto;
import dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lubomir on 11/3/2015.
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
    public List<TestDto> getTests() {
        List<TestDto> listDto = new ArrayList<TestDto>();
        for (TestDto tDto : this.testsAssembler.extractListTestDtoFromDomain(testsDao.getAllTests())) {
            if (tDto.getVisible()) {
                tDto.setVisible(null);
                listDto.add(tDto);
            }
        }
        return listDto;
    }

    /**
     * Return list of all tests
     *
     * @return list of dtos
     */
    public List<TestDto> getMyTests(Integer id) {
        List<TestDto> listDto = new ArrayList<TestDto>();
        for (TestDto tDto : this.testsAssembler.extractListTestDtoFromDomain(testsDao.getTestsByUserId(id))) {
            listDto.add(tDto);
        }
        return listDto;
    }

    /**
     * Returns test by id without option answer
     *
     * @param id of test
     * @return
     */
    public TestDto getTestByIdForUser(Integer id) {
        TestDto testDto = testsAssembler.extractDtoFromDomain(testsDao.findById(id));
        for (QuestionDto question : testDto.getQuestions()) {
            for (OptionDto options : question.getOptions()) {
                options.setTruth(null);
            }
            question.setCategory(null);
        }
        return testDto;
    }

    /**
     * allegedly it saves or updates the test,
     * if id is null it saves new test
     * if id is integer it tries to update test with this id
     *
     * @param testDto test to be saved (or updated)
     * @return saved test or null
     */
    public TestDto saveOrUpdateTest(TestDto testDto) {
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

//    /**
//     * Edit and update an existing test
//     *
//     * @param testDto updated test
//     * @return updated test or not
//     */
//    public TestDto updateTest(TestDto testDto) {
//        List<QuestionDto> listOfQuestions = new ArrayList<QuestionDto>();
//        for (QuestionDto questDto : testDto.getQuestions()) {
//            if (questDto.getId() != null) {
//                listOfQuestions.add(questionService.updateQuestion(questDto));
//            } else {
//                listOfQuestions.add(questionService.addQuestion(questDto));
//            }
//        }
//        return testsAssembler.extractDtoFromDomain(testsDao.updateTest(testsAssembler.populateDtoFromDomain(testDto)));
//    }

    /**
     * Deletes test
     *
     * @param id id of test
     * @return updated test or not
     */
    public TestDto deleteTest(Integer id) {
        return testsAssembler.extractDtoFromDomain(testsDao.deleteTest(testsAssembler.populateDtoFromDomain(getTestByIdForUser(id))));
    }
}
