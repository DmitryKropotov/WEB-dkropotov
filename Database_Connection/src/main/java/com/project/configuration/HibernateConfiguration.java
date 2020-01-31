package com.project.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.h2.Driver;
import java.util.Properties;

@Configuration
//@AnnotationDrivenConfig
@EnableTransactionManagement
@ComponentScan({ "com.project" })
//@PropertySource(value = { "classpath:app.properties" })
public class HibernateConfiguration {

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    @Transactional
    public SessionFactory sessionFactory() {
        System.out.println("This is getSessionFactory method in class HibernateConfiguration");
        return new LocalSessionFactoryBuilder(dataSource()).scanPackages("com.project.models").
                addProperties(hibernateProperties()).buildSessionFactory();
    }

    /*public LocalSessionFactoryBean localSessionFactory() {
        System.out.println("This is localSessionFactory bean method");
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        //localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setPackagesToScan("com.project.model");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties());
        System.out.println("localSessionFactoryBean is " + localSessionFactoryBean);
        return localSessionFactoryBean;
    }

    public AnnotationSessionFactoryBean annotationSessionFactoryBean() {
        System.out.println("This is annotationSessionFactoryBean bean method");
        AnnotationSessionFactoryBean annotationSessionFactoryBean = new AnnotationSessionFactoryBean();
        annotationSessionFactoryBean.setDataSource(dataSource());
        annotationSessionFactoryBean.setPackagesToScan("com.project.model");
        annotationSessionFactoryBean.setHibernateProperties(hibernateProperties());
        System.out.println("annotationSessionFactoryBean is " + annotationSessionFactoryBean);
        return annotationSessionFactoryBean;
    }*/

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        //dataSource.setDriverClassName("jdbc:sqlite:shop.db");
        /*try {
            DriverManager.registerDriver(new JDBC());
            Class.forName("jdbc:sqlite:shop.db");
            Class driver = DriverManager.class;
            dataSource.setDriverClass(driver);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
        //dataSource.setUrl("jdbc:h2:~/test");
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
        //properties.put("driver", "org.h2.Driver");
        System.out.println("This is the end of hibernateProperties in HibernateConfiguration. Properties  is " + properties);
        return properties;
    }
}

