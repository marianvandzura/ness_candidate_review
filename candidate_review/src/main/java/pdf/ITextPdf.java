package pdf;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.codec.PngImage;
import dto.CandidateDto;
import dto.OptionDto;
import dto.PdfTestDto;
import dto.QuestionDto;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Peter on 8.11.2015.
 */
public class ITextPdf {

    private enum QuestionType {
            RADIO, CHECK, CODE, WRITEDOWN;

    }

    private enum QuestionState {
        CORRECT,PARTIALY_CORRECT,INCORRECT
    }

    private final static String RADIO_TYPE = "combobox";

    private final static String CHECK_TYPE = "checkbox";

    private final static String CODE_TYPE = "code";

    private final static String WRITE_TYPE = "text";

    private final static String RADIO_TEXT = "only one correct";

    private final static String CHECK_TEXT = "one or more correct";

    private final static String WRITE_TEXT = "write down answer";

    private final static String FONT_AR = ".\\candidate_review\\arial.ttf";

    private final static String FONT_AR_BD = ".\\candidate_review\\arialbd.ttf";

    private final static String LOGO_PATH = ".\\candidate_review\\ness-logo.png";

    private final static String CORRECT_PATH = ".\\candidate_review\\correct.png";

    private final static String MARKED_CORRECT_PATH = ".\\candidate_review\\marked-correct.png";

    private final static String MARKED_INCORRECT_PATH = ".\\candidate_review\\marked-incorrect.png";

    private final static Integer TAB_DISTANCE = 25;

    private BaseFont baseArial;

    private BaseFont baseArialBold;

    private Font arialFont10;

    private Font arialBoldFont10;

    private Font arialFont12;

    private Font arialBoldFont12;

    private Font arialFont14;

    private Font arialBoldFont14;

    private Image correctImg;

    private Image markedCorrectImg;

    private Image markedIncorrectImg;

    private Document document;

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    private Integer numOfCorrect;
    private Integer numOfPartiallyCorr;
    private Integer numOfIncorrect;

