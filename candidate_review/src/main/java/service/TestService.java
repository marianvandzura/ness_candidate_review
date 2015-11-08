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

    public List<TestDto> getTests() {
        List<TestDto> ListOfTestDto = new ArrayList<TestDto>();
        List<Tests> tests = testsDao.getAllTests();
        for(Tests test: tests)
        {
            test.setQuestions(null);
            test.setUser(null);
        }
        for (TestDto testDto : this.testsAssembler.extractDtoListFromDomain(tests)) {
            if (testDto.getVisible()) {
                testDto.setQuestions(null);
                testDto.setUserId(null);
                ListOfTestDto.add(testDto);
            }
        }
        return ListOfTestDto;
    }

    public List<TestDto> getMyTests(Integer userid) {
        List<TestDto> myListOfTestDto = new ArrayList<TestDto>();
        for (TestDto testdto : this.testsAssembler.extractDtoListFromDomain(testsDao.getAllTests())) {
            if ((testdto.getUserId() == userid) && testdto.getVisible()) {
                testdto.setQuestions(null);
                testdto.setUserId(null);
                myListOfTestDto.add(testdto);
            }
        }
        return myListOfTestDto;
    }



    public TestDto getTestById(Integer id) {
        TestDto testDto = this.testsAssembler.extractDtoFromDomain(testsDao.findById(id));
        //TODO fill questions to test
//        if (testDto != null) {
//            for (QuestionDto questionDto : testDto.getQuestions()) {
//                for (OptionDto optionDto : questionDto.getOptions()) {
//                    optionDto.setTruth(false);
//                }
//            }
//            if (testDto.getVisible()) return testDto;
//        }
         if (testDto.getVisible()) return testDto;
        return null;
    }


    public Tests saveTest(TestDto testDto) {
        return testsDao.addTest(testsAssembler.populateDtoFromDomain(testDto));
    }

    public Tests deleteTest(TestDto testDto) {
        return testsDao.deleteTest(testsAssembler.populateDtoFromDomain(testDto));
    }
}
