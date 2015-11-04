package service;

import dao.ITestsDao;
import model.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Peter on 4.11.2015.
 */
@Service
public class TestService {

    @Autowired
    ITestsDao testsDao;

    public Tests save(final Tests test) {
        return testsDao.addTest(test);
    }
}
