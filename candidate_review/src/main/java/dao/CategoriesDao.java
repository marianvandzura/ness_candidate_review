package dao;

import model.Categories;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class CategoriesDao extends HibernateDaoSupport {

    public void addCategories(Categories categories) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(categories);
        transaction.commit();
    }
}