    public ITextPdf() throws IOException, DocumentException {
        this.baseArial=  BaseFont.createFont(FONT_AR, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        this.baseArialBold=  BaseFont.createFont(FONT_AR_BD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        this.arialFont10 = new Font(baseArial, 10);
        this.arialBoldFont10 = new Font(baseArialBold, 10);

        this.arialFont12 = new Font(baseArial, 12);
        this.arialBoldFont12 = new Font(baseArialBold, 12);

        this.arialFont14 = new Font(baseArial, 14);
        this.arialBoldFont14 = new Font(baseArialBold, 14);

        this.document = new Document();
        this.document.setMargins(60,60,70,70);

        correctImg = Image.getInstance(CORRECT_PATH);
        correctImg.setAlignment(Image.LEFT| Image.TEXTWRAP);
        correctImg.scaleAbsolute(new Rectangle(10,10));

        markedCorrectImg = Image.getInstance(MARKED_CORRECT_PATH);
        markedCorrectImg.setAlignment(Image.LEFT| Image.TEXTWRAP);
        markedCorrectImg.scaleAbsolute(new Rectangle(10,10));

        markedIncorrectImg = Image.getInstance(MARKED_INCORRECT_PATH);
        markedIncorrectImg.setAlignment(Image.LEFT| Image.TEXTWRAP);
        markedIncorrectImg.scaleAbsolute(new Rectangle(10,10));
    }

    public byte[] createPdf(final CandidateDto candidate, final PdfTestDto test)
            throws DocumentException, IOException {

        PdfWriter writer = PdfWriter.getInstance(document,byteArrayOutputStream);

        document.open();
        drawHeader();

        if(candidate != null) {
            PdfContentByte cbPie = writer.getDirectContent();
            PdfTemplate pie = cbPie.createTemplate(PageSize.A4.getWidth(), 300);
            Graphics2D pieChartG2D = new PdfGraphics2D(pie, PageSize.A4.getWidth(), 300);
            Rectangle2D pieChartR2D = new Rectangle2D.Double(0, 0, PageSize.A4.getWidth(), 300);
            JFreeChart pieChart= generatePieChart(test);
            pieChart.draw(pieChartG2D, pieChartR2D);
            pieChart.setBackgroundPaint(Color.WHITE);
            pieChartG2D.dispose();
            cbPie.addTemplate(pie, 0, 0);

            drawCandidateInfos(candidate, test);
        } else {
            drawPlainFistPage(test);
        }

        printQuestions(test.getQuestions(),test.getMarkedAnswers());

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    private void drawHeader() throws DocumentException, IOException {
        Paragraph ph = new Paragraph(new Phrase("NESS KE", arialFont12));
        PdfPCell cell = new PdfPCell(ph);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(1f);
        Image logoImage = PngImage.getImage(LOGO_PATH);
        logoImage.scaleAbsolute(new Rectangle(50,50));
        logoImage.setAbsolutePosition(document.getPageSize().getWidth()-110
                ,document.getPageSize().getHeight()- 84);
        logoImage.scaleAbsolute(50, 50);
        document.add(logoImage);
        PdfPTable table = new PdfPTable(1);
        table.addCell(cell);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100f);
        document.add(table);
    }

    private void drawPlainFistPage(final PdfTestDto test) throws DocumentException {
        Paragraph titleParagraph = new Paragraph("Skills Evaluation", arialBoldFont12);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);

        Paragraph numberOfQuestParagraph = new Paragraph("Number of questions: ",
                arialFont12);
        numberOfQuestParagraph.setTabSettings(new TabSettings(150f));
        numberOfQuestParagraph.add(Chunk.TABBING);
        numberOfQuestParagraph.add(new Chunk(Integer.toString(test.getQuestions().size())));
        document.add(numberOfQuestParagraph);

        if(test.getPosition() != null) {
            Paragraph descParagraph = new Paragraph("Position: ", arialFont12);
            descParagraph.setTabSettings(new TabSettings(150f));
            descParagraph.add(Chunk.TABBING);
            descParagraph.add(new Chunk(test.getPosition()));
            document.add(descParagraph);
        }
    }

    private void drawCandidateInfos(final CandidateDto candidate, final PdfTestDto test) throws DocumentException {
        Paragraph titleParagraph = new Paragraph("Skills Evaluation", arialBoldFont12);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);

        Paragraph nameParagraph = new Paragraph(candidate.getFirstName() + " " + candidate.getLastName() + ", " +
                (candidate.getEmail() != null ? candidate.getEmail() : ""), arialFont12);
        nameParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(nameParagraph);

        Paragraph dateParagraph = new Paragraph("Date: ", arialFont12);
        dateParagraph.setTabSettings(new TabSettings(150f));
        dateParagraph.add(Chunk.TABBING);
        if (candidate.getDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateParagraph.add(new Chunk(dateFormat.format(candidate.getDate())));
        } else {
            dateParagraph.add(new Chunk("NA"));
        }
        document.add(dateParagraph);

        if (candidate.getTotalTime() != null) {
            Paragraph timeParagraph = new Paragraph("Time: ", arialFont12);
            timeParagraph.setTabSettings(new TabSettings(150f));
            timeParagraph.add(Chunk.TABBING);
            timeParagraph.add(new Chunk(candidate.getTotalTime() + " Minutes"));
            document.add(timeParagraph);
        }

        Paragraph numberOfQuestParagraph = new Paragraph("Number of questions: ",
                arialFont12);
        numberOfQuestParagraph.setTabSettings(new TabSettings(150f));
        numberOfQuestParagraph.add(Chunk.TABBING);
        numberOfQuestParagraph.add(new Chunk(Integer.toString(test.getQuestions().size())));
        document.add(numberOfQuestParagraph);

        //if (preview) {
        Paragraph validatedParagraph = new Paragraph("Validated questions: ", arialFont12);
        validatedParagraph.setTabSettings(new TabSettings(150f));
        validatedParagraph.add(Chunk.TABBING);
        validatedParagraph.add(new Chunk(Integer.toString(getNumOfValidatedQuestions(test.getQuestions()))));
        document.add(validatedParagraph);

        DecimalFormat df = new DecimalFormat(".##");
        Paragraph percentageParagraph = new Paragraph("Success rate: ", arialFont12);
        percentageParagraph.setTabSettings(new TabSettings(150f));
        percentageParagraph.add(Chunk.TABBING);
        if(getSuccessRate(test.getQuestions(), test.getMarkedAnswers()) > 0) {
            percentageParagraph.add(new Chunk(df.format(getSuccessRate(test.getQuestions(), test.getMarkedAnswers())) + "%"));
        } else {
            percentageParagraph.add(new Chunk("0%"));
        }
        document.add(percentageParagraph);
//            }
        Paragraph descParagraph = new Paragraph("Test description: ", arialFont12);
        descParagraph.setTabSettings(new TabSettings(150f));
        descParagraph.add(Chunk.TABBING);
        descParagraph.add(new Chunk(candidate.getTestName()));
        document.add(descParagraph);


        Paragraph resultParagraph = new Paragraph("Result: ", arialFont12);
        resultParagraph.setTabSettings(new TabSettings(150f));
        resultParagraph.add(Chunk.TABBING);
        if (candidate.getTestResult() != null) {
            resultParagraph.add(new Chunk(candidate.getTestResult()));
        } else {
            resultParagraph.add(new Chunk("NA"));
        }
        document.add(resultParagraph);

    }

