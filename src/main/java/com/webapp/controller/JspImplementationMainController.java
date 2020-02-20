package com.webapp.controller;

import com.webapp.configuration.AppConfig;
import com.webapp.controller.sessionModeControllers.SessionModeOffControllerJsp;
import com.webapp.controller.sessionModeControllers.SessionModeOnControllerJsp;
import com.webapp.controller.sessionModeControllers.enums.ModifyCartItemsResults;
import com.webapp.model.ProductRequest;
import com.webapp.model.UserChecker;
import com.webapp.repository.DatabaseInitializer;
import com.webapp.repository.ProductRepository;
import com.webapp.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@SessionAttributes("userchecker")
@Log
public class JspImplementationMainController {

    private SessionModeOnControllerJsp sessionModeOnController = null;

    private static ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

    private static int goToMainPageCall = 0;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String goToMainPage(Model model) {
        //update db
        ProductRepository productRepository = (ProductRepository) appContext.getBean("ProductRepositoryImpl");
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(productRepository);
        databaseInitializer.initializeDatabase();

        log.info("MYYYYYYYYY LOG: goToMainPage get");
        UserChecker userChecker = new UserChecker();
        model.addAttribute("userchecker", userChecker);
        return "main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public String goToMainPage(@Valid @ModelAttribute("userchecker") UserChecker user, BindingResult result, Model model) {

        //debug logs
        log.info("MYYYYYYYYY LOG: goToMainPage post " + user.getEmail() + " " + user.getPassword() + " " + user.getProductRequest() + " " + ++goToMainPageCall);
        log.info("MYYYYYYYYY LOG: Amount of errors is " + result.getErrorCount());
        List<ObjectError> errors = result.getAllErrors();
        errors.forEach(error ->  log.warning("MYYYYYYYYY LOG: " + error + " "));
        //debug logs

        String pageToReturn;
        if (sessionModeOnController == null) {
            //debug logs
            log.info("MYYYYYYYYY LOG: session mode off");
            //debug logs
            pageToReturn = sessionModeOff(user, result, model);
            if (pageToReturn.equals("sessionModeOnMainPage")) {
                sessionModeOnController =
                        (SessionModeOnControllerJsp) appContext.getBean("sessionModeOnControllerJsp");
                log.info("MYYYYYYYYY LOG" + sessionModeOnController);
                ProductRequest productRequest = new ProductRequest(sessionModeOnController.getAllProductsAsString());
                model.addAttribute("productrequest", productRequest);
                user.setProductRequest(productRequest);
            }
        } else {
            //debug logs
            log.info("MYYYYYYYYY LOG: session mode on + user.getProductRequest()");
            //debug logs
            pageToReturn = sessionModeOn(user.getProductRequest(), model);
            if (pageToReturn.equals("main")) {
                sessionModeOnController = null;
                user.setProductRequest(null);
            }
        }
        return pageToReturn;
    }

    private String sessionModeOff(UserChecker user, BindingResult result, Model model) {
        SessionModeOffControllerJsp sessionModeOffController =
                (SessionModeOffControllerJsp) appContext.getBean("sessionModeOffControllerJsp");
        log.info("MYYYYYYYYY LOG" + sessionModeOffController);

        user.setPasswordError("");
        user.setWrongEmailOrPassword("");
        user.setSuccessfulRegMessage("");
        user.setUserAlreadyExists("");

        String password = user.getPassword();
        String passwordRepeater = user.getPasswordRepeater();
        //check if there are errors in credentials
        if (result.hasErrors()) {
            user.setPasswordRepeater(null);
            return "main";
        }
        //Error in password
        else if (password.contains(" ")) {
            user.setPasswordError("Password shouldn't contain gaps");
            return "main";
        }
        //operation register user, passwords don't match
        else if (!passwordRepeater.equals(" ") && !password.equals(passwordRepeater)) {
            user.setPasswordError("Passwords don't match");
            user.setPasswordRepeater(null);
            return "main";
        }
        //operation register user, passwords match
        else if (!passwordRepeater.equals(" ")) {
            boolean registered = sessionModeOffController.registerUser(user);
            log.info("MYYYYYYYYY LOG:" + registered);
            if (registered) {
                user.setSuccessfulRegMessage("User is registered successfully");
            } else {
                user.setUserAlreadyExists("Database error. Possible, user with such email already exists");
            }
            user.setPasswordRepeater(null);
            return "main";
        }
        //operation login user
        else {
            Optional<Integer> sessionId = sessionModeOffController.loginUserAndGetSessionId(user);
            if (!sessionId.isPresent()) {
                user.setWrongEmailOrPassword("Email or password error");
                return "main";
            }
            return "sessionModeOnMainPage";
        }
    }

