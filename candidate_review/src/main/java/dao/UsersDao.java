package dao;

import model.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class UsersDao extends HibernateDaoSupport {

    public void addTests(Users users) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(users);
        transaction.commit();
    }
}
