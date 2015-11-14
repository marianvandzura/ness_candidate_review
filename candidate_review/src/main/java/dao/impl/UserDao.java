package dao.impl;

import dao.IUserDao;
import model.User;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class UserDao extends HibernateDaoSupport implements IUserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addUser(User user) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User ");

        List<User> users = query.list();
        transaction.commit();
        return users;
    }

    @SuppressWarnings("unchecked")
    @Override
    public User findUserByEmail(String email) {
//        List<User> users = new ArrayList<User>();
//
//        users = sessionFactory.getCurrentSession()
//                .createQuery("from User where email=?")
//                .setParameter(0, email)
//                .list();
//
//        if (users.size() > 0) {
//            return users.get(0);
//        } else {
//            return null;
//        }
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("user.email", email));
        List<User> users = (List<User>) criteria.list();
        session.clear();
        transaction.commit();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

}
