package assemblers;

import dao.IQuestionsDao;
import dao.ITestsDao;
import dto.QuestionDto;
import dto.TestDto;
import model.Questions;
import model.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
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
    QuestionAssembler questionAssembler;

    @Autowired
    UserAssembler userAssembler;

    public TestsAssembler() {
        // default
    }

    public TestDto extractDtoFromDomain(final Tests domain) {
        TestDto dto = new TestDto();
        dto.setId(domain.getTestId());
        dto.setInfo(domain.getInfo());
        dto.setPosition(domain.getPosition());
        dto.setVisible(domain.getVisible());
        dto.setUser(userAssembler.extractDtoFromDomain(domain.getUserId()));
        List<QuestionDto> catDtos = new ArrayList<QuestionDto>();
        for (QuestionDto questDto: questionAssembler.extractDtosListFromDomain(domain.getQuestions()))
        {
            catDtos.add(questDto);
        }
        dto.setQuestions(catDtos);
        return dto;
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
        domain.setTestId(testDto.getId());
        domain.setUserId(userAssembler.populateDtoFromDomain(testDto.getUser()));
        List<Questions> catDomains = new ArrayList<Questions>();
        for (QuestionDto quest: testDto.getQuestions())
        {
            catDomains.add(questionAssembler.populateDomainFromDto(quest));
        }
        domain.setQuestions(catDomains);
        return domain;
    }
}
