package ru.clevertec.check.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_security")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 20, message = "Name must contains 2 - 20 symbols")
    private String firstName;

    @Column(name = "second_name")
    @NotBlank(message = "Second name must not be blank")
    @Size(min = 2, max = 20, message = "Second name must contains 2 - 20 symbols")
    private String secondName;

    @Column(name = "age")
    @Min(value = 10, message = "Age must not be less than 10")
    @Max(value = 150, message = "Age must not be greater than 150")
    private int age;

    @Column(name = "login")
    @NotBlank(message = "Login must not be blank")
    @Size(min = 2, max = 20, message = "Login must contains 2 - 20 symbols")
    private String login;

    @Column(name = "password")
    @NotBlank(message = "Password must not be blank")
    @Size(min = 3, message = "Password must contains min 6 symbols")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_security_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "\n" + "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
