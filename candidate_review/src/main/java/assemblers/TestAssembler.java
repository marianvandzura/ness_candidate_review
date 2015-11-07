package assemblers;

import dao.IQuestionsDao;
import dao.ITestsDao;
import dao.IUsersDao;
import dto.QuestionDto;
import dto.TestDto;
import model.Questions;
import model.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lubomir on 11/3/2015.
 */
@Component
public class TestAssembler {

    @Autowired
    ITestsDao testsDao;

    @Autowired
    IQuestionsDao questionDao;

    @Autowired
    IUsersDao usersDao;

    @Autowired
    QuestionAssembler questionAssembler;

    @Autowired
    UserAssembler userAssembler;

    public TestAssembler() {
        // default
    }

    public TestDto extractDtoFromDomain(final Tests domain) {
        TestDto testDto = new TestDto();
        testDto.setId(domain.getTestId());
        testDto.setInfo(domain.getInfo());
        testDto.setPosition(domain.getPosition());
        testDto.setVisible(domain.getVisible());
        testDto.setName(domain.getName());
        //testDto.setUserId(domain.getUser().getUserId());
//
//        List<QuestionDto> catDtos = new ArrayList<QuestionDto>();
//        for (QuestionDto questDto : questionAssembler.extractDtosListFromDomain(domain.getQuestions())) {
//            catDtos.add(questDto);
//        }
//        testDto.setQuestions(catDtos);
        return testDto;
    }


    public List<TestDto> extractDtoListFromDomain(final Collection<Tests> domain) {
        List<TestDto> testsDtoArrayList = new ArrayList<TestDto>();
        for (Tests test : domain) {
            testsDtoArrayList.add(extractDtoFromDomain(test));
        }
        return testsDtoArrayList;
    }


    public Tests populateDtoFromDomain(final TestDto testDto) {
        Tests domain = new Tests();
        domain.setInfo(testDto.getInfo());
        domain.setVisible(testDto.getVisible());
        domain.setPosition(testDto.getPosition());
        domain.setName(testDto.getName());
        domain.setTestId(testDto.getId());
        domain.setUser(usersDao.addUserById(testDto.getUserId()));
        List<Questions> catDomains = new ArrayList<Questions>();
        for (QuestionDto quest : testDto.getQuestions()) {
            catDomains.add(questionAssembler.populateDomainFromDto(quest));
        }
        domain.setQuestions(catDomains);
        return domain;
    }
}
