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
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/registerUser", method = RequestMethod.GET)
    public ResponseEntity<Boolean> registerUser(String email, String password, String repeatingPassword) {
        log.info("MYYYYYYYYY LOG: registerUser in JspToJSONAdapter");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("MYYYYYYYYY LOG: User is authenticated " + auth.isAuthenticated() + " Authorities are " + auth.getAuthorities() + " Credentials are " + auth.getCredentials());
        Collection<? extends GrantedAuthority> grantedAuthorities = auth.getAuthorities();
        if (!grantedAuthorities.isEmpty()) {
            GrantedAuthority grantedAuthority = grantedAuthorities.stream().findFirst().get();
            if (!grantedAuthority.getAuthority().equals("ROLE_ANONYMOUS")) {
                return new ResponseEntity(false, HttpStatus.CONFLICT);
            }
        }
        if (!email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+.?[a-zA-Z0-9]+")) {
            return new ResponseEntity("Email format does not satisfy format [a-zA-Z0-9]+@[a-zA-Z0-9]+.?[a-zA-Z0-9]+", HttpStatus.FORBIDDEN);
        }
        if (!password.equals(repeatingPassword)) {
            return new ResponseEntity("Passwords don't match", HttpStatus.FORBIDDEN);
        }
        boolean result = sessionModeOffController.registerUser(new UserChecker(email, password, repeatingPassword));
        return new ResponseEntity(result, result ? HttpStatus.OK: HttpStatus.CONFLICT);
    }

    @Override
    @RequestMapping(value = "/loginUserAndGetSession", method = RequestMethod.GET)
    public String loginUserAndGetSessionId(String email, String password) {
        log.info("MYYYYYYYYY LOG: loginUserAndGetSessionId in JspToJSONAdapter");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("MYYYYYYYYY LOG: User is authenticated " + auth.isAuthenticated() + " Authorities are " + auth.getAuthorities() + " Credentials are " + auth.getCredentials());
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
        log.info("MYYYYYYYYY LOG: getAllProductsFromStore in JspToJSONAdapter");
        return productsRepository.selectProducts(new HashMap<>());
    }

    @Override
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addItem", method = RequestMethod.GET)
    public ResponseEntity<String> addItemToCartProducts(final int ASKED_ITEM_ID, final int ASKED_QUANTITY) {
        log.info("MYYYYYYYYY LOG: addItemToCartProducts in JspToJSONAdapter");
        if (ASKED_QUANTITY <= 0 ) {
            return new ResponseEntity("Asked quantity should be positive", HttpStatus.BAD_REQUEST);
        }
        String legacyResult = sessionModeOnController.addItemToCartProducts(ASKED_ITEM_ID, ASKED_QUANTITY);
        return new ResponseEntity(legacyResult, legacyResult.equals("There is not enough product") ? HttpStatus.GONE: HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/displayCartContent", method = RequestMethod.GET)
    public DisplayCartContentObject displayCartContent() {
        log.info("MYYYYYYYYY LOG: displayCartContent in JspToJSONAdapter");
        List<ProductForCart> productForCartList = sessionModeOnController.getProductsInCart();
        return new DisplayCartContentObject(productForCartList);
    }

    @Override
    @RequestMapping(value = "/removeItem", method = RequestMethod.GET)
    public boolean removeItemFromCartById(int id) {
        log.info("MYYYYYYYYY LOG: removeItem in JspToJSONAdapter");
        return sessionModeOnController.removeItemFromCartById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/modifyItem", method = RequestMethod.GET)
    public ResponseEntity modifyCartItem(final int ID, final int NEW_QUANTITY) {
        log.info("MYYYYYYYYY LOG: modifyCartItem in JspToJSONAdapter");
        if (NEW_QUANTITY <= 0) {
            return new ResponseEntity("Asked quantity should be positive", HttpStatus.BAD_REQUEST);
        }
        Optional<ProductForCart> product = sessionModeOnController.getProductsInCart().stream().filter(productFromCart -> productFromCart.getId() == ID).findFirst();
        int oldQuantity = 0;
        if (product.isPresent()) {
            oldQuantity = product.get().getQuantityInCart();
        }
        ModifyCartItemsResults result = sessionModeOnController.modifyCartItem(ID, NEW_QUANTITY);
        if (result.equals(ModifyCartItemsResults.MODIFIED)) {
            return new ResponseEntity(new ModifyCartReturnedObject(result, oldQuantity, NEW_QUANTITY), HttpStatus.OK);
        }
        return new ResponseEntity(new ModifyCartReturnedObject(result, 0, 0), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/checkoutBooking", method = RequestMethod.GET)
    public boolean checkoutBooking() {
        log.info("MYYYYYYYYY LOG: checkoutBooking in JspToJSONAdapter");
        return sessionModeOnController.checkoutBooking();
    }

    @Override
    @RequestMapping(value = "/finishSession", method = RequestMethod.GET)
    public boolean finishSession(HttpServletRequest request, HttpServletResponse response) {
        log.info("MYYYYYYYYY LOG: finishSession in JspToJSONAdapter");
        String legacyResult = sessionModeOnController.finishSession();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (legacyResult.equals("main") && auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return true;
        }
        return false;
    }
}
