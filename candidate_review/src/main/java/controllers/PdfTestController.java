package controllers;

import com.itextpdf.text.DocumentException;
import dto.CandidateDto;
import dto.PdfTestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pdf.ITextPdf;
import service.CandidateReportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Peter on 7.11.2015.
 */
@Controller
public class PdfTestController {

    @Autowired
    CandidateReportService candidateReportService;
//
//    @RequestMapping(value = "/pdf/test", method = RequestMethod.GET)
//    @ResponseBody
//    public String createPdfTest(){
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        CandidateDto candidate = new CandidateDto ();
//        candidate.setFirstName("Karol");
//        candidate.setLastName("Papagaj");
//        candidate.setEmail("somPan@barofrajer.is");
////        candidate.setDate(dateFormat.format(new Date()));
//        candidate.setTestName("Test for Java junior developer.");
//        candidate.setTotalTime(40);
//
//        String filePath = null;
//        try {
//            ITextPdf IText = new ITextPdf();
//            //filePath = IText.createPdf(candidate,IText.initDto());
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return filePath;
//    }
//
    private byte[] createBytePdf() {
        CandidateDto candidate = new CandidateDto ();
        candidate.setFirstName("Karol");
        candidate.setLastName("Papagaj");
        candidate.setEmail("somPan@barofrajer.is");
//        candidate.setDate(dateFormat.format(new Date()));
        candidate.setTestName("Test for Java junior developer.");
        candidate.setTotalTime(40);

        byte[] byteFile = null;
        try {
            ITextPdf IText = new ITextPdf();
            byteFile = IText.createPdf(null,IText.initDto());
            File file = new File("E:\\test.pdf");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);

            fos.write(byteFile);
            fos.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteFile;
    }

    @RequestMapping(value = "/report/download/{id}",method = RequestMethod.GET)
    public void downloadPdfReport(HttpServletRequest request,
                           HttpServletResponse response, @PathVariable(value = "id") Integer reportId) throws IOException {

        String mimeType = "application/octet-stream";
        CandidateDto report = candidateReportService.geResultById(reportId);
        byte[] buffer = report.getPdf();

        response.setContentType(mimeType);
        response.setContentLength(buffer.length);

        String fileName = report.getFirstName() + " " + report.getLastName() +
                (report.getPosition() != null ?  " " + report.getPosition() : "") + ".pdf";

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                fileName);
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();


        outStream.write(buffer, 0,buffer.length);

    }

    @RequestMapping(value = "/report/generate/download/",method = RequestMethod.GET)
    public void saveAndDownload(HttpServletRequest request,
                                  HttpServletResponse response, @RequestBody PdfTestDto test) throws IOException {
        String mimeType = "application/octet-stream";
        CandidateDto report = candidateReportService.saveAndGeneratePdf(test.getCadidate(), test);

        byte[] buffer = report.getPdf();

        response.setContentType(mimeType);
        response.setContentLength(buffer.length);

        String fileName = report.getFirstName() + " " + report.getLastName() +
                (report.getPosition() != null ?  " " + report.getPosition() : "") + ".pdf";

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                fileName);
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        outStream.write(buffer, 0,buffer.length);
    }

    @RequestMapping(value = "/report/generate/plain/download/",method = RequestMethod.GET)
    public void generatePdfWithoutCandidate(HttpServletRequest request,
                                HttpServletResponse response, @RequestBody PdfTestDto test) throws IOException {
        String mimeType = "application/octet-stream";
        candidateReportService.createPdf(test.getCadidate(), test);

        byte[] buffer = candidateReportService.createPdf(null, test);

        response.setContentType(mimeType);
        response.setContentLength(buffer.length);

        String fileName = test.getPosition() + " test.pdf";

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                fileName);
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        outStream.write(buffer, 0,buffer.length);
    }

    @RequestMapping(value = "/report/full/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CandidateDto getFullReportById(@PathVariable(value = "id") Integer reportId) {
        CandidateDto report = candidateReportService.geResultById(reportId);

        report.setPdf(new byte[0]);

        return candidateReportService.geResultById(reportId);
    }

    /**
     * Save report
     *
     * @param report
     * @return HTTP response
     */
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public ResponseEntity saveReport(@RequestBody CandidateDto report) {

        CandidateDto savedReport = candidateReportService.saveOrUpdateReport(report);

        return new ResponseEntity<>(savedReport, HttpStatus.OK);
    }

    /**
     * update report
     *
     * @param report
     * @return HTTP response
     */
    @RequestMapping(value = "/report", method = RequestMethod.PUT)
    public ResponseEntity updateReport(@RequestBody CandidateDto report) {

        CandidateDto updatedReport = candidateReportService.saveOrUpdateReport(report);

        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }


    @RequestMapping(value = "/report/{frstName}/{lastName}", method = RequestMethod.GET)
    public ResponseEntity findByName(@PathVariable(value = "frstName") String frstName,
                                     @PathVariable(value = "lastName") String lastName) {

        List<CandidateDto> reports = candidateReportService.findByName(frstName,lastName);

        if(reports != null ){
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } else {
            return new ResponseEntity("NOT FOUND", HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/report/createPdf", method = RequestMethod.GET)
    public ResponseEntity generatePdf(@RequestBody CandidateDto report,@RequestBody PdfTestDto test){

        byte[] bytePdf = candidateReportService.createPdf(report,test);
        if(bytePdf != null) {
            return new ResponseEntity<> ("CORRECT",HttpStatus.OK);
        } else {
            return new ResponseEntity("NOT FOUND", HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/report/saveGen", method = RequestMethod.PUT)
    public ResponseEntity saveAndGeneratePdf(@RequestBody PdfTestDto test){

        CandidateDto savedDto = candidateReportService.saveAndGeneratePdf(test.getCadidate(),test);
        if(savedDto != null) {
            return new ResponseEntity<> (savedDto,HttpStatus.OK);
        } else {
            return new ResponseEntity("NOT FOUND", HttpStatus.NOT_FOUND);
        }

    }

}
