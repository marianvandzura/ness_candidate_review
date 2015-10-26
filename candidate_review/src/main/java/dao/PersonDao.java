package dao;

import model.Person;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Marian_Vandzura on 26.10.2015.
 * object to access Person table
 */
//this is used for able to use Transactions
@Transactional
public class PersonDao extends HibernateDaoSupport {

    public void addPerson(Person person) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(person);
        transaction.commit();
    }

    public List<Person> getAllPersons() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Person");

        List<Person> persons = query.list();
        transaction.commit();
        return persons;
    }
}
