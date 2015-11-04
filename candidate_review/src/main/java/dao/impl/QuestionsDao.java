package dao.impl;

import dao.IQuestionsDao;
import model.Questions;
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
public class QuestionsDao extends HibernateDaoSupport implements IQuestionsDao {

    @Override
    public Questions addQuestion(Questions question) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(question);
        transaction.commit();
        return question;
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
    public Questions findQuestionById(final Integer id) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Questions question = (Questions)session.get(Questions.class,id);
        transaction.commit();
        return question;

    }

    @Override
    public List<Questions> findQuestionsByCategory(Integer categoryId) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Questions.class);
        /**
         * Joining Questions table based on the name of the property in {@link Questions}.
         * For getting id of qustion use question.id again name of the property in model class.
         */
        criteria.createAlias("category","category", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("category.id", categoryId));
        List<Questions> catList = (List<Questions>) criteria.list();
        transaction.commit();
        return catList;
    }
}
