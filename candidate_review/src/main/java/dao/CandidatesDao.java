package dao;

import model.Candidates;
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
public class CandidatesDao  extends HibernateDaoSupport {

    public void addCandidates(Candidates candidates) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(candidates);
        transaction.commit();
    }

    public List<Candidates> getAllCandidates() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Candidates ");

        List<Candidates> candidates = query.list();
        transaction.commit();
        return candidates;
    }
}
