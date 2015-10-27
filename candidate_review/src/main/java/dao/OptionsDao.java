package dao;

import model.Options;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class OptionsDao extends HibernateDaoSupport {

    public void addOptions(Options options) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(options);
        transaction.commit();
    }
}
