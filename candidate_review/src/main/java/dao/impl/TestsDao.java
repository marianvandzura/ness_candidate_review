package dao.impl;

import dao.ITestsDao;
import model.Tests;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        session.saveOrUpdate(test);
        transaction.commit();
        return test;
    }

    public List<Tests> getAllTests() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Tests ");

        List<Tests> tests = query.list();
        transaction.commit();
        return tests;
    }

    @Override
    public Tests findById(final Integer id) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Tests test = (Tests)session.get(Tests.class,id);
        transaction.commit();
        return test;
    }
}
