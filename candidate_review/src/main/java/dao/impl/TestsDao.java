package dao.impl;

import dao.ITestsDao;
import model.Questions;
import model.Tests;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class TestsDao extends HibernateDaoSupport implements ITestsDao {

    public Tests addTest(Tests test) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        test.setTestId(null);
        session.save(test);
        transaction.commit();
        return test;
    }

    public Tests updateTest(Tests test) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(test);
        transaction.commit();
        return test;
    }

    public Tests deleteTest(Tests test) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(test);
        transaction.commit();
        return test;
    }

    public List<Tests> getAllTests() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Tests ");
        List<Tests> tests = query.list();

        for (Tests test : tests) {
            Hibernate.initialize(test.getQuestions());
            Hibernate.initialize(test.getUser());
        }

        transaction.commit();
        return tests;
    }


    public Tests findById(final Integer id) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Tests test = (Tests) session.get(Tests.class, id);

        //TODO something to do with lazy initialization
        Hibernate.initialize(test.getQuestions());
        Hibernate.initialize(test.getUser());

        test.setQuestions(test.getQuestions());
        test.setUser(test.getUser());

        transaction.commit();
        return test;
    }

    public List<Tests> getTestsByUserId(Integer userid) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Tests.class);
        criteria.createAlias("user", "user", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("user.userId", userid));

        List<Tests> testList = (List<Tests>) criteria.list();
        transaction.commit();
        return testList;
    }

}
