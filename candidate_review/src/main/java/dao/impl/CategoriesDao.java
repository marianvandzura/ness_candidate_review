package dao.impl;

import dao.ICategoriesDao;
import model.Categories;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class CategoriesDao extends HibernateDaoSupport implements ICategoriesDao {

    @Override
    public void addCategory(Categories category) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(category);
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
    public List<Categories> findCategoriesByQuestion(final Integer questionId) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Categories.class);
        /**
         * Joining Questions table based on the name of the property in {@link Categories}.
         * For getting id of qustion use question.id again name of the property in model class.
         */
        criteria.createAlias("questions","question", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("question.id", questionId));
        List<Categories> catList = (List<Categories>) criteria.list();
        transaction.commit();
        return catList;
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
