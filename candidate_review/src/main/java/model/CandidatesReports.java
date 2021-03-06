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
    @Column(name = "success_rate")
    private Double succesRate;
    @Column(name = "test_result")
    private String testResult;
    @Lob
    private byte[] pdfResult;
    @Column(name = "position")
    private String position;

    public CandidatesReports() {
        //default
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

    public byte[] getPdfResult() {
        return pdfResult;
    }

    public void setPdfResult(final byte[] pdfResult) {
        this.pdfResult = pdfResult;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSuccesRate() {
        return succesRate;
    }

    public void setSuccesRate(Double succesRate) {
        this.succesRate = succesRate;
    }
}
