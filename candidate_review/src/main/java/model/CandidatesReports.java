package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Marian_Vandzura on 27.10.2015.
 */

@Entity
@Table(name = "candidates_reports")
public class CandidatesReports {

    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reportId;

    @Column(name = "candidate_name")
    private String candidateName;
    @Column(name = "candidate_surname")
    private String candidateSurname;
    @Column(name = "email")
    private String email;
    @Column(name = "test_name")
    private String testName;
    @Column(name = "date")
    private Timestamp date;
    @Column(name = "total_time")
    private Integer totalTime;
    @Column(name = "number_of_questions")
    private Integer numberOfQuestions;
    @Column(name = "test_result")
    private String testResult;
    @Column(name = "test_report")
    private byte[] testReport;

    public CandidatesReports() {
        //default
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass() || !(obj instanceof Questions)) {
            return false;
        }
        CandidatesReports candidatesReport = (CandidatesReports) obj;
        return (this.reportId == candidatesReport.getReportId())
                && (this.email != null && this.email.equals(candidatesReport.getEmail())
                && (this.testName != null && this.testName.equals(candidatesReport.getTestName()))
                && (this.date.getTime() == candidatesReport.getDate().getTime()));
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + reportId;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (testName != null ? testName.hashCode() : 0);
        return result;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidateSurname() {
        return candidateSurname;
    }

    public void setCandidateSurname(String candidateSurname) {
        this.candidateSurname = candidateSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public byte[] getTestReport() {
        return testReport;
    }

    public void setTestReport(byte[] testReport) {
        this.testReport = testReport;
    }


}
