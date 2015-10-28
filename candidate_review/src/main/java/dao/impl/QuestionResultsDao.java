package dao.impl;

import dao.IQuestionResultsDao;
import model.QuestionResults;
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
public class QuestionResultsDao extends HibernateDaoSupport implements IQuestionResultsDao {

    public void addQuestionResults(QuestionResults questionResults) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(questionResults);
        transaction.commit();
    }

    public List<QuestionResults> getAllQuestionResults() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from QuestionResults ");

        List<QuestionResults> questionResults = query.list();
        transaction.commit();
        return questionResults;
    }
}
