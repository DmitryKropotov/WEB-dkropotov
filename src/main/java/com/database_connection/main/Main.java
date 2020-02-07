package com.database_connection.main;

import com.database_connection.configuration.AppConfig;
import com.database_connection.model.User;
import com.database_connection.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

public class Main {

    private static ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void main(String[] args) {


        for (int i = 0; i < appContext.getBeanDefinitionNames().length; i++) {
            System.out.println(appContext.getBeanDefinitionNames()[i]);
        }
        UserService userService = (UserService) appContext.getBean("userServiceImpl");
        System.out.println(userService.getUserRepository());
        userService.createUser("Grid@Dynamicus.com", "grid");

        Optional<User> optionalUser = userService.findByEmail("Grid@Dynamics.com");
        System.out.println("We got optionalUser");
        System.out.println("OptionalUser is " + optionalUser);
        System.out.println(optionalUser.isPresent() ? optionalUser.get(): "There is no user with email Grid@Dynami]]cs.com");

        List<User> users = userService.findAll();
        users.forEach(System.out::println);
    }
}