    private int countQuestionsByState(final QuestionState state, final List<QuestionDto> questions,
                                                final Map<Integer, List<Integer>> markedAnswers){
        int count = 0;
        int numberOfCorrectOpt = 0;
        int numberOfCorrectMarked = 0;
        int numberOfIncorrectMarked = 0;
        for(QuestionDto question : questions) {
            List<Integer> marked = markedAnswers.get(question.getId());
            if(question.getOptions() != null) {
            for (OptionDto option : question.getOptions()) {

                if (marked != null && marked.contains(option.getId())) {
                    if (option.getTruth()) {
                        numberOfCorrectMarked++;
                    } else {
                        numberOfIncorrectMarked++;
                    }
                }
                if (option.getTruth()) {
                    numberOfCorrectOpt++;
                }
            }
        }
            switch (state){
                case CORRECT:
                    if(numberOfCorrectOpt == numberOfCorrectMarked && numberOfIncorrectMarked == 0) {
                        count++;
                    }
                    break;
                case PARTIALY_CORRECT:
                    if(numberOfIncorrectMarked < numberOfCorrectMarked && numberOfIncorrectMarked != 0) {
                        count++;
                    }
                    break;
                case INCORRECT:
                    if(numberOfIncorrectMarked >= numberOfCorrectMarked || numberOfCorrectMarked == 0) {
                        count++;
                    }
                    break;
            }
            numberOfCorrectOpt = 0;
            numberOfCorrectMarked = 0;
            numberOfIncorrectMarked = 0;
        }
        return count;
    }

    private int getNumOfValidatedQuestions(final List<QuestionDto> questions) {
        int numberOfValidated = 0;
        for(QuestionDto question : questions) {
            //question.getCode() == null
            if(!CODE_TYPE.equals(question.getType()) || !WRITE_TYPE.equals(question.getType())) {
                numberOfValidated++;
            }
        }
        return numberOfValidated;
    }

    private double getSuccessRate(final List<QuestionDto> questions, final Map<Integer, List<Integer>> markedAnswers) {
        float numberOfCorrectOpt = 0;
        float numberOfCorrectMarked = 0;
        float numberOfIncorrectMarked = 0;
        double result = 0;
        for(QuestionDto question : questions) {
            List<Integer> marked = markedAnswers.get(question.getId());
            if(question.getOptions() != null) {
                for (OptionDto option : question.getOptions()) {

                    if (marked != null && marked.contains(option.getId())) {
                        if (option.getTruth()) {
                            numberOfCorrectMarked++;
                        } else {
                            numberOfIncorrectMarked++;
                        }
                    }
                    if (option.getTruth()) {
                        numberOfCorrectOpt++;
                    }
                }
            }
            if(numberOfCorrectMarked > numberOfIncorrectMarked) {
                result += (numberOfCorrectMarked - numberOfIncorrectMarked) /  numberOfCorrectOpt;
            }
            numberOfCorrectOpt = 0;
            numberOfCorrectMarked = 0;
            numberOfIncorrectMarked = 0;
        }
        return (result / questions.size()) * 100;
    }



