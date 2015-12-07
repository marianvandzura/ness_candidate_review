package dao;

import model.Tests;

import java.util.List;

/**
 * Created by Lubomir on 11/3/2015.
 */
public interface ITestsDao {

    /**
     * add new test to DB
     * @param test
     * @return added Tests object
     */
    Tests addTest(Tests test);

    /**
     * update test
     * @param test
     * @return updated Tests object
     */
    Tests updateTest(Tests test);

    /**
     * get all test from DB
     * @return list of Tests objects
     */
    List<Tests> getAllTests();

    /**
     * get test by test ID
     * @param id
     * @return Tests object with requested ID
     */
    Tests findById(Integer id);

    /**
     * delete test
     * @param test
     * @return deleted Tests object
     */
    Tests deleteTest(Tests test);

    /**
     * get all test owned by User
     * @param userid
     * @return list of Tests
     */
    List<Tests> getTestsByUserId(Integer userid);
}
