package service;

import assemblers.CandidateReportAssembler;
import com.itextpdf.text.DocumentException;
import dao.impl.CandidatesReportsDao;
import dto.CandidateDto;
import dto.PdfTestDto;
import model.CandidatesReports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdf.ITextPdf;

import java.io.IOException;
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
    private ITextPdf pdfCreator;

    public CandidateDto geResultById(final Integer id){
        return reportAssembler.extractDtoFromDomain(candidatesReportsDao.findReportById(id));
    }

    public CandidateDto saveOrUpdateReport(final CandidateDto report) {
        CandidatesReports savedReport = candidatesReportsDao.addCandidateReport(reportAssembler.populateDomainFromDto(report));
        return reportAssembler.extractDtoFromDomain(savedReport);
    }

    public byte[] createPdf(final CandidateDto report,final PdfTestDto test) {
        byte[] bytePdf = null;
        try {
//            ITextPdf pdfCreator = new ITextPdf();
            bytePdf =  pdfCreator.createPdf(report,test);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return bytePdf;
    }

    public CandidateDto saveAndGeneratePdf(final CandidateDto report,final PdfTestDto test) {
        byte[] bytePdf = null;
        try {
//            ITextPdf pdfCreator = new ITextPdf();
            bytePdf =  pdfCreator.createPdf(report,test);
            report.setPdf(bytePdf);
            report.setSuccesRate(pdfCreator.getSuccessRate(test.getQuestions(),test.getMarkedAnswers()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        CandidatesReports savedReport = candidatesReportsDao.addCandidateReport(reportAssembler.populateDomainFromDto(report));
        return reportAssembler.extractDtoFromDomain(savedReport);
    }

    public List<CandidateDto> findByName(final String firstName, final String lastName) {
        return reportAssembler.extractDtoFromDomain(candidatesReportsDao.findByFullName(firstName, lastName));
    }

    public List<CandidateDto> getAll() {
        return reportAssembler.extractDtoFromDomain(candidatesReportsDao.getAllCandidatesReports());
    }


}

