package dao.impl;

import dao.IUserRoleDao;
import model.UserRole;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Marian_Vandzura on 15.11.2015.
 */

@Transactional
public class UserRoleDao extends HibernateDaoSupport implements IUserRoleDao {

    @Override
    public UserRole findUserRoleByName(String role) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(UserRole.class);
        criteria.add(Restrictions.eq("role", role));
        List<UserRole> users = (List<UserRole>) criteria.list();
        session.clear();
        transaction.commit();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
