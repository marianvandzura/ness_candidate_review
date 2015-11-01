package dao.impl;

import dao.IQuestionsDao;
import model.Questions;
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
public class QuestionsDao extends HibernateDaoSupport implements IQuestionsDao {
    @Override
    public Questions addQuestions(final Questions questions) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(questions);
        transaction.commit();
        return questions;
    }

    @Override
    public List<Questions> getAllQuestions() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Questions ");

        List<Questions> questions = query.list();
        transaction.commit();
        return questions;
    }
    @Override
    public Questions findById(final Integer id) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Questions question = (Questions)session.get(Questions.class,id);
        transaction.commit();
        return question;

    }
}
