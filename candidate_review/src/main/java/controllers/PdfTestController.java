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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Peter on 7.11.2015.
 */
@Controller
public class PdfTestController {

    @Autowired
    CandidateReportService candidateReportService;

    @RequestMapping(value = "/pdf/test", method = RequestMethod.GET)
    @ResponseBody
    public String createPdf(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        CandidateDto candidate = new CandidateDto ();
        candidate.setFirstName("Karol");
        candidate.setLastName("Papagaj");
        candidate.setEmail("somPan@barofrajer.is");
//        candidate.setDate(dateFormat.format(new Date()));
        candidate.setTestName("Test for Java junior developer.");
        candidate.setTotalTime(40);

        String filePath = null;
        try {
            ITextPdf IText = new ITextPdf();
            //filePath = IText.createPdf(candidate,IText.initDto());
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CandidateDto getReportById(@PathVariable(value = "id") Integer reportId) {
        //init arrayList because of performance
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


        report.setPdf(createBytePdf());
        CandidateDto savedReport = candidateReportService.saveOrUpdateReport(report);
        try {

            File file = new File("E:\\testovanie.pdf");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);

            fos.write(savedReport.getPdf());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(savedReport, HttpStatus.OK);
    }
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
            byteFile = IText.createPdf(candidate,IText.initDto());
            File file = new File("E:\\sompan.pdf");
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



        InputStream inputStream = null;

        return byteFile;
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
            return new ResponseEntity<byte[]> (bytePdf,HttpStatus.OK);
        } else {
            return new ResponseEntity("NOT FOUND", HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/report/saveGen", method = RequestMethod.PUT)
    public ResponseEntity saveAndGeneratePdf(@RequestBody CandidateDto report,@RequestBody PdfTestDto test){

        CandidateDto savedDto = candidateReportService.saveAndGeneratePdf(report,test);
        if(savedDto != null) {
            return new ResponseEntity<> (savedDto,HttpStatus.OK);
        } else {
            return new ResponseEntity("NOT FOUND", HttpStatus.NOT_FOUND);
        }

    }

}
