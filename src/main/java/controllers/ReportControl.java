package main.java.controllers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class ReportControl {
    public ReportControl() {
    }

    public void printPDF(String[] text, String path) {
        try {
            PDDocument doc = new PDDocument();
            PDPage myPage = new PDPage();
            doc.addPage(myPage);
            PDPageContentStream contentStream = new PDPageContentStream(doc, myPage);
            PDRectangle rect = myPage.getMediaBox();
            for (int i = 0; i < text.length; i++) {
                String line = text[i];
                line = line.replaceAll("[^a-zA-Z0-9]+", " ");
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(100, rect.getHeight() - 50 * (i));
                contentStream.showText(line);
                contentStream.newLine();
                contentStream.endText();
            }
            contentStream.close();
            doc.save(path);
            doc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
