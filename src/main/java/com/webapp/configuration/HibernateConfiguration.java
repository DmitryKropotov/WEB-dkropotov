package com.webapp.configuration;

import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.webapp"})
@Log
@PropertySource("classpath:db.properties")
public class HibernateConfiguration {

    @Value("${url}")
    private String url;
    @Value("${username}")
    private String username;
    @Value("${driverClassName}")
    private String driverClassName;

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SessionFactory sessionFactory() {
        log.info("MYYYYY LOG: This is getSessionFactory method in class HibernateConfiguration");
        return new LocalSessionFactoryBuilder(dataSource()).scanPackages("com.webapp.model").
                addProperties(hibernateProperties()).buildSessionFactory();
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        try {
            dataSource.setUrl(url);
            dataSource.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
            dataSource.setUsername(username);
        } catch (ClassNotFoundException e) {
            log.warning("MYYYYY LOG: " + e);
            return null;
        }
        log.info("MYYYYY LOG: dataSource bean in class HibernateConfiguration " + dataSource);
        return dataSource;
    }

    @Bean
    public Properties hibernateProperties() {
        log.info("MYYYYY LOG: This is beginning of hibernateProperties in class HibernateConfiguration");
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", "hibernate.show_sql");
        properties.put("hibernate.format_sql", "hibernate.format_sql");
        log.info("MYYYYY LOG: This is the end of hibernateProperties in class HibernateConfiguration. Properties is " + properties);
        return properties;
    }
}
