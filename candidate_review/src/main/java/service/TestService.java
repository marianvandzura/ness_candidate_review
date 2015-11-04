package service;

import assemblers.TestAssembler;
import dao.ITestsDao;
import dto.TestDto;
import model.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 4.11.2015.
 */
public class TestService {
    @Autowired
    ITestsDao testsDao;

    @Autowired
    TestAssembler testsAssembler;


    public Tests save(final Tests test) {
        return testsDao.addTest(test);
    }

    public List<TestDto> getTests() {
        List<TestDto> testDtos = new ArrayList<TestDto>();
        for(TestDto testDto:this.testsAssembler.extractDtoListFromDomain(testsDao.getAllTests()))
        {
            if (testDto.getVisible())
                testDtos.add(testDto);
        }
        return testDtos;
    }

    public List<TestDto> getMyTests() {
        List<TestDto> testDtos = getTests();
//        if ()
        return testDtos;
    }


    public TestDto getTest(Integer id) {
        TestDto tDto = this.testsAssembler.extractDtoFromDomain(testsDao.findById(id));
        if (tDto.getVisible()) return tDto;
        return null;
    }
}
