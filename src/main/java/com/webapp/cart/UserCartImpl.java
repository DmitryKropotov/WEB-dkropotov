package com.webapp.cart;

import com.webapp.controller.sessionModeControllers.enums.ModifyCartItemsResults;
import com.webapp.model.Product;
import com.webapp.model.ProductForCart;
import com.webapp.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Log
public class UserCartImpl implements UserCart {

    @Autowired
    ProductService productService;

    private List<ProductForCart> cartProduct = new ArrayList<>();

    @Override
    public List<ProductForCart> getProductCart() {
        return cartProduct;
    }

    @Override
    public String addItemToCardProducts(int ASKED_ITEM_ID, int ASKED_QUANTITY) {
        String respond = null;
        Map<String, Object> conditions = new HashMap();
        conditions.put("id", ASKED_ITEM_ID);
        List<Product> selectedProducts = productService.findProducts(conditions);

        if (selectedProducts.isEmpty()) {
            respond = "There is no product with id " + ASKED_ITEM_ID;
        } else {
            Product foundProduct = selectedProducts.get(0);
            final int AVAILABLE = foundProduct.getAvailable();
            if (AVAILABLE < ASKED_QUANTITY) {
                respond = "There is not enough product";
            } else {
                List<ProductForCart> productsFromCartWithAskedId = cartProduct.stream().filter(product -> product.getId() == foundProduct.getId()).collect(Collectors.toList());
                if (productsFromCartWithAskedId.isEmpty()) {
                    cartProduct.add(new ProductForCart(foundProduct.getId(), foundProduct.getTitle(), ASKED_QUANTITY, foundProduct.getPrice()));
                } else {
                    ProductForCart productFromCartWithAskedId = productsFromCartWithAskedId.get(0);
                    productFromCartWithAskedId.setQuantityInCart(productFromCartWithAskedId.getQuantityInCart() + ASKED_QUANTITY);
                }
                Map<String, Object> fieldsToUpdate = new HashMap();
                fieldsToUpdate.put("available", AVAILABLE - ASKED_QUANTITY);
                productService.updateProducts(fieldsToUpdate, conditions);
                respond = ASKED_QUANTITY + " items with id " + ASKED_ITEM_ID + " is added";
            }
        }
        return respond;
    }

    @Override
    public String displayCartContent() {
        String result = "";
        for (ProductForCart product : cartProduct) {
            result += product.toString() + " ";
        }
        if (result.equals("")) {
            result = "Your cart is empty";
        }
        return result;
    }

    @Override
    public boolean removeItemFromCartById(int id) {
        Optional<ProductForCart> productToRemove = cartProduct.stream().filter(product -> product.getId() == id).findFirst();
        if (productToRemove.isPresent()) {
            Map<String, Object> conditionsToSelect = new HashMap();
            conditionsToSelect.put("id", id);
            int availableInDB = productService.findProducts(conditionsToSelect).get(0).getAvailable();
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", availableInDB + productToRemove.get().getQuantityInCart());
            productService.updateProducts(columnsToUpdate, conditionsToSelect);
            cartProduct.remove(productToRemove.get());
            return true;
        }
        return false;
    }

    @Override
    public ModifyCartItemsResults modifyCartItem(int id, int newAmount) {
        List<ProductForCart> modifiedProductsFromCart = cartProduct.stream().filter(product -> product.getId() == id).collect(Collectors.toList());
        if (modifiedProductsFromCart.isEmpty()) {
            return ModifyCartItemsResults.NOT_FOUND_IN_CART;
        }
        Map<String, Object> conditionsToSelect = new HashMap();
        conditionsToSelect.put("id", id);
        int availableInDB = productService.findProducts(conditionsToSelect).get(0).getAvailable();
        ProductForCart productFromCart = modifiedProductsFromCart.get(0);
        if (newAmount > availableInDB + productFromCart.getQuantityInCart()) {
            return ModifyCartItemsResults.NOT_ENOUGH_IN_DATABASE;
        } else {
            Product productFromDb = productService.findProducts(conditionsToSelect).get(0);
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", productFromDb.getAvailable() - (newAmount - productFromCart.getQuantityInCart()));
            productService.updateProducts(columnsToUpdate, conditionsToSelect);
            productFromCart.setQuantityInCart(newAmount);
        }
        return ModifyCartItemsResults.MODIFIED;
    }

    @Override
    public boolean checkValidityProducts() {
        for (ProductForCart product : cartProduct) {
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("id", product.getId());
            List<Product> products = productService.findProducts(conditions);
            int productAvailable = products.get(0).getAvailable();
            if (products.isEmpty() || productAvailable < product.getQuantityInCart()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clearCartProducts() {
        cartProduct.clear();
    }

    @Override
    public void returnGoodsToStore() {
        List<Product> productsFromDataBase = productService.getAllProductsAsList();
        cartProduct.forEach(product -> {
            Optional<Product> productFromDB = productsFromDataBase.stream().filter(productFromDBInFilter -> productFromDBInFilter.getId() == product.getId()).findFirst();
            if (!productFromDB.isPresent()) {
                log.warning("MYYYYYYYYY LOG: database error");
                return;
            }
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", productFromDB.get().getAvailable() + product.getQuantityInCart());
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("id", product.getId());
            productService.updateProducts(columnsToUpdate, conditions);
        });
    }
}
