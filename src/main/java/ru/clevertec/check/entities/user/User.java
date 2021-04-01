package ru.clevertec.check.entities.user;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private int id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 20, message = "Name must contains 2 - 20 symbols")
    private String firstName;

    @NotBlank(message = "Second name must not be blank")
    @Size(min = 2, max = 20, message = "Second name must contains 2 - 20 symbols")
    private String secondName;

    @Min(value = 10, message = "Age must not be less than 10")
    @Max(value = 150, message = "Age must not be greater than 150")
    private int age;

    @NotBlank(message = "Login must not be blank")
    @Size(min = 2, max = 20, message = "Login must contains 2 - 20 symbols")
    private String login;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 3, message = "Password must contains min 6 symbols")
    private String password;

    private UserType userType = UserType.USER;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
       this.userType = UserType.valueOf(userType);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", login=" + login +
                ", userType=" + userType +
                '}';
    }
}
