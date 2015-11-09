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

    @Override
    public Categories addCategory(Categories category) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(category);
        transaction.commit();
        return category;
    }

    @Override
    public Categories updateCategory(Categories category) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(category);
        transaction.commit();
        return category;
    }

    @Override
    public void deleteCategory(int categoryId) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete Categories where categoryId = :id").setParameter("id", categoryId);
        query.executeUpdate();
        transaction.commit();
    }

    @Override
    public List<Categories> getAllCategories() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Categories ");

        List<Categories> categories = query.list();
        transaction.commit();
        return categories;
    }

    @Override
    public Categories findById(final Integer id) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Categories categories = (Categories) session.get(Categories.class, id);
        transaction.commit();
        return categories;
    }
}
