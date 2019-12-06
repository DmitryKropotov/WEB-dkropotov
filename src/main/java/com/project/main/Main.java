package com.project.main;

import com.project.controllers.sessionModeControllers.SessionModeOffController;
import com.project.controllers.sessionModeControllers.SessionModeOffControllerConsole;
import com.project.controllers.sessionModeControllers.SessionModeOnController;
import com.project.controllers.sessionModeControllers.SessionModeOnControllerConsole;
import com.project.repositories.ConnectionSaver;
import configuration.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static SessionModeOnController sessionModeOnController = null;

    private static SessionModeOffController sessionModeOffController = SessionModeOffControllerConsole.getInstance();

    //@Configuration
    //@ComponentScan
    //@EnableAutoConfiguration
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

        ConnectionSaver connectionSaver = new ConnectionSaver();

        System.out.println(connectionSaver.getUrl());

        boolean sessionModeOnStatus = false;
        while (true) {
            if (!sessionModeOnStatus) {
                sessionModeOnStatus = sessionOffCommandMenu();
                if (sessionModeOnStatus) {
                    sessionModeOnController = new SessionModeOnControllerConsole();
                }
            } else {
                sessionModeOnStatus = sessionOnControl();
                if (!sessionModeOnStatus) {
                    sessionModeOnController = null;
                }
            }
        }
    }

    private static boolean sessionOffCommandMenu() {
        List<String> allowedFormats = new ArrayList();
        allowedFormats.add("register user [a-zA-Z0-9]+@[a-z]+.[a-z]+ [a-zA-Z0-9]+");
        allowedFormats.add("login user [a-zA-Z0-9]+@[a-z]+.[a-z]+ [a-zA-Z0-9]+");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please print command in format 'register user [email] [password]'," +
                "'login user [email] [password]' or 'exit' to exit application. " +
                "Password can contain lower and upper cases latin letters and numbers");

        String inputString = scanner.nextLine();
        if (inputString.equals("exit")) {
            sessionModeOffController.exitApp();
        }
        int commandNumber = -1;
        for (int i = 0; i < allowedFormats.size(); i++) {
            if (inputString.matches(allowedFormats.get(i))) {
                commandNumber = i;
                break;
            }
        }

        List<String> strings = Arrays.asList(inputString.split(" "));
        String respond;
        boolean sessionModeOnStatus = false;
        switch(commandNumber) {
            case 0:
                respond = sessionModeOffController.registerUser(strings.get(2), strings.get(3));
                break;
            case 1:
                respond = sessionModeOffController.loginUserAndGetSessionId(strings.get(2),strings.get(3));
                if (!respond.contains("null")) {
                    sessionModeOnStatus = true;
                }
                break;
            default:
                respond = "Wrong format";
                break;
        }
        System.out.println(respond);
        return sessionModeOnStatus;
    }

    private static boolean sessionOnControl() {
        List<String> allowedFormats = new ArrayList();
        allowedFormats.add("show all products in store");
        allowedFormats.add("add item to cart id: [1-9][0-9]* quantity: [1-9][0-9]*");
        allowedFormats.add("display your cart content");
        allowedFormats.add("remove an item from cart id: [1-9][0-9]*");
        allowedFormats.add("modify cart item id: [1-9][0-9]* quantity: [1-9][0-9]*");
        allowedFormats.add("checkout");
        allowedFormats.add("finish session");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please print command in format 'show all products in store', " +
                "'add item to cart id: [itemId] quantity: [number]', 'display your cart content', " +
                "'remove an item from cart id: [itemId]', " +
                "'modify cart item id: [itemId] quantity: [number]', 'checkout' to verify your book, " +
                "'finish session' to logout or 'exit' to exit application.");

        String inputString = scanner.nextLine();
        if (inputString.equals("exit")) {
            sessionModeOnController.exitApp();
        }
        int commandNumber = -1;
        for (int i = 0; i < allowedFormats.size(); i++) {
            if (inputString.matches(allowedFormats.get(i))) {
                commandNumber = i;
                break;
            }
        }

        List<String> strings = Arrays.asList(inputString.split(" "));
        String respond;
        switch(commandNumber) {
            case 0:
                respond = sessionModeOnController.showProductsInStore();
                break;
            case 1:
                respond = sessionModeOnController.addItemToCardProducts(Integer.parseInt(strings.get(5)), Integer.parseInt(strings.get(7)));
                break;
            case 2:
                respond = sessionModeOnController.displayCartContent();
                break;
            case 3:
                respond = sessionModeOnController.removeItemFromCart(Integer.parseInt(strings.get(6)));
                break;
            case 4:
                respond = sessionModeOnController.modifyCartItem(Integer.parseInt(strings.get(4)), Integer.parseInt(strings.get(6)));
                break;
            case 5:
                sessionModeOnController.checkoutBooking();
            case 6:
                sessionModeOnController.finishSession();
                return false;
            default:
                respond = "Wrong format";
                break;
        }
        System.out.println(respond);
        return true;
    }
}