package assemblers;

import dao.IQuestionsDao;
import dao.ITestsDao;
import dao.IUsersDao;
import dto.ListTestDto;
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
        testDto.setUserId(domain.getUser().getUserId());
        testDto.setQuestions(questionAssembler.extractDtosListFromDomain(domain.getQuestions()));
        return testDto;
    }


    public List<ListTestDto> extractListTestDtoFromDomain(final Collection<Tests> domain) {
        List<ListTestDto> testsDtoArrayList = new ArrayList<ListTestDto>();
        ListTestDto testDto;
        for (Tests test : domain) {
            testDto = new ListTestDto();
            testDto.setId(test.getTestId());
            testDto.setInfo(test.getInfo());
            testDto.setPosition(test.getPosition());
            testDto.setName(test.getName());
            if(test.getVisible())
            testsDtoArrayList.add(testDto);
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
        domain.setUser(usersDao.findUserById(testDto.getUserId()));
        List<Questions> catDomains = new ArrayList<Questions>();
        for (QuestionDto quest : testDto.getQuestions()) {
            catDomains.add(questionAssembler.populateDomainFromDto(quest));
        }
        domain.setQuestions(catDomains);
        return domain;
    }


    public List<Tests> populateDtoListFromDomain(final List<TestDto> testDtos) {
        List<Tests> testsList = new ArrayList<Tests>();
        for (TestDto testDto : testDtos) {
            testsList.add(populateDtoFromDomain(testDto));
        }
        return testsList;
    }
}
