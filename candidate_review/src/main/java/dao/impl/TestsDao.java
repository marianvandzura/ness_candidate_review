package dao.impl;

import dao.ITestsDao;
import model.Questions;
import model.Settings;
import model.Tests;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class TestsDao extends HibernateDaoSupport implements ITestsDao {

    public void addTests(Tests tests) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(tests);
        transaction.commit();
    }

    public List<Tests> getAllTests() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Tests ");

        List<Tests> tests = query.list();
        transaction.commit();
        return tests;
    }
}
