package dao;

import model.CandidatesReports;

import java.util.List;

/**
 * Created by Marian_Vandzura on 28.10.2015.
 */
public interface ICandidatesReportsDao {

    CandidatesReports addCandidateReport(CandidatesReports candidatesReport);
    List<CandidatesReports> getAllCandidatesReports();
    CandidatesReports findReportById(Integer id);

    List<CandidatesReports> findByFullName(String firstName, String lastName);
}
