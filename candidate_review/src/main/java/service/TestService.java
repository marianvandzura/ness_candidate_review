package service;

import assemblers.QuestionAssembler;
import assemblers.TestAssembler;
import dao.IQuestionsDao;
import dao.ITestsDao;
import dto.OptionDto;
import dto.QuestionDto;
import dto.TestDto;
import model.Tests;
import org.hibernate.Hibernate;
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
    public List<TestDto> getTests() {
        return this.testsAssembler.extractDtoListFromDomain(testsDao.getAllTests());
    }

    /**
     * Returns test by id
     * @param id of test
     * @return
     */
    public TestDto getTestById(Integer id) {
        return testsAssembler.extractDtoFromDomain(testsDao.findById(id));
    }


    public TestDto saveTest(TestDto testDto) {
        return testsAssembler.extractDtoFromDomain(testsDao.addTest(testsAssembler.populateDtoFromDomain(testDto)));
    }

    public TestDto editTest(TestDto testDto) {
        return testsAssembler.extractDtoFromDomain(testsDao.updateTest(testsAssembler.populateDtoFromDomain(testDto)));
    }

    public TestDto deleteTest(TestDto testDto) {
        return testsAssembler.extractDtoFromDomain(testsDao.deleteTest(testsAssembler.populateDtoFromDomain(testDto)));
    }
}
