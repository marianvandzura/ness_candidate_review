package dao.impl;

import dao.ICandidatesReportsDao;
import model.CandidatesReports;
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
public class CandidatesReportsDao extends HibernateDaoSupport implements ICandidatesReportsDao {

    public void addCandidateReport(CandidatesReports candidatesReport) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(candidatesReport);
        transaction.commit();
    }

    public List<CandidatesReports> getAllCandidatesReports() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from CandidatesReports ");

        List<CandidatesReports> candidates = query.list();
        transaction.commit();
        return candidates;
    }
}
