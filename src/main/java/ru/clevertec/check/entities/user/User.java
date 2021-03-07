package ru.clevertec.check.entities.user;

import java.util.Map;

public class User {
    private int id;
    private String firstName;
    private String secondName;
    private int age;
    private Map<String, String> credentials;
    private UserType userType = UserType.USER;

    public User(int id, String firstName, String secondName, int age, Map<String, String> credentials, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.credentials = credentials;
        this.userType = userType;
    }

    public User(String firstName, String secondName, int age, Map<String, String> credentials, UserType userType) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.credentials = credentials;
        this.userType = userType;
    }
    public User(String firstName, String secondName, int age, Map<String, String> credentials) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.credentials = credentials;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, String> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map<String, String> credentials) {
        this.credentials = credentials;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", credentials=" + credentials +
                ", userType=" + userType +
                '}';
    }
}