    private void printQuestions(final List<QuestionDto> questions, final Map<Integer, List<Integer>> markedAnswers)
            throws DocumentException, IOException {
        String type = null;

        document.newPage();
        Paragraph questionParagraph;
        Paragraph optionParagraph;
        PdfPCell questionCell;
        List<QuestionDto> otherQuestions = new ArrayList<QuestionDto>();
        int questionCounter = 1;
        for(QuestionDto question : questions) {
            if (CODE_TYPE.equals(question.getType()) || WRITE_TYPE.equals(question.getType())) {
                otherQuestions.add(question);
            }
            else {
                if (RADIO_TYPE.equals(question.getType())) {
                    type = RADIO_TEXT;
                } else {
                    type = CHECK_TEXT;
                }

                PdfPTable table = new PdfPTable(1);
                questionCell = new PdfPCell();
                questionParagraph = new Paragraph(questionCounter++ + ". " + question.getQuestion() +
                        "(" + type + ")", this.arialBoldFont10);
                questionCell.addElement(questionParagraph);
                questionCell.addElement(Chunk.NEWLINE);
                List<Integer> marked = markedAnswers.get(question.getId());
                for (OptionDto option : question.getOptions()) {
                    optionParagraph = new Paragraph();

                    if (marked != null && marked.contains(option.getId())) {
                        if (option.getTruth()) {
                            optionParagraph.add(new Chunk(markedCorrectImg, 0, 0, true));
                        } else {
                            optionParagraph.add(new Chunk(markedIncorrectImg, 0, 0, true));
                        }
                    } else if (option.getTruth()) {
                        optionParagraph.add(new Chunk(correctImg, 0, 0, true));
                    }
                    optionParagraph.setTabSettings(new TabSettings(25f));
                    optionParagraph.add(Chunk.TABBING);
                    optionParagraph.add(new Chunk(option.getOption(), this.arialFont10));

                    questionCell.addElement(optionParagraph);
                }
                questionCell.setFixedHeight((PageSize.A4.getHeight() / 2) - 70);
                questionCell.setBorder(Rectangle.BOTTOM);
                questionCell.setBorderColor(BaseColor.BLACK);
                table.addCell(questionCell);
                document.add(table);
            }
        }
        PdfPCell codeQuestionCell;

        for(QuestionDto question : otherQuestions) {

            if (WRITE_TYPE.equals(question.getType())) {
                type = WRITE_TEXT;
            } else {
                type = CODE_TYPE;
            }
            PdfPTable codeTable = new PdfPTable(1);
            codeQuestionCell = new PdfPCell();
            codeQuestionCell.setFixedHeight((PageSize.A4.getHeight() - 140));
            Paragraph codeQuestionParagraph = new Paragraph(question.getQuestion() + "(" +
                    type + ")", arialFont10);
            Paragraph code = new Paragraph(question.getCode(), arialFont10);
            codeQuestionCell.addElement(codeQuestionParagraph);
            codeQuestionCell.addElement(code);
            codeQuestionCell.setBorder(Rectangle.NO_BORDER);
            codeTable.addCell(codeQuestionCell);
            document.add(codeTable);
        }

    }

    public JFreeChart generatePieChart(final PdfTestDto test) {

        int correct = countQuestionsByState(
                QuestionState.CORRECT,test.getQuestions(),test.getMarkedAnswers());
        int partialyCorrect = countQuestionsByState(
                QuestionState.PARTIALY_CORRECT,test.getQuestions(),test.getMarkedAnswers());
        int incorrect =  countQuestionsByState(
                QuestionState.INCORRECT,test.getQuestions(),test.getMarkedAnswers());

        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("Correct questions = " + correct, correct);
        dataSet.setValue("Partially correct questions = " + partialyCorrect, partialyCorrect);
        dataSet.setValue("Incorrect question = " + incorrect, incorrect);

        JFreeChart chart = ChartFactory.createPieChart(
                "Test Evaluation", dataSet, true, true, false);

        return chart;
    }

