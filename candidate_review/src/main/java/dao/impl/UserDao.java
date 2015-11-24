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

    @Override
    public User addUser(User user) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
        return user;
    }

    @Override
    public void deleteUser(int userId) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete User where userId = :userId").setParameter("userId", userId);
        query.executeUpdate();
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User");
        List<User> users = query.list();
        transaction.commit();
        return users;
    }

    @Override
    public User findUserByUserName(String userName) {
//        List<User> users;
//        users = getSessionFactory().getCurrentSession()
//                .createQuery("from User where userName=?")
//                .setParameter(0, userName)
//                .list();
//
//        if (users.size() > 0) {
//            return users.get(0);
//        } else {
//            return null;
//        }

        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("userName", userName));
        List<User> users = (List<User>) criteria.list();
        session.clear();
        transaction.commit();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User findUserById(int userId) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("userId", userId));
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
