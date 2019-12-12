package com.project.controllers.sessionModeControllers;

import com.project.models.Product;
import com.project.models.ProductRequest;
import com.project.services.ProductsService;
import com.project.services.ProductsServiceImpl;
import com.sun.tools.internal.ws.processor.model.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("productrequest")
public class SessionModeOnControllerJsp implements SessionModeOnController {

    private ProductsService productsService = new ProductsServiceImpl();

    private List<Product> cartProducts = new ArrayList<>();

    @RequestMapping(value = "/sessionModeOn", method = RequestMethod.GET)
    public String mainInSessionModeOn(Model model) {
        System.out.println("mainInSessionModeOn method get");
        model.addAttribute("productrequest", new ProductRequest());
        return "sessionModeOnMainPage";
    }

    @RequestMapping(value = "/sessionModeOn", method = RequestMethod.POST)
    public String mainInSessionModeOn(@Valid @ModelAttribute("sessionModeOn") ProductRequest product, BindingResult result, Model model) {
        System.out.println("mainInSessionModeOn method post");
        if (product.isLogOut()) {
            return finishSession();
        } else {
            return "sessionModeOnMainPage";
        }
    }

    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    public @ResponseBody List<Product> getGoods(Model model) {
        System.out.println("getGoods method get");
        List<Product> products = new ArrayList<>();

        Product p1 = new Product(0, "p1", 1, 1.0);
        products.add(p1);

        Product p2 = new Product(1, "p2", 1, 1.0);
        products.add(p2);

        Product p3 = new Product(2, "p3", 1, 1.0);
        products.add(p3);

        return products;
    }



    @Override
    public String showProductsInStore() {
        List<Product> products = productsService.selectAllProducts();
        String result = "";
        for (Product product : products) {
            result = result + product.toString();
        }
        return result;
    }

    @Override
    public String addItemToCardProducts(int ASKED_ITEM_ID, int ASKED_QUANTITY) {
        String respond;
        Map<String, Object> conditions = new HashMap();
        conditions.put("id", ASKED_ITEM_ID);
        List<Product> selectedProducts = productsService.selectProducts(conditions);

        if (selectedProducts.isEmpty()) {
            respond = "There is no product with id " + ASKED_ITEM_ID;
        } else {
            Product foundProduct = selectedProducts.get(0);
            final int AVAILABLE = foundProduct.getAvailable();
            if (AVAILABLE < ASKED_QUANTITY) {
                respond = "There is not enough product";
            } else {
                List<Product> productsFromCartWithAskedId = cartProducts.stream().filter(cartProduct -> cartProduct.getId() == foundProduct.getId()).collect(Collectors.toList());
                if (productsFromCartWithAskedId.isEmpty()) {
                    cartProducts.add(new Product(foundProduct.getId(), foundProduct.getTitle(), ASKED_QUANTITY, foundProduct.getPrice()));
                } else {
                    Product productFromCartWithAskedId = productsFromCartWithAskedId.get(0);
                    productFromCartWithAskedId.setAvailable(productFromCartWithAskedId.getAvailable() + ASKED_QUANTITY);
                }
                Map<String, Object> fieldsToUpdate = new HashMap();
                fieldsToUpdate.put("available", AVAILABLE - ASKED_QUANTITY);
                productsService.updateProducts(fieldsToUpdate, conditions);
                respond = ASKED_QUANTITY + " items with id " + ASKED_ITEM_ID + " is added";
            }
        }
        return respond;
    }

    @Override
    public String displayCartContent() {
        String result = "";
        for (Product product : cartProducts) {
            result += "title: " + product.getTitle() + ", amount in cart: " + product.getAvailable() +
                    " total value: " + product.getAvailable()*product.getPrice() + " ";
        }
        if (result.equals("")) {
            result = "Your cart is empty";
        }
        return result;
    }

    @Override
    public String removeItemFromCart(int id) {
        Optional<Product> productToRemove = cartProducts.stream().filter(product -> product.getId() == id).findFirst();
        if (productToRemove.isPresent()) {
            Map<String, Object> conditionsToSelect = new HashMap();
            conditionsToSelect.put("id", id);
            int availableInDB = productsService.selectProducts(conditionsToSelect).get(0).getAvailable();
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", availableInDB + productToRemove.get().getAvailable());
            productsService.updateProducts(columnsToUpdate, conditionsToSelect);
            cartProducts.remove(productToRemove.get());
            return "Item with id " + id + " removed";
        } else {
            return "Item with id " + id+ " not found";
        }
    }

    @Override
    public String modifyCartItem(int id, int newAmount) {
        List<Product> modifiedProductsFromCart = cartProducts.stream().filter(cartProduct -> cartProduct.getId() == id).collect(Collectors.toList());
        if (modifiedProductsFromCart.isEmpty()) {
            return "There is no item with id " + id + " in cart";
        }
        Map<String, Object> conditionsToSelect = new HashMap();
        conditionsToSelect.put("id", id);
        int availableInDB = productsService.selectProducts(conditionsToSelect).get(0).getAvailable();
        Product productFromCart = modifiedProductsFromCart.get(0);
        if (newAmount > availableInDB + productFromCart.getAvailable()) {
            return "Item with id " + id + " is not enough in database";
        } else {
            Product productFromDb = productsService.selectProducts(conditionsToSelect).get(0);
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", productFromDb.getAvailable() - (newAmount - productFromCart.getAvailable()));
            productsService.updateProducts(columnsToUpdate, conditionsToSelect);
            productFromCart.setAvailable(newAmount);
        }
        return "Item with id " + id + (!modifiedProductsFromCart.isEmpty() ? " modified": " not found");
    }

    @Override
    public boolean checkoutBooking() {
        for (Product product : cartProducts) {
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("id", product.getId());
            List<Product> products = productsService.selectProducts(conditions);
            int productAvailable = products.get(0).getAvailable();
            if (products.isEmpty() || productAvailable < product.getAvailable()) {
                if (products.isEmpty()) {
                    System.out.println("Sorry. There is no product with id = " + product.getId() + " any more");
                } else {
                    System.out.println("Sorry. There is only " +
                            productAvailable + " with id = " + product.getId() + " available in the database");
                }
                return false;
            }
        }
        System.out.println("Your booking is successfully registered");
        return true;
    }

    @Override
    public String finishSession() {
         return "redirect: main.html";
    }
}
