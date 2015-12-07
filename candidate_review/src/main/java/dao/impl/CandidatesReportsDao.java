package dao.impl;

import dao.ICandidatesReportsDao;
import model.CandidatesReports;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */
@Transactional
public class CandidatesReportsDao extends HibernateDaoSupport implements ICandidatesReportsDao {

    public CandidatesReports addCandidateReport(CandidatesReports candidatesReport) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(candidatesReport);
        transaction.commit();
        return candidatesReport;
    }

    public List<CandidatesReports> getAllCandidatesReports() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from CandidatesReports ");

        List<CandidatesReports> candidates = query.list();
        transaction.commit();
        return candidates;
    }

    @Override
    public CandidatesReports findReportById(final Integer id) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        CandidatesReports report = (CandidatesReports) session.get(CandidatesReports.class, id);
        transaction.commit();
        return report;
    }

    @Override
    public List<CandidatesReports> findByFullName(final String firstName, final String lastName){
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(CandidatesReports.class);
        if (firstName != null && !firstName.isEmpty()) {
            criteria.add(Restrictions.like("candidateName", firstName, MatchMode.EXACT).ignoreCase());
        }
        if(lastName != null && !lastName.isEmpty()) {
            criteria.add(Restrictions.like("candidateSurname",lastName, MatchMode.EXACT).ignoreCase());
        }
        List<CandidatesReports> reports = criteria.list();
        transaction.commit();
        return reports;
    }
}
