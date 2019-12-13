package com.project.controllers.sessionModeControllers;

import com.project.controllers.sessionModeControllers.enums.ModifyCartItemsResults;
import com.project.models.Product;
import com.project.models.ProductRequest;
import com.project.services.ProductsService;
import com.project.services.ProductsServiceImpl;
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
        model.addAttribute("productrequest", new ProductRequest(getAllProductsAsString(), getTitleAmountProductsAsMap(),
                getTitleIdProductsAsMap()));
        return "sessionModeOnMainPage";
    }

    @RequestMapping(value = "/sessionModeOn", method = RequestMethod.POST)
    public String mainInSessionModeOn(@Valid @ModelAttribute("productrequest") ProductRequest product, BindingResult result, Model model) {
        System.out.println("mainInSessionModeOn method post");

        String allProductsAsString = getAllProductsAsString();
        product.setAvailableProducts(allProductsAsString);
        Map<String, Integer> titleAmountProducts = getTitleAmountProductsAsMap();
        product.setTitleAmountProducts(titleAmountProducts);
        Map<String, Integer> titleIdProductsAsMap = getTitleIdProductsAsMap();
        product.setTitleIdProducts(titleIdProductsAsMap);

        String title = product.getTitle();
        Integer amount = product.getAmount();
        if (title != null && amount != null) {
            String answer =  addItemToCardProducts(titleIdProductsAsMap.get(title), titleAmountProducts.get(title));
            product.setAnswerForGoodRespond(answer);
            return "sessionModeOnMainPage";
        } else if (product.isDisplayContent()) {
              String answer = displayCartContent();
              product.setCartContent(answer);
              return "sessionModeOnMainPage";
        } else if (product.getItemToRemove() != null) {
            int id = titleIdProductsAsMap.get(product.getItemToRemove());
            String removedSuccessfully = "Item with id " + id + (removeItemFromCart(id) ? " removed": " not found");
            product.setRemovedSuccessfully(removedSuccessfully);
            return "sessionModeOnMainPage";
        } else if (product.getItemToModify() != null && product.getNewAmount() != null) {
            int id = titleIdProductsAsMap.get(product.getItemToRemove());
            ModifyCartItemsResults modificationResult = modifyCartItem(id, product.getNewAmount());
            switch (modificationResult) {
                case NOT_FOUND_IN_CART:
                    product.setModificationResult("There is no item with id " + id + " in cart");
                    break;
                case NOT_FOUND_IN_DATABASE:
                    product.setModificationResult("Item with id " + id + " not found");
                    break;
                case NOT_ENOUGH_IN_DATABASE:
                    product.setModificationResult("Item with id " + id + " is not enough in database");
                    break;
                case MODIFIED:
                    product.setModificationResult("Item with id " + id + " modified");
                    break;
            }
            return "sessionModeOnMainPage";
        } else if (product.isCheckoutBooking()) {
            String successfulCheckout = checkoutBooking() ? "Your booking is successfully registered": "Sorry. There is not enough products in database any more";
            product.setCheckOutResult(successfulCheckout);
            return "sessionModeOnMainPage";
        } else if (product.isLogOut()) {
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
    public List<Product> showProductsInStore() {
        return productsService.getAllProductsAsList();
    }

    @Override
    public String getAllProductsAsString() {
        return productsService.getAllProductsAsString();
    }

    @Override
    public Map<String, Integer> getTitleAmountProductsAsMap() {
        return productsService.getTitleAmountProductsAsMap();
    }

    @Override
    public Map<String, Integer> getTitleIdProductsAsMap() {
        return productsService.getTitleIdProductsAsMap();
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
    public boolean removeItemFromCart(int id) {
        Optional<Product> productToRemove = cartProducts.stream().filter(product -> product.getId() == id).findFirst();
        if (productToRemove.isPresent()) {
            Map<String, Object> conditionsToSelect = new HashMap();
            conditionsToSelect.put("id", id);
            int availableInDB = productsService.selectProducts(conditionsToSelect).get(0).getAvailable();
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", availableInDB + productToRemove.get().getAvailable());
            productsService.updateProducts(columnsToUpdate, conditionsToSelect);
            cartProducts.remove(productToRemove.get());
            return true;
        }
        return false;
    }

    @Override
    public ModifyCartItemsResults modifyCartItem(int id, int newAmount) {
        List<Product> modifiedProductsFromCart = cartProducts.stream().filter(cartProduct -> cartProduct.getId() == id).collect(Collectors.toList());
        if (modifiedProductsFromCart.isEmpty()) {
            return ModifyCartItemsResults.NOT_FOUND_IN_CART;
        }
        Map<String, Object> conditionsToSelect = new HashMap();
        conditionsToSelect.put("id", id);
        int availableInDB = productsService.selectProducts(conditionsToSelect).get(0).getAvailable();
        Product productFromCart = modifiedProductsFromCart.get(0);
        if (newAmount > availableInDB + productFromCart.getAvailable()) {
            return ModifyCartItemsResults.NOT_ENOUGH_IN_DATABASE;
        } else {
            Product productFromDb = productsService.selectProducts(conditionsToSelect).get(0);
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", productFromDb.getAvailable() - (newAmount - productFromCart.getAvailable()));
            productsService.updateProducts(columnsToUpdate, conditionsToSelect);
            productFromCart.setAvailable(newAmount);
        }
        if (modifiedProductsFromCart.isEmpty()) {
            return ModifyCartItemsResults.NOT_FOUND_IN_DATABASE;
        }
        return ModifyCartItemsResults.MODIFIED;
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
        returnGoodsToStore();
        return "redirect: main.html";
    }

    private void returnGoodsToStore() {
        List<Product> productsFromDataBase = productsService.getAllProductsAsList();
        cartProducts.forEach(product -> {
            Optional<Product> productFromDB = productsFromDataBase.stream().filter(productFromDBInFilter -> productFromDBInFilter.getId() == product.getId()).findFirst();
            if (!productFromDB.isPresent()) {
                System.out.println("database error");
                return;
            }
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", productFromDB.get().getAvailable() + product.getAvailable());
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("id", product.getId());
            productsService.updateProducts(columnsToUpdate, conditions);
        });
    }
}
