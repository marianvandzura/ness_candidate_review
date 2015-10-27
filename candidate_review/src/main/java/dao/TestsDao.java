package dao;

import model.Questions;
import model.Tests;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class TestsDao extends HibernateDaoSupport {

    public void addTests(Tests tests) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(tests);
        transaction.commit();
    }
}
