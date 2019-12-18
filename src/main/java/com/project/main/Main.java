package com.project.main;

import com.project.controllers.sessionModeControllers.SessionModeOffControllerConsole;
import com.project.controllers.sessionModeControllers.SessionModeOnController;
import com.project.controllers.sessionModeControllers.SessionModeOnControllerConsole;
import com.project.controllers.sessionModeControllers.enums.ModifyCartItemsResults;
import com.project.models.User;
import com.project.models.UserChecker;

import java.util.*;

public class Main {

    private static SessionModeOnController sessionModeOnController = null;

    //@Configuration
    //@ComponentScan
    //@EnableAutoConfiguration
    public static void main(String[] args) {
        /*ApplicationContext appContext = new FileSystemXmlApplicationContext("/src/main/webapp/WEB-INF/config/servlet-config.xml");//new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println(appContext.getBeanDefinitionCount());
        String[] beanNames = appContext.getBeanDefinitionNames();
        for (int i = 0; i < beanNames.length; i++) {
            System.out.println(beanNames[i]);
        }
        Object sessionModeOffControllerJsp = appContext.getBean("SessionModeOffControllerJsp");

        System.out.println(sessionModeOffControllerJsp.getClass());

        ConnectionSaver connectionSaver = new ConnectionSaver();

        System.out.println(connectionSaver.getUrl());*/

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
        SessionModeOffControllerConsole sessionModeOffController = (SessionModeOffControllerConsole) SessionModeOffControllerConsole.getInstance();

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
        switch (commandNumber) {
            case 0:
                respond = sessionModeOffController.registerUser(new User(-200, strings.get(2), strings.get(3))) ?
                        "user is registered" : "user is not registered";//think how to implement it better
                break;
            case 1:
                UserChecker user = new UserChecker();
                user.setEmail(strings.get(2));
                user.setPassword(strings.get(3));
                Optional<Integer> sessionId = sessionModeOffController.loginUserAndGetSessionId(user);
                if (sessionId.isPresent()) {
                    sessionModeOnStatus = true;
                    respond = "User is logged in successfully. SessionId is " + sessionId.get();
                } else {
                    respond = "Email or password error";
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
        allowedFormats.add("modify cart item title: .* quantity: [1-9][0-9]*");
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
        String respond = null;
        switch (commandNumber) {
            case 0:
                respond = sessionModeOnController.getAllProductsAsString();
                break;
            case 1:
                respond = sessionModeOnController.addItemToCardProducts(Integer.parseInt(strings.get(5)), Integer.parseInt(strings.get(7)));
                break;
            case 2:
                respond = sessionModeOnController.displayCartContent();
                break;
            case 3:
                int idToRemove = Integer.parseInt(strings.get(6));
                respond = "Item with id " + idToRemove + (sessionModeOnController.removeItemFromCart(idToRemove) ? " removed" : " not found");
                break;
            case 4:
                Map<String, Integer> titleIdProductsAsMap = sessionModeOnController.getTitleIdProductsAsMap();
                String productTitle = strings.get(4);
                int newAmount = Integer.parseInt(strings.get(6));
                if (titleIdProductsAsMap.containsKey(productTitle)) {
                    int idToModify = titleIdProductsAsMap.get(productTitle);
                    ModifyCartItemsResults result = sessionModeOnController.modifyCartItem(idToModify, newAmount);
                    switch (result) {
                        case NOT_FOUND_IN_CART:
                            respond = "There is no item with title " + productTitle + " in cart";
                            break;
                        case NOT_ENOUGH_IN_DATABASE:
                            respond = "Item with title " + productTitle + " is not enough in database";
                            break;
                        case MODIFIED:
                            respond = "Item with title " + productTitle + " modified";
                            break;
                    }
                } else {
                    respond = "Item with title " + productTitle + " not found in database";
                }
                break;
            case 5:
                sessionModeOnController.checkoutBooking();
                break;
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