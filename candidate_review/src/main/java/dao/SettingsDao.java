package dao;

import model.Settings;
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
public class SettingsDao extends HibernateDaoSupport {

    public void addQuestions(Settings settings) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(settings);
        transaction.commit();
    }

    public List<Settings> getAllCategories() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Settings ");

        List<Settings> settings = query.list();
        transaction.commit();
        return settings;
    }
}
