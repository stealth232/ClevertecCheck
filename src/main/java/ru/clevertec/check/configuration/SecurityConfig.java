package ru.clevertec.check.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.clevertec.check.security.AuthProviderImpl;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@ComponentScan("ru.clevertec.check.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthProviderImpl authProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/*").authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .usernameParameter("login")
                .defaultSuccessUrl("/authRouter")
                .failureUrl("/login?error=true")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/admin_access")
                .and().logout();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }
}
