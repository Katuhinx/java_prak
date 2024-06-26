package com.pismennaya.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class HibernateDatabaseConfig {
    @Value("${spring.datasource.driver}")
    private String DB_DRIVER;
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String DB_USERNAME;
    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(oraDataSource());
        sessionFactory.setPackagesToScan("com.pismennaya.shop.models");

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.ddl-auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("connection_pool_size", "10");
        hibernateProperties.setProperty("hibernate.current_session_context_class", "thread");
        
        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }

    @Bean
    public DataSource oraDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);

        return dataSource;
    }
}