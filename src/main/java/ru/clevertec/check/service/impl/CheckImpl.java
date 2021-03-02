package ru.clevertec.check.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import ru.clevertec.check.annotations.log.LogMe;
import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.entities.Card;
import ru.clevertec.check.entities.parameters.ProductParameters;
import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.observer.Publisher;
import ru.clevertec.check.service.Check;

import javax.mail.MessagingException;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ru.clevertec.check.exception.ProductExceptionConstants.*;
import static ru.clevertec.check.observer.entity.State.*;
import static ru.clevertec.check.service.CheckConstants.*;

public class CheckImpl implements Check {

    private Publisher publisher = new Publisher(CHECK_WAS_PRINTED_IN_PDF, CHECK_WAS_PRINTED_IN_TXT, CHECK_HAS_NOT_PRINTED);
    private Map<String, Integer> map;
    private Repository repository = Repository.getInstance();

    public CheckImpl(Map<String, Integer> map) {
        this.map = map;
    }

    @LogMe
    @Override
    public StringBuilder showCheck(List<ProductParameters> list) {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        Card card = new Card(0);
        sb.append("\n\n              CASH RECEIPT\n\n").
                append("       supermarket 'The Two Geese' \n").
                append("      " + date + "\n\n").
                append("QTY  DESCRIPTION            PRICE   TOTAL \n");
        int key;
        int quantity;
        double totalPriceProduct;
        double totalPrice = 0;
        double discount = 0;
        String line;
        int repositorySize = repository.getSize();
        for (Map.Entry<String, Integer> entry : map.entrySet()
        ) {
            if (!entry.getKey().equals(CARD)) {
                for (int i = 0; i <= list.size() - 1; i++) {
                    key = Integer.parseInt(entry.getKey());
                    if (list.get(i).getItemId() == key) {
                        quantity = entry.getValue();
                        if (list.get(i).isStock() && quantity >= repositorySize) {
                            totalPriceProduct = list.get(i).getCost() * quantity * PERCENT90;
                        } else {
                            totalPriceProduct = list.get(i).getCost() * quantity;
                        }
                        line = String.format("%-3d  %-18s %8.2f %8.2f \n", quantity,
                                list.get(i).getName(), list.get(i).getCost(), totalPriceProduct);
                        totalPrice += totalPriceProduct;
                        discount += list.get(i).getCost() * quantity - totalPriceProduct;
                        sb.append(line);
                    }
                }
            } else {
                card = new Card(entry.getValue());
            }
        }
        sb.append(TRANSFER);
        if (card.getNumber() == CARD_RANGE_0) {
            sb.append("No Discount Card ");
            sb.append(String.format(DISCOUNT, discount));
            sb.append(String.format(PRICE, totalPrice));
        } else {

            if (card.getNumber() > CARD_RANGE_0 && card.getNumber() < CARD_RANGE_100) {
                sb.append(" \nYour Card with 3% Discount № " + card.getNumber());
                sb.append(String.format(DISCOUNT, discount + totalPrice * PERCENT3));
                sb.append(String.format(PRICE, totalPrice * PERCENT97));
            }
            if (card.getNumber() >= CARD_RANGE_100 && card.getNumber() < CARD_RANGE_1000) {
                sb.append(" \nYour Card with 4% Discount № " + card.getNumber());
                sb.append(String.format(DISCOUNT, discount + totalPrice * PERCENT4));
                sb.append(String.format(PRICE, totalPrice * PERCENT96));
            }
            if (card.getNumber() >= CARD_RANGE_1000) {
                sb.append(" \nYour Card with 5% Discount № " + card.getNumber());
                sb.append(String.format(DISCOUNT, discount + totalPrice * PERCENT5));
                sb.append(String.format(PRICE, totalPrice * PERCENT95));
            }
        }
        return sb;

    }

