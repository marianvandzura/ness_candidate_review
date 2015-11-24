package controllers;

import com.itextpdf.text.DocumentException;
import dto.CandidateDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pdf.ITextPdf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Peter on 7.11.2015.
 */
@Controller
public class PdfTestController {

    @RequestMapping(value = "/pdf/test", method = RequestMethod.GET)
    public @ResponseBody String createPdf(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        CandidateDto candidate = new CandidateDto ();
        candidate.setFirstName("Karol");
        candidate.setLastName("Papagaj");
        candidate.setEmail("somPan@barofrajer.is");
        candidate.setDate(dateFormat.format(new Date()));
        candidate.setTestName("Test for Java junior developer.");
        candidate.setTotalTime(40);

        String filePath = null;
        try {
            ITextPdf IText = new ITextPdf();
            filePath = IText.createPdf(candidate);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
