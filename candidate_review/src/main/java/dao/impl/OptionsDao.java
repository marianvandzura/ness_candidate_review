package dao.impl;

import dao.IOptionsDao;
import model.Options;
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
public class OptionsDao extends HibernateDaoSupport implements IOptionsDao {

    public void addOptions(Options options) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(options);
        transaction.commit();
    }

    public List<Options> getAllOptiopns() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Options ");

        List<Options> options = query.list();
        transaction.commit();
        return options;
    }
}
