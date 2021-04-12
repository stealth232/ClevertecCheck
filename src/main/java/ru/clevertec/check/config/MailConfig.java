package ru.clevertec.check.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.*;
import java.util.Properties;

@AllArgsConstructor
@Configuration
@ComponentScan("ru.clevertec.check")
@PropertySource("classpath:properties/mail.yml")
public class MailConfig {
    public static final String PROPS_FILE = "c:/Users/Stealth/IdeaProjects/NewCheck/src/main/resources/properties/mail.yml";
    public static Properties properties = getProperties(PROPS_FILE);
    public static final String MY_EMAIL_ACCOUNT = properties.getProperty("myAccountEmail");
    public static final String EMAIL_RECIPIENT = properties.getProperty("toMail");
    public static final String PASSWORD = properties.getProperty("password");
    public static final String MESSAGE_WITH_ATTACH = properties.getProperty("messageWithAttach");
    public static final String MESSAGE_WITHOUT_ATTACH = properties.getProperty("messageWithoutAttach");
    public static final String SUBJECT = properties.getProperty("subject");
    public static final String ATTACH = properties.getProperty("withAttach");

    public static Properties getProperties(String emailPropertiesFilePath) {
        try (InputStream inputStream = new FileInputStream(emailPropertiesFilePath)) {
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
