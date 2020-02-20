package com.webapp.main;

import com.webapp.configuration.AppConfig;
import com.webapp.controller.sessionModeControllers.SessionModeOffControllerConsole;
import com.webapp.controller.sessionModeControllers.SessionModeOnController;
import com.webapp.controller.sessionModeControllers.enums.ModifyCartItemsResults;
import com.webapp.model.UserChecker;
import com.webapp.repository.DatabaseInitializer;
import com.webapp.repository.ProductRepository;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

@Log
public class Main {

    private static SessionModeOnController sessionModeOnController = null;

    private static ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void main(String[] args) {

        //update db
        ProductRepository productRepository = (ProductRepository) appContext.getBean("ProductRepositoryImpl");
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(productRepository);
        databaseInitializer.initializeDatabase();

        while (true) {
            if (sessionModeOnController == null) {
                if (sessionOffCommandMenu()) {
                    sessionModeOnController = (SessionModeOnController) appContext.getBean("sessionModeOnControllerConsole");
                    log.info("MYYYYYYYYY LOG" +  sessionModeOnController);
                }
            } else {
                if (!sessionOnControl()) {
                    sessionModeOnController = null;
                }
            }
        }
    }

    private static boolean sessionOffCommandMenu() {
        SessionModeOffControllerConsole sessionModeOffController = (SessionModeOffControllerConsole) appContext.getBean("sessionModeOffControllerConsole");

        log.info("MYYYYYYYYY LOG" +  sessionModeOffController);

        List<String> allowedFormats = new ArrayList();
        allowedFormats.add("register user [a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-z]+ [a-zA-Z0-9]+");
        allowedFormats.add("login user [a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-z]+ [a-zA-Z0-9]+");

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
                respond = sessionModeOffController.registerUser(new UserChecker(strings.get(2), strings.get(3))) ?
                        "user is registered" : "user is not registered";
                break;
            case 1:
                UserChecker user = new UserChecker(strings.get(2), strings.get(3));
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
                "'modify cart item title: [itemId] quantity: [number]', 'checkout' to verify your book, " +
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
                respond = sessionModeOnController.addItemToCartProducts(Integer.parseInt(strings.get(5)), Integer.parseInt(strings.get(7)));
                break;
            case 2:
                respond = sessionModeOnController.displayCartContent();
                break;
            case 3:
                int idToRemove = Integer.parseInt(strings.get(6));
                respond = "Item with id " + idToRemove + (sessionModeOnController.removeItemFromCartById(idToRemove) ? " removed" : " not found");
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
                boolean successfullyRegistered = sessionModeOnController.checkoutBooking();
                respond = successfullyRegistered ? "Your booking is successfully registered" : "Sorry. There is not enough products in database any more";
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