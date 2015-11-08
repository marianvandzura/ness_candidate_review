package dao;

import model.Tests;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ITestsDao {

    Tests addTest(Tests test);

    Tests updateTest(Tests test);

    List<Tests> getAllTests();

    Tests findById(Integer id);

    Tests deleteTest(Tests test);
}