    @Override
    public StringBuilder htmlCheck(List<ProductParameters> list) {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        Card card = new Card(0);
        sb.append(HTML_OPEN).
                append("<head><h2><pre>        CASH RECEIPT</pre></h2></head>").
                append("<pre>        supermarket 'The Two Geese'</pre> ").
                append(String.format("<pre>       %s </pre>    ", date.toString())).
                append("<h3><pre>QTY DESCRIPTION          PRICE   TOTAL </pre></h3>");
        int key;
        int quantity;
        double totalPriceProduct;
        double totalPrice = 0;
        double discount = 0;
        int repositorySize = repository.getSize();
        String line;
        for (Map.Entry<String, Integer> entry : map.entrySet()
        ) {
            if (!entry.getKey().equals(CARD)) {
                for (int i = 0; i <= list.size() - 1; i++) {
                    key = Integer.parseInt(entry.getKey());
                    if (list.get(i).getItemId() == key) {
                        quantity = entry.getValue();
                        if (list.get(i).isStock() && quantity >= repositorySize) {
                            totalPriceProduct = list.get(i).getCost() * quantity * PERCENT90;
                        } else {
                            totalPriceProduct = list.get(i).getCost() * quantity;
                        }
                        line = String.format("<pre>%-3d  %-18s   %8.2f %8.2f </pre>", quantity,
                                list.get(i).getName(), list.get(i).getCost(), totalPriceProduct);
                        totalPrice += totalPriceProduct;
                        discount += list.get(i).getCost() * quantity - totalPriceProduct;
                        sb.append(line);
                    }
                }
            } else {
                card = new Card(entry.getValue());
            }
        }
        sb.append(TRANSFER);
        if (card.getNumber() == CARD_RANGE_0) {
            sb.append("<pre>No Discount Card </pre>");
            sb.append(String.format(DISCOUNT_HTML, discount));
            sb.append(String.format(PRICE_HTML, totalPrice));
        } else {
            if (card.getNumber() > CARD_RANGE_0 && card.getNumber() < CARD_RANGE_100) {
                sb.append("<pre>Your Card with 3% Discount: </pre>" + card.getNumber());
                sb.append(String.format(DISCOUNT_HTML, discount + totalPrice * PERCENT3));
                sb.append(String.format(PRICE_HTML, totalPrice * PERCENT97));
            }
            if (card.getNumber() >= CARD_RANGE_100 && card.getNumber() < CARD_RANGE_1000) {
                sb.append("<pre>Your Card with 4% Discount: </pre>" + card.getNumber());
                sb.append(String.format(DISCOUNT_HTML, discount + totalPrice * PERCENT4));
                sb.append(String.format(PRICE_HTML, totalPrice * PERCENT96));
            }
            if (card.getNumber() >= CARD_RANGE_1000) {
                sb.append("<pre>Your Card with 5% Discount: </pre>" + card.getNumber());
                sb.append(String.format(DISCOUNT_HTML, discount + totalPrice * PERCENT5));
                sb.append(String.format(PRICE_HTML, totalPrice * PERCENT95));
            }
        }
        sb.append(HTML_CLOSE);
        return sb;

    }

