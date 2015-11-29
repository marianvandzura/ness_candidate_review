package dao;

import model.CandidatesReports;

import java.util.List;

/**
 * Created by
 */
public interface ICandidatesReportsDao {

    /**
     * add new report to DB
     * @param candidatesReport
     */
    void addCandidateReport(CandidatesReports candidatesReport);

    /**
     * get all reports from DB
     * @return
     */
    List<CandidatesReports> getAllCandidatesReports();
}
