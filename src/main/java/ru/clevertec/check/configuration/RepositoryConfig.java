package ru.clevertec.check.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
@ComponentScan("ru.clevertec.check")
@PropertySource("classpath:properties/repository.yml")
public class RepositoryConfig {
    final private Environment environment;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(environment.getProperty("database.driver"));
        hikariConfig.setJdbcUrl(environment.getProperty("database.url"));
        hikariConfig.setUsername(environment.getProperty("database.user"));
        hikariConfig.setPassword(environment.getProperty("database.password"));
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
