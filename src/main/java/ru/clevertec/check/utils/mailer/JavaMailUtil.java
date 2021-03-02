package ru.clevertec.check.utils.mailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.observer.entity.State;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Date;

import static ru.clevertec.check.exception.ProductExceptionConstants.*;
import static ru.clevertec.check.service.CheckConstants.CHECKFILEPDF;
import static ru.clevertec.check.utils.mailer.JavaMailProperties.*;

public class JavaMailUtil {
    private static Logger logger = LogManager.getLogger();

    public static void sendMail() throws ProductException {
        logger.info("Preparing to send");
        try {
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MY_EMAIL_ACCOUNT, PASSWORD);
                }
            });
            if (Boolean.parseBoolean(ATTACH)) {
                Message message = prepareMessageWithContent(session, MY_EMAIL_ACCOUNT, EMAIL_RECIPIENT);
                Transport.send(message);
                logger.info("Send successfully with PDF");
            } else {
                Message message = prepareMessageWithoutContent(session, MY_EMAIL_ACCOUNT, EMAIL_RECIPIENT);
                Transport.send(message);
                logger.info("Send successfully without PDF");
            }
        } catch (MessagingException e) {
            throw new ProductException(MESSAGE_EXCEPTION, e);
        }
    }

    public static void sendMailToListener(String recipient, State eventType, String messageText, String filePath) throws ProductException {
        try {
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MY_EMAIL_ACCOUNT, PASSWORD);
                }
            });
            Date date = new Date();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MY_EMAIL_ACCOUNT));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(eventType.toString());
            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBody = new MimeBodyPart();
            textBody.setText(date + "\n" + messageText);
            MimeBodyPart attach = new MimeBodyPart();
            attach.attachFile(filePath);
            emailContent.addBodyPart(textBody);
            emailContent.addBodyPart(attach);
            message.setContent(emailContent);
            Transport.send(message);
            logger.info("Mail with " + attach.getFileName() + " was successfully sent to " + recipient);
        } catch (MessagingException e) {
            throw new ProductException(MESSAGE_EXCEPTION, e);
        } catch (IOException e) {
            throw new ProductException(IOEXCEPTION, e);
        }
    }

    private static Message prepareMessageWithContent(Session session, String myAccountMail, String recipient) throws ProductException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(SUBJECT);
            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBody = new MimeBodyPart();
            textBody.setText(MESSAGE_WITH_ATTACH);
            MimeBodyPart pdfAttach = new MimeBodyPart();
            pdfAttach.attachFile(CHECKFILEPDF);
            emailContent.addBodyPart(textBody);
            emailContent.addBodyPart(pdfAttach);
            message.setContent(emailContent);
            return message;
        } catch (MessagingException e) {
            throw new ProductException(MESSAGE_EXCEPTION, e);
        } catch (IOException e) {
            throw new ProductException(IOEXCEPTION, e);
        }
    }

    private static Message prepareMessageWithoutContent(Session session, String myAccountMail, String recipient) throws ProductException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(SUBJECT);
            message.setText(MESSAGE_WITHOUT_ATTACH);
            return message;
        } catch (AddressException e) {
            throw new ProductException(ADDRESS_EXCEPTION, e);
        } catch (MessagingException e) {
            throw new ProductException(MESSAGE_EXCEPTION, e);
        }
    }
}
