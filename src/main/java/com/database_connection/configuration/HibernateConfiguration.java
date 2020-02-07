package com.database_connection.configuration;

import lombok.extern.java.Log;
import org.h2.Driver;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.database_connection"})
@Log
public class HibernateConfiguration {

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    @Transactional
    public SessionFactory sessionFactory() {
        log.info("MYYYYY LOG: This is getSessionFactory method in class HibernateConfiguration");
        return new LocalSessionFactoryBuilder(dataSource()).scanPackages("com.database_connection.model").
                addProperties(hibernateProperties()).buildSessionFactory();
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUrl("jdbc:h2:~/Desktop/idea_projects/Web-dkropotov2/test");
        dataSource.setDriver(new Driver());
        dataSource.setUsername("sa");
        log.info("MYYYYY LOG: dataSource bean in HibernateConfiguration " + dataSource);
        return dataSource;
    }

    @Bean
    public Properties hibernateProperties() {
        log.info("MYYYYY LOG: This is beginning of hibernateProperties in HibernateConfiguration");
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", "hibernate.show_sql");
        properties.put("hibernate.format_sql", "hibernate.format_sql");
        log.info("MYYYYY LOG: This is the end of hibernateProperties in HibernateConfiguration. Properties is " + properties);
        return properties;
    }
}