    @Override
    public void printCheck(StringBuilder sb) throws ProductException {
        File file = new File(CHECKFILETXT);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(sb.toString());
            writer.close();
            publisher.notify(CHECK_WAS_PRINTED_IN_TXT, "Check was printed in TXT file");
        } catch (IOException | MessagingException e) {
            throw new ProductException(IOEXCEPTION);
        }
    }

    @Override
    public void printPDFCheck(StringBuilder sb) throws ProductException {
        try {
            FileOutputStream file = new FileOutputStream(CHECKFILEPDF);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            document.newPage();
            document.add(new Paragraph(INDENT));
            document.add(new Paragraph(sb.toString()));
            PdfReader reader = new PdfReader(new FileInputStream(PDFTEMPLATE));
            PdfImportedPage page = writer.getImportedPage(reader, PAGENUMBER);
            PdfContentByte pdfContentByte = writer.getDirectContentUnder();
            pdfContentByte.addTemplate(page, COORD_X, COORD_Y);
            document.close();
            publisher.notify(CHECK_WAS_PRINTED_IN_PDF, "Check was printed in PDF file");
        } catch (DocumentException e) {
            throw new ProductException(DOCUMENT_EXCEPTION);
        } catch (FileNotFoundException e) {
            throw new ProductException(NO_FILE_EXCEPTION);
        } catch (IOException | MessagingException e) {
            throw new ProductException(IOEXCEPTION);
        }
    }

    @Override
    public StringBuilder pdfCheck(List<ProductParameters> list) {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        Card card = new Card(0);
        sb.append(DOUBLE_INDENT + TRANSPORT + TRANSPORT_HALF + "      CASH RECEIPT" + DOUBLE_INDENT).
                append(TRANSPORT + TRANSPORT_HALF + "  supermarket 'The Two Geese' \n").
                append(TRANSPORT + TRANSPORT_HALF + date + DOUBLE_INDENT).
                append(TRANSPORT + "QTY        DESCRIPTION            PRICE         TOTAL \n");

        int key;
        int quantity;
        double totalPriceProduct;
        double totalPrice = 0;
        double discount = 0;
        String line;
        int repositorySize = repository.getSize();
        for (Map.Entry<String, Integer> entry : map.entrySet()
        ) {
            if (!entry.getKey().equals(CARD)) {
                for (int i = 0; i <= list.size() - 1; i++) {
                    key = Integer.parseInt(entry.getKey());
                    if (list.get(i).getItemId() == key) {
                        quantity = entry.getValue();
                        if (list.get(i).isStock() && quantity >= repositorySize) {
                            totalPriceProduct = list.get(i).getCost() * quantity * PERCENT90;
                        } else {
                            totalPriceProduct = list.get(i).getCost() * quantity;
                        }
                        line = String.format(" %43d  %18s %27.2f  %14.2f \n", quantity,
                                list.get(i).getName(), list.get(i).getCost(), totalPriceProduct);
                        totalPrice += totalPriceProduct;
                        discount += list.get(i).getCost() * quantity - totalPriceProduct;
                        sb.append(line);
                    }
                }
            } else {
                card = new Card(entry.getValue());
            }
        }
        sb.append(TRANSPORT + TRANSFER_PDF);
        double priceDiscount = 0;
        if (card.getNumber() == CARD_RANGE_0) {
            priceDiscount = discount;
            sb.append("\n" + TRANSPORT + "No Discount Card");
            sb.append(String.format(DISCOUNT_PDF_FORMAT, DISCOUNT_PDF, priceDiscount));
            sb.append(String.format(PRICE_PDF_FORMAT, TOTAL_PRICE_PDF, totalPrice - priceDiscount));
        } else {
            if (card.getNumber() > CARD_RANGE_0 && card.getNumber() < CARD_RANGE_100) {
                priceDiscount = discount + totalPrice * PERCENT3;
                sb.append("\n" + TRANSPORT + "Your Card with 3% Discount Number " + card.getNumber());
                sb.append(String.format(DISCOUNT_PDF_FORMAT, DISCOUNT_PDF, priceDiscount));
                sb.append(String.format(PRICE_PDF_FORMAT, TOTAL_PRICE_PDF, totalPrice - priceDiscount));
            }
            if (card.getNumber() >= CARD_RANGE_100 && card.getNumber() < CARD_RANGE_1000) {
                priceDiscount = discount + totalPrice * PERCENT4;
                sb.append("\n" + TRANSPORT + "Your Card with 4% Discount Number " + card.getNumber());
                sb.append(String.format(DISCOUNT_PDF_FORMAT, DISCOUNT_PDF, priceDiscount));
                sb.append(String.format(PRICE_PDF_FORMAT, TOTAL_PRICE_PDF, totalPrice - priceDiscount));
            }
            if (card.getNumber() >= CARD_RANGE_1000) {
                priceDiscount = discount + totalPrice * PERCENT5;
                sb.append("\n" + TRANSPORT + "Your Card with 5% Discount Number " + card.getNumber());
                sb.append(String.format(DISCOUNT_PDF_FORMAT, DISCOUNT_PDF, priceDiscount));
                sb.append(String.format(PRICE_PDF_FORMAT, TOTAL_PRICE_PDF, totalPrice - priceDiscount));
            }
        }
        return sb;
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

}
