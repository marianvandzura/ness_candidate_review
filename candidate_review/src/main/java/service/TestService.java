package service;

import assemblers.QuestionAssembler;
import assemblers.TestAssembler;
import dao.IQuestionsDao;
import dao.ITestsDao;
import dto.ListTestDto;
import dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     * Return list of all tests
     * @return list of dtos
     */
    public List<ListTestDto> getTests() {
        return this.testsAssembler.extractListTestDtoFromDomain(testsDao.getAllTests());
    }

    /**
     * Return list of all tests
     * @return list of dtos
     */
    public List<ListTestDto> getMyTests(Integer id) {
        return this.testsAssembler.extractListTestDtoFromDomain(testsDao.getTestsByUserId(id));
    }

    /**
     * Returns test by id
     * @param id of test
     * @return
     */
    public TestDto getTestById(Integer id) {
        return testsAssembler.extractDtoFromDomain(testsDao.findById(id));
    }

    /**
     * allegendly it save test
     * @param testDto test to be saved
     * @return saved test or null
     */
    public TestDto saveTest(TestDto testDto) {
        return testsAssembler.extractDtoFromDomain(testsDao.addTest(testsAssembler.populateDtoFromDomain(testDto)));
    }

    /**
     * Edit and update an existing test
     * @param testDto updated test
     * @return updated test or not
     */
    public TestDto editTest(TestDto testDto) {
        return testsAssembler.extractDtoFromDomain(testsDao.updateTest(testsAssembler.populateDtoFromDomain(testDto)));
    }

    /**
     * Deletes test
     * @param id id of test
     * @return updated test or not
     */
    public TestDto deleteTest(Integer id) {
        return testsAssembler.extractDtoFromDomain(testsDao.deleteTest(testsAssembler.populateDtoFromDomain(getTestById(id))));
    }
}
