package com.database_connection.main;

import com.database_connection.configuration.AppConfig;
import com.database_connection.model.User;
import com.database_connection.service.UserService;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

@Log
public class Main {

    private static ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void main(String[] args) {


        for (int i = 0; i < appContext.getBeanDefinitionNames().length; i++) {
            log.info("MYYYYY LOG: "  + appContext.getBeanDefinitionNames()[i]);
        }
        UserService userService = (UserService) appContext.getBean("userServiceImpl");
        log.info("MYYYYY LOG:" + userService.getUserRepository());
        userService.createUser("Grid@Dynamicus.com", "grid");

        String email = "Grid@Dynamics.com";
        Optional<User> optionalUser = userService.findByEmail(email);
        log.info("MYYYYY LOG: We got optionalUser");
        log.info("MYYYYY LOG: OptionalUser is " + optionalUser);
        log.info("MYYYYY LOG: " + (optionalUser.isPresent() ? optionalUser.get(): "There is no user with email " + email));

        List<User> users = userService.findAll();
        users.forEach(user -> log.info("MYYYYY LOG: " + user));
    }
}
