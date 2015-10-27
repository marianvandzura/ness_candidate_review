package dao;

import model.QuestionResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class QuestionResultsDao extends HibernateDaoSupport {

    public void addQuestionResults(QuestionResults questionResults) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(questionResults);
        transaction.commit();
    }
}
