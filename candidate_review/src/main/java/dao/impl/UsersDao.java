package dao.impl;

import dao.IUsersDao;
import model.Tests;
import model.Users;
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
public class UsersDao extends HibernateDaoSupport implements IUsersDao {

    public void addUser(Users user) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
    }

    public List<Users> getAllUsers() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Users ");

        List<Users> users = query.list();
        transaction.commit();
        return users;
    }

    public Users findUserById(Integer userId) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Users users = (Users)session.get(Users.class,userId);
        transaction.commit();
        return users;
    }
}
