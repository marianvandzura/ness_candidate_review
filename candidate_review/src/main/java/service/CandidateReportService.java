package service;

import assemblers.CandidateReportAssembler;
import com.itextpdf.text.DocumentException;
import dao.impl.CandidatesReportsDao;
import dto.CandidateDto;
import dto.PdfTestDto;
import dto.QuestionDto;
import model.CandidatesReports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdf.ITextPdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 24.11.2015.
 */
@Service
public class CandidateReportService {

    @Autowired
    private CandidateReportAssembler reportAssembler;

    @Autowired
    private CandidatesReportsDao candidatesReportsDao;

    @Autowired
    QuestionService questionService;

    /**
     * Get result by ID.
     * @param id
     * @return
     */
    public CandidateDto geResultById(final Integer id){
        return reportAssembler.extractDtoFromDomain(candidatesReportsDao.findReportById(id));
    }

    /**
     * Save or update report.
     * @param report
     * @return
     */
    public CandidateDto saveOrUpdateReport(final CandidateDto report) {
        CandidatesReports savedReport = candidatesReportsDao.addCandidateReport(reportAssembler.populateDomainFromDto(report));
        return reportAssembler.extractDtoFromDomain(savedReport);
    }

    /**
     * Create PDF.
     * @param report
     * @param test
     * @return
     */
    public byte[] createPdf(final CandidateDto report,final PdfTestDto test) {
        byte[] bytePdf = null;
        try {
            ITextPdf pdfCreator = new ITextPdf();
            bytePdf =  pdfCreator.createPdf(report,test);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return bytePdf;
    }

    /**
     * Save report and generate pdf.
     * @param report
     * @param test
     * @return
     */
    public CandidateDto saveAndGeneratePdf(final CandidateDto report, final PdfTestDto test) {
        byte[] bytePdf = null;
        try {
            ITextPdf pdfCreator = new ITextPdf();
            test.setQuestions(loadAllQuestions(test.getQuestions()));
            bytePdf =  pdfCreator.createPdf(report,test);
            report.setPdf(bytePdf);
            report.setSuccesRate(pdfCreator.getSuccessRate(test.getQuestions(), test.getMarkedAnswers()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        CandidatesReports savedReport = candidatesReportsDao.addCandidateReport(reportAssembler.populateDomainFromDto(report));
        return reportAssembler.extractDtoFromDomain(savedReport);
    }

    /**
     * Load test questions from DB. Required for validation.
     *
     * @param questionsFromTest
     * @return
     */
    private List<QuestionDto> loadAllQuestions(final List<QuestionDto> questionsFromTest) {
        List<QuestionDto> questionsFromDb = new ArrayList<QuestionDto>();

        for(QuestionDto questionFromTest : questionsFromTest) {
            if(questionFromTest.getResponse() == null) {
                QuestionDto question = questionService.getQuestionById(questionFromTest.getId());
                if (question != null) {
                    questionsFromDb.add(question);
                }
            } else {
                questionsFromDb.add(questionFromTest);
            }
        }

        return questionsFromDb;
    }

    /**
     * Find report by candidate name.
     * @param firstName
     * @param lastName
     * @return
     */
    public List<CandidateDto> findByName(final String firstName, final String lastName) {
        return reportAssembler.extractDtoFromDomain(candidatesReportsDao.findByFullName(firstName, lastName));
    }

    /**
     * Get all reports.
     * @return
     */
    public List<CandidateDto> getAll() {
        return reportAssembler.extractDtoFromDomain(candidatesReportsDao.getAllCandidatesReports());
    }


}

