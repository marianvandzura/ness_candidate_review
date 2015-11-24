package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.codec.PngImage;
import dto.CandidateDto;
import dto.OptionDto;
import dto.PdfTestDto;
import dto.QuestionDto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Peter on 8.11.2015.
 */
public class ITextPdf {

    private final static String FONT_AR = ".\\candidate_review\\arial.ttf";

    private final static String FONT_AR_BD = ".\\candidate_review\\arialbd.ttf";

    private String logoPath = ".\\candidate_review\\ness_logo.png";

    private BaseFont baseArial;

    private BaseFont baseArialBold;

    private Font arialFont10;

    private Font arialBoldFont10;

    private Font arialFont12;

    private Font arialBoldFont12;

    private Font arialFont14;

    private Font arialBoldFont14;

    private Document document;

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
    }

    private void drawHeader() throws DocumentException, IOException {
        Paragraph ph = new Paragraph(new Phrase("NESS Kosice Development Centre", arialFont12));
        PdfPCell cell = new PdfPCell(ph);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(1f);
        Image logoImage = PngImage.getImage(logoPath);
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

    private void drawCandidateInfos(final CandidateDto candidate) throws DocumentException {
        Paragraph namePhagraph = new Paragraph("Canidate name: ", arialFont12);
        namePhagraph.setTabSettings(new TabSettings(56f));
        namePhagraph.add(Chunk.TABBING);
        namePhagraph.add(new Chunk(candidate.getFirstName() + " " + candidate.getLastName()));
        document.add(namePhagraph);

        Paragraph mailPhagraph = new Paragraph("Mail: \t" + candidate.getEmail(), arialFont12);
        document.add(mailPhagraph);

        Paragraph datePhagraph = new Paragraph("Date: \t" + candidate.getDate(), arialFont12);
        document.add(datePhagraph);

        Paragraph descPhagraph = new Paragraph("Test description: \t" + candidate.getTestName(), arialFont12);
        document.add(descPhagraph);

        Paragraph resultPhagraph = new Paragraph("Result: \t" + candidate.getTestResult(), arialFont12);
        document.add(resultPhagraph);

    }

    public void createPdf(final CandidateDto candidate)
            throws DocumentException, IOException {

        PdfWriter.getInstance(document, new FileOutputStream("E:\\iText.pdf"));
        document.open();
        drawHeader();
        drawCandidateInfos(candidate);

        printQuestions(initDto());

        Paragraph paragraph;

        document.close();
    }

    private void printQuestions(PdfTestDto test) throws DocumentException {
        Paragraph preface = new Paragraph("Hello World!",this.arialBoldFont12);
        preface.setAlignment(Element.ALIGN_CENTER);
        document.add(preface);
        document.newPage();

        List<QuestionDto> questions = test.getQuestions();

        Paragraph questionParagraph;
        Paragraph optionParagraph;

//        PdfPTable table = new PdfPTable(1);
        PdfPCell cell1;
        Chunk chunk;
        PdfPCell cell2 = new PdfPCell();
//        cell1.setFixedHeight((PageSize.A4.getHeight() / 2) - 70);
//        cell1.setBorder(Rectangle.BOTTOM);
//        cell1.setBorderColor(BaseColor.BLACK);
//        cell1.addElement(new Paragraph("ssssss",this.arialBoldFont10));
//        cell2.setFixedHeight((PageSize.A4.getHeight() / 2) - 70);
//        cell2.setBorder(Rectangle.BOTTOM);
//        cell2.setBorderColor(BaseColor.BLACK);
//        cell2.addElement(new Paragraph("AAAAAA", this.arialBoldFont10));


        int questionCounter = 1;
        for(QuestionDto question : questions) {
            PdfPTable table = new PdfPTable(1);
            cell1 = new PdfPCell();
            questionParagraph = new Paragraph(questionCounter++ + ". " + question.getQuestion(), this.arialBoldFont10);
            cell1.addElement(questionParagraph);
            cell1.addElement(Chunk.NEWLINE);
            for(OptionDto option : question.getOptions()) {
                //if(){
                // chunk = new Chunk(option.getOption(),arialFont10);
                // chunk.setBackground(BaseColor.RED);
                // optionParagraph = new Paragraph(chunk);
                // }else {
                optionParagraph = new Paragraph();//option.getOption(), this.arialFont10);
                optionParagraph.setTabSettings(new TabSettings(25f));
                optionParagraph.add(Chunk.TABBING);
                optionParagraph.add(new Chunk(option.getOption(),this.arialFont10));
                //}
               cell1.addElement(optionParagraph);
            }
            cell1.setFixedHeight((PageSize.A4.getHeight() / 2) - 70);
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setBorderColor(BaseColor.BLACK);
            table.addCell(cell1);
            document.add(table);
        }

    }

    private PdfTestDto initDto(){
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
        quest7.setId(9);
        quest8.setId(10);
        quest9.setId(11);
        quest10.setId(12);
        quest11.setId(13);
        quest12.setId(14);
        quest13.setId(15);

        quest1.setQuestion("Which of these lines will compile? Select the four correct answers.");
        opt11.setId(11);
        opt11.setOption("short s = 20;");
        opt11.setTruth(true);

        opt12.setId(12);
        opt12.setOption("byte b = 128;");
        opt12.setTruth(false);

        opt13.setId(13);
        opt13.setOption("char c = 32");
        opt13.setTruth(true);

        opt14.setId(14);
        opt14.setOption("double d = 1.4");
        opt14.setTruth(true);


        quest2.setQuestion("Objekt pozost·va z:");
        opt21.setId(21);
        opt21.setOption("MetÛd a oper·ciÌ.");
        opt21.setTruth(false);

        opt22.setId(22);
        opt22.setOption("Atrib˙tov a premenn˝ch.");
        opt22.setTruth(false);

        opt23.setId(23);
        opt23.setOption("Premenn˝ch a metÛd.");
        opt23.setTruth(true);


        quest3.setQuestion("Kaûd˝ program v jazyku Java m· k dispozÌcii 3 ötandardnÈ I/O objekty. KtorÈ s˙ to?\n" +
                "Vyberte aspoÚ jednu odpoveÔ.");
        opt31.setId(31);
        opt31.setOption("System1.out, System2.out, System3.out");
        opt31.setTruth(false);

        opt32.setId(13);
        opt32.setOption("System.in, System.out, System.err");
        opt32.setTruth(true);

        opt33.setId(33);
        opt33.setOption("Main.in, Main.out, Main.err");
        opt33.setTruth(false);

        opt34.setId(34);
        opt34.setOption("Console.in, Console.out, Console.exe");
        opt34.setTruth(false);


        quest4.setQuestion("KtorÈ z nasleduj˙cich tvrdenÌ je pravdivÈ o System.in?\n" +
                "Vyberte aspoÚ jednu odpoveÔ.");
        opt41.setId(41);
        opt41.setOption("Je to objekt typu InputStream predstavuj˙ci ötandardn˝ vstup.");
        opt41.setTruth(true);

        opt42.setId(42);
        opt42.setOption("Je to objekt triedy FileReader predstavuj˙ci ötandardn˝ vstup.");
        opt42.setTruth(false);

        opt43.setId(43);
        opt43.setOption("Je to objekt typu InputStreamReader predstavuj˙ci ötandardn˝ vstup.");
        opt34.setTruth(false);


        quest5.setQuestion("KtorÈ z nasleduj˙cich tvrdenÌ o serializ·cii objektov s˙ pravdivÈ.");
        opt51.setId(51);
        opt51.setOption("Trieda, ktorej objekt chceme serializovaù musÌ implementovaù rozhranie\n" +
                "Serializable.");
        opt51.setTruth(true);

        opt52.setId(52);
        opt52.setOption("Serializ·cia je proces transform·cie stavu objektu do serializovanej formy takej, aby\n" +
                "bolo moûnÈ zrekonötruovaù objekt.");
        opt52.setTruth(true);

        opt53.setId(53);
        opt53.setOption("Trieda, ktorej objekt chceme serializovaù musÌ dediù od triedy Serializable.");
        opt53.setTruth(false);

        opt54.setId(54);
        opt54.setOption("Objekty s veæk˝m poËtom metÛd nie je moûnÈ serializovaù.");
        opt54.setTruth(false);


        quest6.setQuestion("KtorÈ z nasleduj˙cich kæ˙Ëov˝ch slov programovacieho jazyka Java indikuje dediËnosù?");
        opt61.setId(61);
        opt61.setOption("extends");
        opt61.setTruth(true);

        opt62.setId(62);
        opt62.setOption("import");
        opt62.setTruth(false);

        opt63.setId(63);
        opt63.setOption("nherits");
        opt63.setTruth(false);


        quest7.setQuestion("Ktor· z nasleduj˙cich moûnostÌ vytv·ra objekt triedy Boat a priradÌ ho do premennej sailBoat typu Boat?");
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


        quest8.setQuestion("KtorÈ z nasleduj˙cich tvrdenÌ o abstraktn˝ch triedach s˙ pravdivÈ:");
        opt81.setId(81);
        opt81.setOption("Trieda mÙûe dediù od viacer˝ch abstraktn˝ch tried.");
        opt81.setTruth(false);

        opt82.setId(82);
        opt82.setOption("Abstraktn· trieda mÙûe obsahovaù abstraktnÈ aj implementovanÈ metÛdy.");
        opt82.setTruth(true);

        opt83.setId(83);
        opt83.setOption("Vöetky metÛdy uvedenÈ v abstraktnej triede s˙ abstraktnÈ.");
        opt83.setTruth(false);


        quest9.setQuestion("Ako je moûnÈ sprÌstupniù posledn˝ element v nasleduj˙com poli?\n" +
                "int[] autoMobile = new int[13];");
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

        test.setQuestions(questions);

        return test;
    }

}