    public PdfTestDto initDto(){
        PdfTestDto test = new PdfTestDto();
        QuestionDto quest1 = new QuestionDto();
        QuestionDto quest2 = new QuestionDto();
        QuestionDto quest3 = new QuestionDto();
        QuestionDto quest4 = new QuestionDto();
        QuestionDto quest5 = new QuestionDto();
        QuestionDto quest6 = new QuestionDto();
        QuestionDto quest7 = new QuestionDto();
        QuestionDto quest8 = new QuestionDto();
        QuestionDto quest9 = new QuestionDto();
        QuestionDto quest10 = new QuestionDto();
        QuestionDto quest11 = new QuestionDto();
        QuestionDto quest12 = new QuestionDto();
        QuestionDto quest13 = new QuestionDto();
        QuestionDto quest14 = new QuestionDto();
        QuestionDto quest15 = new QuestionDto();
        OptionDto opt11 = new OptionDto();
        OptionDto opt12 = new OptionDto();
        OptionDto opt13 = new OptionDto();
        OptionDto opt14 = new OptionDto();
        OptionDto opt21 = new OptionDto();
        OptionDto opt22 = new OptionDto();
        OptionDto opt23 = new OptionDto();
        OptionDto opt31 = new OptionDto();
        OptionDto opt32 = new OptionDto();
        OptionDto opt33 = new OptionDto();
        OptionDto opt34 = new OptionDto();
        OptionDto opt41 = new OptionDto();
        OptionDto opt42 = new OptionDto();
        OptionDto opt43 = new OptionDto();
        OptionDto opt51 = new OptionDto();
        OptionDto opt52 = new OptionDto();
        OptionDto opt53 = new OptionDto();
        OptionDto opt54 = new OptionDto();
        OptionDto opt61 = new OptionDto();
        OptionDto opt62 = new OptionDto();
        OptionDto opt63 = new OptionDto();
        OptionDto opt71 = new OptionDto();
        OptionDto opt72 = new OptionDto();
        OptionDto opt73 = new OptionDto();
        OptionDto opt74 = new OptionDto();
        OptionDto opt81 = new OptionDto();
        OptionDto opt82 = new OptionDto();
        OptionDto opt83 = new OptionDto();
        OptionDto opt91 = new OptionDto();
        OptionDto opt92 = new OptionDto();
        OptionDto opt93 = new OptionDto();
        OptionDto opt94 = new OptionDto();
        OptionDto opt101 = new OptionDto();
        OptionDto opt102 = new OptionDto();
        OptionDto opt103 = new OptionDto();
        OptionDto opt111 = new OptionDto();
        OptionDto opt112 = new OptionDto();
        OptionDto opt113 = new OptionDto();
        OptionDto opt114 = new OptionDto();
        OptionDto opt121 = new OptionDto();
        OptionDto opt122 = new OptionDto();
        OptionDto opt123 = new OptionDto();
        OptionDto opt131 = new OptionDto();
        OptionDto opt132 = new OptionDto();
        OptionDto opt133 = new OptionDto();
        OptionDto opt141 = new OptionDto();
        OptionDto opt142 = new OptionDto();
        OptionDto opt143 = new OptionDto();
        OptionDto opt144 = new OptionDto();
        OptionDto opt151 = new OptionDto();
        OptionDto opt152 = new OptionDto();
        OptionDto opt153 = new OptionDto();

        quest1.setId(1);
        quest2.setId(2);
        quest3.setId(3);
        quest4.setId(4);
        quest5.setId(5);
        quest6.setId(6);
        quest7.setId(7);
        quest8.setId(8);
        quest9.setId(9);
        quest10.setId(10);
        quest11.setId(11);
        quest12.setId(12);
        quest13.setId(13);

        quest1.setQuestion("Which of these lines will compile? Select the four correct answers.");
        quest1.setType(CHECK_TYPE);
        opt11.setId(11);
        opt11.setOption("short s = 20;");
        opt11.setTruth(true);

        opt12.setId(12);
        opt12.setOption("byte b = 128;");
        opt12.setTruth(false);

        opt13.setId(13);
        opt13.setOption("char c = 32;");
        opt13.setTruth(true);

        opt14.setId(14);
        opt14.setOption("double d = 1.4;");
        opt14.setTruth(true);


        quest2.setQuestion("Objekt pozost�va z:");
        quest2.setType(RADIO_TYPE);
        opt21.setId(21);
        opt21.setOption("Met�d a oper�ci�.");
        opt21.setTruth(false);

        opt22.setId(22);
        opt22.setOption("Atrib�tov a premenn�ch.");
        opt22.setTruth(false);

        opt23.setId(23);
        opt23.setOption("Premenn�ch a met�d.");
        opt23.setTruth(true);


        quest3.setQuestion("Ka�d� program v jazyku Java m� k dispoz�cii 3 �tandardn� I/O objekty. Ktor� s� to?\n" +
                "Vyberte aspo� jednu odpove�.");
        quest3.setType(RADIO_TYPE);
        opt31.setId(31);
        opt31.setOption("System1.out, System2.out, System3.out");
        opt31.setTruth(false);

        opt32.setId(32);
        opt32.setOption("System.in, System.out, System.err");
        opt32.setTruth(true);

        opt33.setId(33);
        opt33.setOption("Main.in, Main.out, Main.err");
        opt33.setTruth(false);

        opt34.setId(34);
        opt34.setOption("Console.in, Console.out, Console.exe");
        opt34.setTruth(false);


        quest4.setQuestion("Ktor� z nasleduj�cich tvrden� je pravdiv� o System.in?\n" +
                "Vyberte aspo� jednu odpove�.");
        quest4.setType(RADIO_TYPE);
        opt41.setId(41);
        opt41.setOption("Je to objekt typu InputStream predstavuj�ci �tandardn� vstup.");
        opt41.setTruth(true);

        opt42.setId(42);
        opt42.setOption("Je to objekt triedy FileReader predstavuj�ci �tandardn� vstup.");
        opt42.setTruth(false);

        opt43.setId(43);
        opt43.setOption("Je to objekt typu InputStreamReader predstavuj�ci �tandardn� vstup.");
        opt43.setTruth(false);


        quest5.setQuestion("Ktor� z nasleduj�cich tvrden� o serializ�cii objektov s� pravdiv�.");
        quest5.setType(CHECK_TYPE);
        opt51.setId(51);
        opt51.setOption("Trieda, ktorej objekt chceme serializova� mus� implementova� rozhranie\n" +
                "Serializable.");
        opt51.setTruth(true);

        opt52.setId(52);
        opt52.setOption("Serializ�cia je proces transform�cie stavu objektu do serializovanej formy takej, aby\n" +
                "bolo mo�n� zrekon�truova� objekt.");
        opt52.setTruth(true);

        opt53.setId(53);
        opt53.setOption("Trieda, ktorej objekt chceme serializova� mus� dedi� od triedy Serializable.");
        opt53.setTruth(false);

        opt54.setId(54);
        opt54.setOption("Objekty s ve�k�m po�tom met�d nie je mo�n� serializova�.");
        opt54.setTruth(false);


        quest6.setQuestion("Ktor� z nasleduj�cich k���ov�ch slov programovacieho jazyka Java indikuje dedi�nos�?");
        quest6.setType(RADIO_TYPE);
        opt61.setId(61);
        opt61.setOption("extends");
        opt61.setTruth(true);

        opt62.setId(62);
        opt62.setOption("import");
        opt62.setTruth(false);

        opt63.setId(63);
        opt63.setOption("nherits");
        opt63.setTruth(false);


        quest7.setQuestion("Ktor� z nasleduj�cich mo�nost� vytv�ra objekt triedy Boat a prirad� ho do premennej sailBoat typu Boat?");
        quest7.setType(RADIO_TYPE);
        opt71.setId(71);
        opt71.setOption("Boat sailBoat = new Boat();");
        opt71.setTruth(true);

        opt72.setId(72);
        opt72.setOption("Boat sailBoat = Boat();");
        opt72.setTruth(false);

        opt73.setId(73);
        opt73.setOption("Boat = new Boat();");
        opt73.setTruth(false);

        opt74.setId(74);
        opt74.setOption("Boat sailBoat;");
        opt74.setTruth(false);


        quest8.setQuestion("Ktor� z nasleduj�cich tvrden� o abstraktn�ch triedach s� pravdiv�:");
        quest8.setType(RADIO_TYPE);
        opt81.setId(81);
        opt81.setOption("Trieda m��e dedi� od viacer�ch abstraktn�ch tried.");
        opt81.setTruth(false);

        opt82.setId(82);
        opt82.setOption("Abstraktn� trieda m��e obsahova� abstraktn� aj implementovan� met�dy.");
        opt82.setTruth(true);

        opt83.setId(83);
        opt83.setOption("V�etky met�dy uveden� v abstraktnej triede s� abstraktn�.");
        opt83.setTruth(false);


        quest9.setQuestion("Ako je mo�n� spr�stupni� posledn� element v nasleduj�com poli?\n" +
                "int[] autoMobile = new int[13];");
        quest9.setType(RADIO_TYPE);
        opt91.setId(91);
        opt91.setOption("autoMobile[13]");
        opt91.setTruth(false);

        opt92.setId(92);
        opt92.setOption("autoMobile[12]");
        opt92.setTruth(true);

        opt93.setId(93);
        opt93.setOption("autoMobile[14]");
        opt93.setTruth(false);

        opt94.setId(94);
        opt94.setOption("autoMobile[0]");
        opt94.setTruth(false);

        quest10.setQuestion("Čo bude vypísané na štandardný výstup?");
        quest10.setCode("public class Foo {\n" +
                "\tpublic static void main(String[]args) {\n" +
                "\tString s = \"Hello\";\n" +
                "modify(s);\n" +
                "System.out.println(s);\n" +
                "}\n" +
                "public static void modify(String s) {\n" +
                "s += \"World!\";\n" +
                "}\n" +
                "}");
        quest10.setType(CODE_TYPE);

        quest11.setQuestion("Čo bude vypísané na štandardný výstup?");

        quest11.setType(WRITE_TYPE);

        List<QuestionDto> questions = new LinkedList<QuestionDto>();

        quest1.setOptions(Arrays.asList(opt11,opt12,opt13,opt14));
        quest2.setOptions(Arrays.asList(opt21,opt22,opt23));
        quest3.setOptions(Arrays.asList(opt31,opt32,opt33,opt34));
        quest4.setOptions(Arrays.asList(opt41,opt42,opt43));
        quest5.setOptions(Arrays.asList(opt51,opt52,opt53,opt54));
        quest6.setOptions(Arrays.asList(opt61,opt62,opt63));
        quest7.setOptions(Arrays.asList(opt71,opt72,opt73,opt74));
        quest8.setOptions(Arrays.asList(opt81,opt82,opt83));
        quest9.setOptions(Arrays.asList(opt91,opt92,opt93,opt94));

        questions.add(quest1);
        questions.add(quest2);
        questions.add(quest3);
        questions.add(quest4);
        questions.add(quest5);
        questions.add(quest6);
        questions.add(quest7);
        questions.add(quest8);
        questions.add(quest9);
        questions.add(quest10);
        questions.add(quest11);

        test.setQuestions(questions);

        Map<Integer,List<Integer>> markedAnswers = new HashMap<Integer,List<Integer>>();

        markedAnswers.put(1,Arrays.asList(11,12,13));
        markedAnswers.put(2,Arrays.asList(23));
        markedAnswers.put(3,Arrays.asList(32));
        markedAnswers.put(4,Arrays.asList(41));
        markedAnswers.put(5,Arrays.asList(51,52));
        markedAnswers.put(6,Arrays.asList(61));
        markedAnswers.put(7,Arrays.asList(71));
        markedAnswers.put(8,Arrays.asList(81));
        markedAnswers.put(9,Arrays.asList(92));
        test.setMarkedAnswers(markedAnswers);
        return test;
    }

}
