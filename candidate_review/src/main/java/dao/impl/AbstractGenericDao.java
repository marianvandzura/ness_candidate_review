package dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * Created by Peter on 3.11.2015.
 */
public abstract class AbstractGenericDao<T> {

    protected transient SessionFactory sessionFactory;

    protected final transient Class<T> persistentClass;

    public AbstractGenericDao() {
        persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public T findById(final String id) {
        final Session session = getSessionFactory().getCurrentSession();
        return (T) session.get(getPersistentClass(), id);
    }

    public T add(T entity) {
        final Session session = getSessionFactory().getCurrentSession();
        final Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        return entity;
    }

    public Collection<T> add(final Collection<T> entities) {
       for (T entity : entities){
          this.add(entity);
       }
        return entities;
    }
}
