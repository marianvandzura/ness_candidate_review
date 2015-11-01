package dao;

import model.CandidatesReports;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ICandidatesReportsDao {

    void addCandidateReport(CandidatesReports candidatesReport);
    List<CandidatesReports> getAllCandidatesReports();
}
