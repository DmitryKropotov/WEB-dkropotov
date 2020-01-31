package com.project.main;

//import com.project.configuration.HibernateConfiguration;
import com.project.configuration.AppConfig;
import com.project.models.User;
import com.project.repositories.UserRepository;
import com.project.repositories.UserRepositoryImpl;
import com.project.service.UserService;
import com.project.service.UserServiceImpl;
import com.sun.tools.javac.code.Attribute;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.hibernate.SessionFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.lang.Class;
import java.util.List;
import java.util.Optional;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void main(String[] args) {


        for (int i = 0; i < appContext.getBeanDefinitionNames().length; i++) {
            System.out.println(appContext.getBeanDefinitionNames()[i]);
        }
        UserService userService = (UserService) appContext.getBean("userServiceImpl");
        //Object userRepositoryProxy = appContext.getBean("userRepositoryImpl");
        //userService.setUserRepository(userRepository);
        //if (userServiceProxy instanceof UserServiceImpl) {
            //UserServiceImpl userServiceImpl = (UserServiceImpl) userService;
            System.out.println(userService.getUserRepository());
            userService.createUser("Grid@Dynamics.com", "grid");
        /*} else {
            System.out.println(userServiceProxy.getClass());
        }*/
        //UserRepositoryImpl userRepository = new UserRepositoryImpl((SessionFactory) appContext.getBean("sessionFactory"));
        //userRepository.createUser("John@Doe.com", "john");
        //userRepository.getSessionFactory();

        Optional<User> optionalUser = userService.selectUserByEmail("Grid@Dynamics.com");
        System.out.println("We got optionalUser");
        System.out.println("OptionalUser is " + optionalUser);
        System.out.println(optionalUser.isPresent() ? optionalUser.get(): "There is no user with email Grid@Dynami]]cs.com");

        List<User> users = userService.selectAllUsers();
        users.forEach(System.out::println);
    }
}
