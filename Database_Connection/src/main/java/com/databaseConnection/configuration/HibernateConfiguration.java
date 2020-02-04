package com.databaseConnection.configuration;

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

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.databaseConnection"})
public class HibernateConfiguration {

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    @Transactional
    public SessionFactory sessionFactory() {
        System.out.println("This is getSessionFactory method in class HibernateConfiguration");
        return new LocalSessionFactoryBuilder(dataSource()).scanPackages("com.databaseConnection.models").
                addProperties(hibernateProperties()).buildSessionFactory();
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUrl("jdbc:h2:~/Desktop/idea_projects/Database_Connection/test");
        dataSource.setDriver(new Driver());
        dataSource.setUsername("sa");
        System.out.println("dataSource bean in HibernateConfiguration " + dataSource);
        return dataSource;
    }

    @Bean
    public Properties hibernateProperties() {
        System.out.println("This is beginning of hibernateProperties in HibernateConfiguration");
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", "hibernate.show_sql");
        properties.put("hibernate.format_sql", "hibernate.format_sql");
        System.out.println("This is the end of hibernateProperties in HibernateConfiguration. Properties  is " + properties);
        return properties;
    }
}

