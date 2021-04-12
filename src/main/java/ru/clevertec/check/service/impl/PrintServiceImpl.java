package ru.clevertec.check.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import ru.clevertec.check.annotations.printer.PrinterAnnotation;
import ru.clevertec.check.exception.ProductExceptionConstants;
import ru.clevertec.check.exception.ServiceException;
import ru.clevertec.check.service.PrintService;

import java.io.*;

import static ru.clevertec.check.service.CheckConstants.*;
@PrinterAnnotation
@Service
public class PrintServiceImpl implements PrintService {
    @Override
    public void printCheck(StringBuilder sb) throws ServiceException {
        File file = new File(CHECKFILETXT);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            throw new ServiceException(ProductExceptionConstants.IOEXCEPTION);
        }
    }

    @Override
    public void printPDFCheck(StringBuilder sb) throws ServiceException {
        try {
            FileOutputStream file = new FileOutputStream(CHECKFILEPDF);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            document.newPage();
            document.add(new Paragraph(THRIPLE_INDENT));
            document.add(new Paragraph(sb.toString()));
            PdfReader reader = new PdfReader(new FileInputStream(PDFTEMPLATE));
            PdfImportedPage page = writer.getImportedPage(reader, PAGENUMBER);
            PdfContentByte pdfContentByte = writer.getDirectContentUnder();
            pdfContentByte.addTemplate(page, COORD_X, COORD_Y);
            document.close();
        } catch (DocumentException e) {
            throw new ServiceException(ProductExceptionConstants.DOCUMENT_EXCEPTION);
        } catch (FileNotFoundException e) {
            throw new ServiceException(ProductExceptionConstants.NO_FILE_EXCEPTION);
        } catch (IOException e) {
            throw new ServiceException(ProductExceptionConstants.IOEXCEPTION);
        }
    }
}
