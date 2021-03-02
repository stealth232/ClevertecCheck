package ru.clevertec.check.utils.mailer;

import java.io.*;
import java.util.Properties;

public class JavaMailProperties {
    static final String PROPS_FILE = "c:/Users/Stealth/IdeaProjects/NewCheck/src/main/resources/config.properties";
    static Properties properties = getProperties(PROPS_FILE);
    static final String MY_EMAIL_ACCOUNT = properties.getProperty("myAccountEmail");
    static final String EMAIL_RECIPIENT = properties.getProperty("toMail");
    static final String PASSWORD = properties.getProperty("password");
    static final String MESSAGE_WITH_ATTACH = properties.getProperty("messageWithAttach");
    static final String MESSAGE_WITHOUT_ATTACH = properties.getProperty("messageWithoutAttach");
    static final String SUBJECT = properties.getProperty("subject");
    static final String ATTACH = properties.getProperty("withAttach");
    public static final String MAIL_STEALTH = "stealth2322@gmail.com";
    public static final String MAIL_WHITE_RUSSIAN = "whiterussian2018@gmail.com";

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
