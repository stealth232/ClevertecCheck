package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.check.exception.ServiceException;
import ru.clevertec.check.observer.entity.State;
import ru.clevertec.check.service.MailService;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;

import static ru.clevertec.check.configuration.MailConfig.*;
import static ru.clevertec.check.exception.ProductExceptionConstants.*;
import static ru.clevertec.check.service.CheckConstants.CHECKFILEPDF;

@Slf4j
@AllArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    @Override
    public void sendMail() throws ServiceException {
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
                log.warn("Send successfully with PDF");
            } else {
                Message message = prepareMessageWithoutContent(session, MY_EMAIL_ACCOUNT, EMAIL_RECIPIENT);
                Transport.send(message);
                log.warn("Send successfully without PDF");
            }
        } catch (MessagingException e) {
            throw new ServiceException(MESSAGE_EXCEPTION, e);
        }
    }

    @Override
    public void sendMailToListener(String recipient, State eventType, String messageText, String filePath) throws ServiceException {
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
            log.warn("Mail with " + attach.getFileName() + " was successfully sent to " + recipient);
        } catch (MessagingException e) {
            throw new ServiceException(MESSAGE_EXCEPTION, e);
        } catch (IOException e) {
            throw new ServiceException(IOEXCEPTION, e);
        }
    }

    @Override
    public boolean sendMailToUser(String recipient) throws ServiceException {
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
            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBody = new MimeBodyPart();
            textBody.setText(date + "\n" + MESSAGE_WITH_ATTACH);
            MimeBodyPart attach = new MimeBodyPart();
            attach.attachFile(CHECKFILEPDF);
            emailContent.addBodyPart(textBody);
            emailContent.addBodyPart(attach);
            message.setContent(emailContent);
            Transport.send(message);
            log.warn("Mail with " + attach.getFileName() + " was successfully sent to " + recipient);
            return true;
        } catch (MessagingException e) {
            throw new ServiceException(MESSAGE_EXCEPTION, e);
        } catch (IOException e) {
            throw new ServiceException(IOEXCEPTION, e);
        }
    }

    private Message prepareMessageWithContent(Session session, String myAccountMail, String recipient) throws ServiceException {
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
            throw new ServiceException(MESSAGE_EXCEPTION, e);
        } catch (IOException e) {
            throw new ServiceException(IOEXCEPTION, e);
        }
    }

    private Message prepareMessageWithoutContent(Session session, String myAccountMail, String recipient) throws ServiceException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(SUBJECT);
            message.setText(MESSAGE_WITHOUT_ATTACH);
            return message;
        } catch (AddressException e) {
            throw new ServiceException(ADDRESS_EXCEPTION, e);
        } catch (MessagingException e) {
            throw new ServiceException(MESSAGE_EXCEPTION, e);
        }
    }
}
