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
                foundProduct.setAvailable(AVAILABLE - ASKED_QUANTITY);
                productService.updateProducts(selectedProducts);
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
            ProductForCart productForCart = productToRemove.get();
            Product product = new Product(id, productForCart.getTitle(), productForCart.getQuantityInCart() + availableInDB, productForCart.getPriceForOneItem());
            List<Product> products = new ArrayList<>();
            products.add(product);
            productService.updateProducts(products);
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
            productFromDb.setAvailable(productFromDb.getAvailable() - (newAmount - productFromCart.getQuantityInCart()));
            List<Product> productsToUpdate = new ArrayList<>();
            productsToUpdate.add(productFromDb);
            productService.updateProducts(productsToUpdate);
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
            Optional<Product> productFromDBOptional = productsFromDataBase.stream().filter(productFromDBInFilter -> productFromDBInFilter.getId() == product.getId()).findFirst();
            if (!productFromDBOptional.isPresent()) {
                log.warning("MYYYYYYYYY LOG: database error");
                return;
            }
            Product realProductFromDb = productFromDBOptional.get();
            realProductFromDb.setAvailable(realProductFromDb.getAvailable() + product.getQuantityInCart());
            cartProduct.remove(product);
        });
        productService.updateProducts(productsFromDataBase);
    }
}
