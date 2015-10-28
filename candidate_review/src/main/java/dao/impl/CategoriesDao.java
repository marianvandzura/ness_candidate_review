package dao.impl;

import dao.ICategoriesDao;
import model.Categories;
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
public class CategoriesDao extends HibernateDaoSupport implements ICategoriesDao {

    public void addCategories(Categories categories) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(categories);
        transaction.commit();
    }

    public List<Categories> getAllCategories() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Categories ");

        List<Categories> categories = query.list();
        transaction.commit();
        return categories;
    }
}