    private String sessionModeOn(ProductRequest product, Model model) {
        //log
        log.info("MYYYYYYYYY LOG: session mode off");
        //log
        ProductService productService = (ProductService) appContext.getBean("ProductsService");
        model.addAttribute("productrequest", product);

        //update values which will be displayed on jsp page
        product.setAnswerForGoodRespond("");
        product.setCartContent("");
        product.setRemovedSuccessfully("");
        product.setModificationResult("");
        product.setCheckOutResult("");


        Map<String, Integer> titleAmountProducts = productService.getTitleAmountProductsAsMap();
        product.setTitleAmountProducts(titleAmountProducts);
        Map<String, Integer> titleIdProductsAsMap = sessionModeOnController.getTitleIdProductsAsMap();
        product.setTitleIdProducts(titleIdProductsAsMap);

        if (product.getTitle() != null && product.getAmount() != null) {
            //debug logs
            log.info("MYYYYYYYYY LOG: session mode off");
            log.info("MYYYYYYYYY LOG: 1");
            //debug logs
            String title = product.getTitle();
            Integer requestedAmount = product.getAmount();
            if (requestedAmount <= 0) {
                product.setAnswerForGoodRespond("Amount of quantity should be positive");
            } else if (titleIdProductsAsMap.containsKey(title)) {
                String answer = sessionModeOnController.addItemToCartProducts(titleIdProductsAsMap.get(title), requestedAmount);
                product.setAnswerForGoodRespond(answer);
            } else {
                product.setAnswerForGoodRespond("There is no product with name " + title);
            }
            makeValuesOfLogicVarsAndContentToShowDefault(product, sessionModeOnController);
            return "sessionModeOnMainPage";
        } else if (product.isDisplayContent()) {
            //debug logs
            log.info("MYYYYYYYYY LOG: 2");
            //debug logs
            String answer = sessionModeOnController.displayCartContent();
            product.setCartContent(answer);
            makeValuesOfLogicVarsAndContentToShowDefault(product, sessionModeOnController);
            return "sessionModeOnMainPage";
        } else if (product.getItemToRemove() != null) {
            //debug logs
            log.info("MYYYYYYYYY LOG: 3");
            //debug logs
            String productName = product.getItemToRemove();
            if (titleIdProductsAsMap.containsKey(product.getItemToRemove())) {
                int id = titleIdProductsAsMap.get(product.getItemToRemove());
                String removedSuccessfully = "Item with name " + productName + " " + (sessionModeOnController.removeItemFromCartById(id) ? " removed" : " is not found in cart");
                product.setRemovedSuccessfully(removedSuccessfully);
            } else {
                product.setRemovedSuccessfully("Item with name " + productName + " is not in database");
            }
            makeValuesOfLogicVarsAndContentToShowDefault(product, sessionModeOnController);
            return "sessionModeOnMainPage";
        } else if (product.getItemToModify() != null && product.getNewAmount() != null) {
            //debug logs
            log.info("MYYYYYYYYY LOG: 4");
            //debug logs
            String productName = product.getItemToModify();
            int productNewAmount = product.getNewAmount();
            if (productNewAmount <= 0) {
                product.setModificationResult("Amount of quantity should be positive");
            } else if (titleIdProductsAsMap.containsKey(productName)) {
                int id = titleIdProductsAsMap.get(productName);
                ModifyCartItemsResults modificationResult = sessionModeOnController.modifyCartItem(id, productNewAmount);
                switch (modificationResult) {
                    case NOT_FOUND_IN_CART:
                        product.setModificationResult("There is no item with productName " + productName + " in cart");
                        break;
                    case NOT_ENOUGH_IN_DATABASE:
                        product.setModificationResult("Item with productName " + productName + " is not enough in database");
                        break;
                    case MODIFIED:
                        product.setModificationResult("Item with productName " + productName + " modified");
                        break;
                }
            } else {
                product.setModificationResult("Item with productName " + productName + " not found in database");
            }
            makeValuesOfLogicVarsAndContentToShowDefault(product, sessionModeOnController);
            return "sessionModeOnMainPage";
        } else if (product.isCheckoutBooking()) {
            //debug logs
            log.info("MYYYYYYYYY LOG: 5");
            //debug logs
            boolean successfullyRegistered = sessionModeOnController.checkoutBooking();
            String successfulCheckout = successfullyRegistered ? "Your booking is successfully registered" : "Sorry. There is not enough products in database any more";
            product.setCheckOutResult(successfulCheckout);
            makeValuesOfLogicVarsAndContentToShowDefault(product, sessionModeOnController);
            return "sessionModeOnMainPage";
        } else if (product.isLogOut()) {
            //debug logs
            log.info("MYYYYYYYYY LOG: 6");
            //debug logs
            return sessionModeOnController.finishSession();
        } else {
            //debug logs
            log.info("MYYYYYYYYY LOG: 7");
            //debug logs
            makeValuesOfLogicVarsAndContentToShowDefault(product, sessionModeOnController);
            return "sessionModeOnMainPage";
        }
    }

    private void makeValuesOfLogicVarsAndContentToShowDefault(ProductRequest product, SessionModeOnControllerJsp sessionModeOnController) {
        String allProductsAsString = sessionModeOnController.getAllProductsAsString();
        product.setAvailableProducts(allProductsAsString);
        product.setTitle(null);
        product.setAmount(null);
        product.setDisplayContent(false);
        product.setItemToRemove(null);
        product.setItemToModify(null);
        product.setNewAmount(null);
        product.setCheckoutBooking(false);
        product.setLogOut(false);
    }
}