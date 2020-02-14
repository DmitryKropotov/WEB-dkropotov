package com.webapp.controller.adapter;

import com.webapp.controller.sessionModeControllers.SessionModeOffController;
import com.webapp.controller.sessionModeControllers.SessionModeOnController;
import com.webapp.controller.sessionModeControllers.enums.ModifyCartItemsResults;
import com.webapp.controller.user_functional.UserFunctionalController;
import com.webapp.model.Product;
import com.webapp.model.ProductForCart;
import com.webapp.model.UserChecker;
import com.webapp.repository.ProductsRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@Log
public class JspToJSONAdapter implements UserFunctionalController {

    @Autowired
    SessionModeOffController sessionModeOffController;

    @Autowired
    SessionModeOnController sessionModeOnController;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    @RequestMapping(value = "/registerUser", method = RequestMethod.GET)
    public ResponseEntity<Boolean> registerUser(String email, String password, String repeatingPassword) {
        log.info("registerUser in JspToJSONAdapter");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User is authenticated " + auth.isAuthenticated() + " Authorities are " + auth.getAuthorities() + " Credentials are " + auth.getCredentials());
        Collection<? extends GrantedAuthority> grantedAuthorities = auth.getAuthorities();
        if (!grantedAuthorities.isEmpty()) {
            GrantedAuthority grantedAuthority = grantedAuthorities.stream().findFirst().get();
            if (!grantedAuthority.getAuthority().equals("ROLE_ANONYMOUS")) {
                return new ResponseEntity(false, HttpStatus.CONFLICT);
            }
        }
        boolean result = sessionModeOffController.registerUser(new UserChecker(email, password, repeatingPassword));
        return result ? new ResponseEntity(result, HttpStatus.OK): new ResponseEntity(result, HttpStatus.CONFLICT);
    }

    @Override
    @RequestMapping(value = "/loginUserAndGetSession", method = RequestMethod.GET)
    public String loginUserAndGetSessionId(String email, String password) {
        log.info("loginUserAndGetSessionId in JspToJSONAdapter");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User is authenticated " + auth.isAuthenticated() + " Authorities are " + auth.getAuthorities() + " Credentials are " + auth.getCredentials());
        Collection<? extends GrantedAuthority> grantedAuthorities = auth.getAuthorities();
        if (!grantedAuthorities.isEmpty()) {
            GrantedAuthority grantedAuthority = grantedAuthorities.stream().findFirst().get();
            if (!grantedAuthority.getAuthority().equals("ROLE_ANONYMOUS")) {
                return "User is already logged in";
            }
        }
        Optional<Integer> sessionId = sessionModeOffController.loginUserAndGetSessionId(new UserChecker(email, password));
        return sessionId.map(Object::toString).orElseGet(() -> "null");
    }

    @Override
    @RequestMapping(value = "/getAllProductsFromStore", method = RequestMethod.GET)
    public List<Product> getAllProductsFromStore() {
        log.info("getAllProductsFromStore in JspToJSONAdapter");
        return productsRepository.selectProducts(new HashMap<>());
    }

    @Override
    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public String addItemToCartProducts(int ASKED_ITEM_ID, int ASKED_QUANTITY) {
        log.info("addItemToCartProducts in JspToJSONAdapter");
        return sessionModeOnController.addItemToCartProducts(ASKED_ITEM_ID, ASKED_QUANTITY);
    }

    @Override
    @RequestMapping(value = "/displayCartContent", method = RequestMethod.GET)
    public List<ProductForCart> displayCartContent() {
        log.info("displayCartContent in JspToJSONAdapter");
        return sessionModeOnController.getProductsInCart();
    }

    @Override
    @RequestMapping(value = "/removeItem", method = RequestMethod.GET)
    public boolean removeItemFromCartById(int id) {
        log.info("removeItem in JspToJSONAdapter");
        return sessionModeOnController.removeItemFromCartById(id);
    }

    @Override
    @RequestMapping(value = "/modifyItem", method = RequestMethod.GET)
    public ModifyCartReturnedObject modifyCartItem(int id, int newQuantity) {
        log.info("modifyCartItem in JspToJSONAdapter");
        Optional<ProductForCart> product = sessionModeOnController.getProductsInCart().stream().filter(productFromCart -> productFromCart.getId() == id).findFirst();
        int oldQuantity = 0;
        if (product.isPresent()) {
            oldQuantity = product.get().getQuantityInCart();
        }
        ModifyCartItemsResults result = sessionModeOnController.modifyCartItem(id, newQuantity);
        if (result.equals(ModifyCartItemsResults.MODIFIED)) {
            return new ModifyCartReturnedObject(result, oldQuantity, newQuantity);
        }
        return new ModifyCartReturnedObject(result, 0, 0);
    }

    @Override
    @RequestMapping(value = "/checkoutBooking", method = RequestMethod.GET)
    public boolean checkoutBooking() {
        log.info("checkoutBooking in JspToJSONAdapter");
        return sessionModeOnController.checkoutBooking();
    }

    @Override
    @RequestMapping(value = "/finishSession", method = RequestMethod.GET)
    public boolean finishSession(HttpServletRequest request, HttpServletResponse response) {
        log.info("finishSession in JspToJSONAdapter");
        String legacyResult = sessionModeOnController.finishSession();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (legacyResult.equals("main") && auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return true;
        }
        return false;
    }
}
