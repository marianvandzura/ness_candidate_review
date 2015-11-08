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

import javax.transaction.Transactional;
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


    public Tests save(final Tests test) {
        return testsDao.addTest(test);
    }

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


    public Tests saveTest(TestDto testDto) {
        return testsDao.addTest(testsAssembler.populateDtoFromDomain(testDto));
    }

    public Tests deleteTest(TestDto testDto) {
        return testsDao.deleteTest(testsAssembler.populateDtoFromDomain(testDto));
    }
}
