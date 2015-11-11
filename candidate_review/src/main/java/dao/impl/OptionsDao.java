package dao.impl;

import dao.IOptionsDao;
import model.Options;
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
public class OptionsDao extends HibernateDaoSupport implements IOptionsDao {

    public OptionsDao(){
        //default
    }

    @Override
    public Options addOption(Options option) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(option);
        transaction.commit();
        return option;
    }

    @Override
    public Options updateOption(Options option) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(option);
        transaction.commit();
        return option;
    }

    @Override
    public void deleteOption(Options option) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(option);
        session.clear();
        transaction.commit();
    }

    @Override
    public List<Options> getAllOptions() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Options ");

        List<Options> options = query.list();
        transaction.commit();
        return options;
    }

    @Override
    public Options findOptionById(final Integer id) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Options option = (Options)session.get(Options.class,id);
        transaction.commit();
        return option;
    }

    @Override
    public List<Options> findOptionsByQuestionId(final Integer questionId) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Options.class);
        criteria.createAlias("question","question", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("question.questionId", questionId));
        List<Options> options = (List<Options>) criteria.list();
        session.clear();
        transaction.commit();
        return options;
    }
}
