package dao;

import model.Tests;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ITestsDao {

    void addTest(Tests test);

    List<Tests> getAllTests();
}
