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

    @Override
    public Options addOption(Options option) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(option);
        transaction.commit();
        return option;
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
    public List<Options> findOptionsByQuestion(Integer questionId) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Options.class);
        /**
         * Joining Questions table based on the name of the property in {@link Questions}.
         * For getting id of qustion use question.id again name of the property in model class.
         */
        criteria.createAlias("options","option", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("option.optionId", questionId));
        List<Options> catList = (List<Options>) criteria.list();
        transaction.commit();
        return catList;
    }
}
